package com.github.zastrixarundell.torambot.commands.discord;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.ArrayList;

public class Points implements MessageCreateListener
{

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent)
    {

        if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(Values.getPrefix() + "points"))
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
                .setDescription("You can use this command to get " +
                        "how many skill points will you have at which level!")
                .addField(Values.getPrefix() + "points [current skill points] [your level] [target level]",
                        "All of the arguments need to be present or else it will not work!");

        Parser.parsePrimaryThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendMessage(MessageCreateEvent messageCreateEvent, String name, String description)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(name)
                .setDescription(description);

        Parser.parsePrimaryThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }
}
