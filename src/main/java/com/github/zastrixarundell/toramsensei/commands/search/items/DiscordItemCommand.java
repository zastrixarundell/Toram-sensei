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
import com.github.zastrixarundell.toramsensei.objects.toram.Item;
import com.github.zastrixarundell.toramsensei.objects.toram.ItemType;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class DiscordItemCommand extends DiscordCommand
{
    private final ItemType itemType;

    public DiscordItemCommand(ItemType type)
    {
        super(type.getCallers());
        this.itemType = type;
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
                Connection connection = Jsoup.connect("http://coryn.club/item.php")
                        .data("name", data)
                        .data("show", "5")
                        .data("order", itemType.getType() != null ? itemType.getType() + " DESC,name" : "name");

                if(itemType.getCode() != null)
                    connection = connection.data("type", itemType.getCode());

                Document document = connection.get();

                Element cardContainer = document.getElementsByClass("card-container").first();

                getItems(cardContainer).forEach(item -> sendItemEmbed(item, event));
            }
            catch (Exception e)
            {
                e.printStackTrace();
                sendErrorMessage(event);
            }
        };

        executeRunnable(event, runnable);
    }

    public static ArrayList<Item> getItems(Element cardContainer)
    {
        Elements divs = cardContainer.getElementsByTag("div");

        ArrayList<Item> listOfItems = new ArrayList<>();

        for(int size = 0, count = 0; size < divs.size() && count < 5; size++)
        {
            Element div = divs.get(size);

            if (div.parent() == cardContainer)
                if (!div.hasClass("card-adsense"))
                {
                    listOfItems.add(new Item(divs.get(size)));
                    count++;
                }

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

    private void emptySearch(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Empty search!")
                .setDescription("You can not find " + itemType.getLongText() + " without specifying it!");

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendErrorMessage(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Error while getting the " + itemType.getName() + "!")
                .setDescription("An error happened! Does the " + itemType.getName() + " even exist? It may not be added yet.");

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }
}
