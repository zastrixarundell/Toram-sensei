package com.github.zastrixarundell.torambot.commands.discord;

import com.github.zastrixarundell.torambot.ToramBot;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.util.ArrayList;

public class Points implements MessageCreateListener
{

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent)
    {

        if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(ToramBot.getPrefix() + "points"))
            return;

        if (!messageCreateEvent.getMessageAuthor().isRegularUser())
            return;

        ArrayList<String> arguments = new ArrayList<>();

        for (int i = 1; i < messageCreateEvent.getMessageContent().split(" ").length; i++)
            arguments.add(messageCreateEvent.getMessageContent().split(" ")[i]);

        if (arguments.size() < 3)
        {
            sendCommandUsage(messageCreateEvent);
            return;
        }

        int current, level, target;

        try
        {
            current = Integer.valueOf(arguments.get(0));
            level = Integer.valueOf(arguments.get(1)) + 1;
            target = Integer.valueOf(arguments.get(2));
        }
        catch (NumberFormatException e)
        {
            sendMessage(messageCreateEvent, "Can't determine level!", "Are you sure you formatted this correct? There was" +
                    " an error while turning the values into numbers!");
            return;
        }

        for (; level <= target; level++)
            current += (level % 5) == 0 ? 2 : 1;

        sendMessage(messageCreateEvent, "Calculated skill points: " + current, "This is the amount of " +
                "skill points you will have.");
    }

    private void sendCommandUsage(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Points Command: ")
                .setThumbnail("http://coryn.club/images/cc_logo.gif")
                .setDescription("You can use this command to get " +
                        "how many skill points will you have at which level!")
                .addField(ToramBot.getPrefix() + "points [current skill points] [your level] [target level]",
                        "All of the arguments need to be present or else it will not work!");

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

    private void sendMessage(MessageCreateEvent messageCreateEvent, String name, String description)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(name)
                .setThumbnail(ToramBot.logo())
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
