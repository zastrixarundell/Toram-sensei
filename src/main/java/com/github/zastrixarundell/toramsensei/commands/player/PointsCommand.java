package com.github.zastrixarundell.toramsensei.commands.player;

import com.github.zastrixarundell.toramsensei.Parser;
import com.github.zastrixarundell.toramsensei.Values;
import com.github.zastrixarundell.toramsensei.commands.DiscordCommand;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.ArrayList;

public class PointsCommand extends DiscordCommand
{

    public PointsCommand()
    {
        super("points");
    }

    @Override
    protected void runCommand(MessageCreateEvent event)
    {
        ArrayList<String> arguments = new ArrayList<>();

        for (int i = 1; i < event.getMessageContent().split(" ").length; i++)
            arguments.add(event.getMessageContent().split(" ")[i]);

        if (arguments.size() < 3)
        {
            sendCommandUsage(event);
            return;
        }

        int current, level, target;

        try
        {
            current = Integer.parseInt(arguments.get(0));
            level = Integer.parseInt(arguments.get(1)) + 1;
            target = Integer.parseInt(arguments.get(2));
        }
        catch (NumberFormatException e)
        {
            sendMessage(event, "Can't determine level!", "Are you sure you formatted this correct? There was" +
                    " an error while turning the values into numbers!");
            return;
        }

        for (; level <= target; level++)
            current += (level % 5) == 0 ? 2 : 1;

        sendMessage(event, "Calculated skill points: " + current, "This is the amount of " +
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

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendMessage(MessageCreateEvent messageCreateEvent, String name, String description)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(name)
                .setDescription(description);

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }
}
