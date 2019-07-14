package com.github.zastrixarundell.torambot.commands.corynwebsite;

import com.github.zastrixarundell.torambot.ToramBot;
import com.github.zastrixarundell.torambot.objects.MonsterObject;
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

public class Monster implements MessageCreateListener
{

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent)
    {

        if (!messageCreateEvent.getMessageAuthor().isRegularUser())
            return;

        if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(ToramBot.getPrefix() + "monster"))
            return;

        ArrayList<String> arguments = new ArrayList<>();

        for (int i = 1; i < messageCreateEvent.getMessageContent().split(" ").length; i++)
            arguments.add(messageCreateEvent.getMessageContent().split(" ")[i]);

        if (arguments.isEmpty())
        {
            sendCommandUsage(messageCreateEvent);
            return;
        }

        if (ToramBot.TimeOutCoryn(messageCreateEvent))
            return;

        String data = String.join(" ", arguments);

        try
        {
            Document document = Jsoup.connect("http://coryn.club/monster.php").data("name", data).get();
            Elements tables = document.getElementsByClass("table table-striped");
            Element body = tables.first().getElementsByTag("tbody").first();

            for (Element element : body.getElementsByTag("tr"))
            {
                if(element.parent() != body)
                    continue;

                MonsterObject object = new MonsterObject(element);

                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle(object.getName())
                        .setThumbnail("http://coryn.club/images/cc_logo.gif")
                        .addInlineField("HP:", object.getHp())
                        .addInlineField("Element:", object.getElement())
                        .addInlineField("EXP:", object.getExp())
                        .addInlineField("Tamable:", object.getTamable())
                        .addInlineField("Spawns at:", object.getLocation());

                String drops = String.join("\n", object.getItems());

                embed.addField("Drops:", drops);

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
            sendError(messageCreateEvent, "Error while getting the monster!",
                    "An error happened while getting the monster info! Does the specified monster even " +
                            "exist?");
        }

        ToramBot.updateTime();
    }

    private void sendCommandUsage(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Monster Command: ")
                .setThumbnail("http://coryn.club/images/cc_logo.gif")
                .setDescription("You can use this command to get " +
                        "info about a monster!")
                .addField(ToramBot.getPrefix() + "monster [monster]", "This command is used to info about a monster!");

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

    private void sendError(MessageCreateEvent messageCreateEvent, String name, String description)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(name)
                .setThumbnail("http://coryn.club/images/cc_logo.gif")
                .setDescription(description);

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
