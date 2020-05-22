/*
 *             DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *                     Version 2, December 2004
 *
 * Copyright (C) 2019, Zastrix Arundell, https://github.com/ZastrixArundell
 *
 *  Everyone is permitted to copy and distribute verbatim or modified
 *  copies of this license document, and changing it is allowed as long
 *  as the name is changed.
 *
 *             DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *    TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *   0. You just DO WHAT THE FUCK YOU WANT TO.
 *
 *
 */

package com.github.zastrixarundell.toramsensei.commands.search.items;

import com.github.zastrixarundell.toramsensei.Parser;
import com.github.zastrixarundell.toramsensei.commands.DiscordCommand;
import com.github.zastrixarundell.toramsensei.objects.toram.items.Item;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class UpgradeCommand extends DiscordCommand
{
    /*
        For some reason a HashSet can't be used here so I am using the name of the item
        as they key for an unique identifier to remove copies.
     */
    private HashMap<String, Item> allUpgradeXtals = new HashMap<>();

    public UpgradeCommand()
    {
        super("upgrade", "enhance");

        try
        {
            setupXtals();
        }
        catch (Exception ignore)
        {

        }
    }

    @Override
    protected void runCommand(MessageCreateEvent event)
    {
        ArrayList<String> arguments = Parser.argumentsParser(event);

        if (arguments.isEmpty())
        {
            emptySearch(event);
            return;
        }

        String data = String.join(" ", arguments);

        Runnable runnable = () ->
        {
            try
            {
                if(allUpgradeXtals.isEmpty())
                    setupXtals();

                List<Item> itemList = new ArrayList<>();

                allUpgradeXtals.values().forEach(upgradeXtal ->
                {
                    if(String.join("", upgradeXtal.getStats()).toLowerCase().contains("upgrade for: " + data.toLowerCase()))
                        itemList.add(upgradeXtal);
                });

                if(itemList.isEmpty())
                {
                    noResults(event);
                    return;
                }

                itemList.forEach(item -> sendItemEmbed(item, event));
            }
            catch (Exception e)
            {
                sendErrorMessage(event);
                e.printStackTrace();
                allUpgradeXtals = new HashMap<>();
            }
        };

        executeRunnable(event, runnable);
    }

    private void setupXtals() throws IOException
    {
        /*
            Ya for some reason the webcrawler doesn't like load all of the xtals in one run so I gotta like
            go ascending for like most of 'em and then go descending for the rest of the xtals which aren't like
            loaded... oof... Hey, it works!
         */

        Document document = Jsoup.connect("http://coryn.club/item.php")
                .data("special", "xtal")
                .data("show", "3000")
                .data("order", "name ASC")
                .get();

        Element cardContainer = document.getElementsByClass("card-container").first();
        getUpgradable(cardContainer).forEach(item -> allUpgradeXtals.put(item.getName(), item));

        document = Jsoup.connect("http://coryn.club/item.php")
                .data("special", "xtal")
                .data("show", "3000")
                .data("order", "name DESC")
                .get();

        cardContainer = document.getElementsByClass("card-container").first();
        getUpgradable(cardContainer).forEach(item -> allUpgradeXtals.put(item.getName(), item));
    }

    private static Elements getChildrenElements(Element element)
    {
        Elements elements = new Elements();

        for (Element child : element.children())
            if(child.parent() == element)
                elements.add(child);

        return elements;
    }

    public static ArrayList<Item> getUpgradable(Element cardContainer)
    {
        Elements divs = getChildrenElements(cardContainer);

        ArrayList<Item> listOfItems = new ArrayList<>();

        for (Element div : divs)
            if (div.parent() == cardContainer)
                if (!div.hasClass("card-adsense"))
                {
                    Item item = new Item(div);

                    if (String.join("\n", item.getStats()).toLowerCase().contains("upgrade for"))
                        listOfItems.add(item);
                }

        return listOfItems;
    }

    private void sendItemEmbed(Item item, MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(item.getName())
                .addInlineField("NPC sell price:", item.getPrice())
                .addInlineField("Processed into:", item.getProc());

        String stats = String.join("\n", item.getStats());

        embed.addField("Stats/Effect:", stats);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < item.getObtainedFrom().size() && i < 10; i++)
        {
            String key = item.getObtainedFrom().get(i);
            stringBuilder.append(i == 0 ? key : "\n" + key);
        }

        embed.addInlineField("Obtained from:", stringBuilder.toString());

        if (item.getApp() != null)
            embed.setThumbnail(item.getApp());
        else
            Parser.parseThumbnail(embed, messageCreateEvent);

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void noResults(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("No results!")
                .setDescription("Looks like there isn't an upgrade xtal for that!");

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void emptySearch(MessageCreateEvent messageCreateEvent)
    {
    EmbedBuilder embed = new EmbedBuilder()
            .setTitle("Empty search!")
            .setDescription("You can not find an upgrade xtal without specifying what to upgrade!");

    Parser.parseThumbnail(embed, messageCreateEvent);
    Parser.parseFooter(embed, messageCreateEvent);
    Parser.parseColor(embed, messageCreateEvent);

    messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendErrorMessage(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Error while getting the upgrade!")
                .setDescription("An error happened! Report to the bot owner!");

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

}
