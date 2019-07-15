package com.github.zastrixarundell.torambot.commands.corynwebsite;

import com.github.zastrixarundell.torambot.ToramBot;
import com.github.zastrixarundell.torambot.objects.ItemObject;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.util.ArrayList;

public class Item implements MessageCreateListener
{

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent)
    {

        if (!messageCreateEvent.getMessageAuthor().isRegularUser())
            return;

        if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(ToramBot.getPrefix() + "item"))
            return;

        ArrayList<String> arguments = new ArrayList<>();

        for (int i = 1; i < messageCreateEvent.getMessageContent().split(" ").length; i++)
            arguments.add(messageCreateEvent.getMessageContent().split(" ")[i]);

        if (arguments.isEmpty())
        {
            emptySearch(messageCreateEvent);
            return;
        }

        if (ToramBot.TimeOutCoryn(messageCreateEvent))
            return;

        String data = String.join(" ", arguments);

        try
        {
            Document document = Jsoup.connect("http://coryn.club/item.php").data("name", data).get();
            Element table = document.getElementsByClass("table table-striped").first();
            Element body = table.getElementsByTag("tbody").first();

            Elements trs = body.getElementsByTag("tr");

            ArrayList<Element> items = new ArrayList<>();

            trs.forEach(element -> {
                if (element.parent() == body) items.add(element);
            });

            int count = 0;

            for (Element ItemHtml : items)
            {

                if (count >= 5)
                    break;

                count++;

                ItemObject item = new ItemObject(ItemHtml);

                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle(item.getName())
                        .setThumbnail("http://coryn.club/images/cc_logo.gif")
                        .addInlineField("NPC sell price:", item.getPrice())
                        .addInlineField("Processed into:", item.getProc());

                if(ToramBot.isRanOnHostingService())
                    embed.setFooter("Support me by going on the link: " + ToramBot.supportURL);

                String stats = String.join("\n", item.getStats());

                embed.addField("Stats/Effect:", stats);

                StringBuilder stringBuilder = new StringBuilder();

                {

                    for (int i = 0; i < item.getObtainedFrom().size() && i < 10; i++)
                    {
                        String key = item.getObtainedFrom().get(i);
                        stringBuilder.append(i == 0 ? key : "\n" + key);
                    }
                }

                embed.addInlineField("Obtained from:", stringBuilder.toString());

                if (item.getApp() != null)
                    embed.setThumbnail(item.getApp());

                if (messageCreateEvent.getServer().isPresent())
                    if (messageCreateEvent.getServer().get().getHighestRole(messageCreateEvent.getApi().getYourself()).isPresent())
                    {
                        Role role = messageCreateEvent.getServer().get().getHighestRole(messageCreateEvent.getApi().getYourself()).get();
                        if (role.getColor().isPresent())
                        {
                            Color color = role.getColor().get();
                            embed.setColor(color);
                        }
                    }
                messageCreateEvent.getChannel().sendMessage(embed);
            }
        }
        catch (Exception e)
        {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Error while getting item!")
                    .setDescription("An error happened! Does the item even exist? It may not be yet added into " +
                            "coryn.club!")
                    .setThumbnail("http://coryn.club/images/cc_logo.gif");

            if(ToramBot.isRanOnHostingService())
                embed.setFooter("Support me by going on the link: " + ToramBot.supportURL);

            if (messageCreateEvent.getServer().isPresent())
                if (messageCreateEvent.getServer().get().getHighestRole(messageCreateEvent.getApi().getYourself()).isPresent())
                {
                    Role role = messageCreateEvent.getServer().get().getHighestRole(messageCreateEvent.getApi().getYourself()).get();
                    if (role.getColor().isPresent())
                    {
                        Color color = role.getColor().get();
                        embed.setColor(color);
                    }
                }

            messageCreateEvent.getChannel().sendMessage(embed);
        }

        ToramBot.updateTime();
    }

    private void emptySearch(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Empty search!")
                .setThumbnail("http://coryn.club/images/cc_logo.gif")
                .setDescription("You can not find an item on coryn.club without specifying which item!");

        if(ToramBot.isRanOnHostingService())
            embed.setFooter("Support me by going on the link: " + ToramBot.supportURL);

        if (messageCreateEvent.getServer().isPresent())
            if (messageCreateEvent.getServer().get().getHighestRole(messageCreateEvent.getApi().getYourself()).isPresent())
            {
                Role role = messageCreateEvent.getServer().get().getHighestRole(messageCreateEvent.getApi().getYourself()).get();
                if (role.getColor().isPresent())
                {
                    Color color = role.getColor().get();
                    embed.setColor(color);
                }
            }

        messageCreateEvent.getChannel().sendMessage(embed);

    }

}
