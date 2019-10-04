package com.github.zastrixarundell.torambot;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.util.ArrayList;

public class Parser
{

    public static void parseThumbnail(EmbedBuilder builder, MessageCreateEvent messageCreateEvent)
    {
        //messageCreateEvent might be used in the future.
        builder.setThumbnail("https://raw.githubusercontent.com/ZastrixArundell/ToramBot/master/images/potum.png");
    }

    public static void parseMonsterThumbnail(EmbedBuilder builder, MessageCreateEvent messageCreateEvent)
    {
        //messageCreateEvent might be used in the future.
        builder.setThumbnail("https://raw.githubusercontent.com/ZastrixArundell/ToramBot/master/images/fight.gif");
    }

    public static void parseColorList(EmbedBuilder builder, MessageCreateEvent messageCreateEvent)
    {
        //messageCreateEvent might be used in the future.
        builder.setImage("https://raw.githubusercontent.com/ZastrixArundell/ToramBot/master/images/dyes.jpg");
    }

    public static void parseDonationThumbnail(EmbedBuilder builder, MessageCreateEvent messageCreateEvent)
    {
        //messageCreateEvent might be used in the future.
        builder.setThumbnail(Values.donationLogo);
    }

    public static void parseFooter(EmbedBuilder builder, MessageCreateEvent messageCreateEvent)
    {
        //messageCreateEvent might be used in the future.
    }

    public static void parseColor(EmbedBuilder embed, MessageCreateEvent messageCreateEvent)
    {
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
    }

    public static ArrayList<String> argumentsParser(MessageCreateEvent messageCreateEvent)
    {
        ArrayList<String> arguments = new ArrayList<>();

        for (int i = 1; i < messageCreateEvent.getMessageContent().split(" ").length; i++)
            arguments.add(messageCreateEvent.getMessageContent().split(" ")[i]);

        return arguments;
    }

    public static String nameParser(String name)
    {
        String[] nameParts = name.split(" ");

        int toEdit = -1;

        for(int counter = nameParts.length - 1; counter >= 0; counter--)
            if(nameParts[counter].contains("["))
            {
                toEdit = counter;
                break;
            }

        if(toEdit == -1)
            return name;

        while(String.valueOf(nameParts[toEdit].toCharArray()[0]).equalsIgnoreCase(" ") ||
                ((int) nameParts[toEdit].toCharArray()[0]) == 160)
            nameParts[toEdit] = nameParts[toEdit].substring(1);

        return String.join(" ", nameParts);
    }

}
