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

package com.github.zastrixarundell.torambot.commands.search.items;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import com.github.zastrixarundell.torambot.commands.DiscordCommand;
import com.github.zastrixarundell.torambot.objects.toram.Item;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class UpgradeCommand extends DiscordCommand
{

    private static final int sizeOfPage = 2000;

    private Element body = null;

    public UpgradeCommand()
    {
        super("upgrade", "enhance");

        try
        {
            Document document = Jsoup.connect("http://coryn.club/item.php")
                    .data("special", "xtal")
                    .data("show", String.valueOf(sizeOfPage))
                    .get();

            Element table = document.getElementsByClass("table table-striped").first();
            body = table.getElementsByTag("tbody").first();
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
                if(body == null)
                {
                    Document document = Jsoup.connect("http://coryn.club/item.php")
                            .data("special", "xtal")
                            .data("show", String.valueOf(sizeOfPage))
                            .get();

                    Element table = document.getElementsByClass("table table-striped").first();
                    body = table.getElementsByTag("tbody").first();
                }

                List<Item> itemList = getItems(body, data);

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
                body = null;
            }
        };

        executeRunnable(event, runnable);
    }

    private ArrayList<Item> getItems(Element body, String data)
    {

        Elements trs = body.getElementsByTag("tr");

        ArrayList<Item> listOfItems = new ArrayList<>();

        for(int size = 0, count = 0; size < trs.size() && count < 5; size++)
            if(trs.get(size).parent() == body)
            {
                Item item = new Item(trs.get(size));

                if(String.join("\n", item.getStats()).toLowerCase().contains("upgrade for: " + data.toLowerCase()))
                {
                    listOfItems.add(item);
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
