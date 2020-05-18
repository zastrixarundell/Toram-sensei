package com.github.zastrixarundell.toramsensei.commands.torambot;

import com.github.zastrixarundell.toramsensei.Parser;
import com.github.zastrixarundell.toramsensei.Values;
import com.github.zastrixarundell.toramsensei.commands.DiscordCommand;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

public class SupportCommand extends DiscordCommand
{

    public SupportCommand() { super("support"); }

    @Override
    protected void runCommand(MessageCreateEvent event)
    {
        String name = event.getApi().getYourself().getName();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Support link for: " + name)
                .setDescription("You can [join this Discord server](" + Values.supportLink + ") if you have any" +
                        " issues, want to suggest a feature or just join out of fun!");


        Parser.parseFooter(embed, event);
        Parser.parseThumbnail(embed, event);
        Parser.parseColor(embed, event);

        event.getChannel().sendMessage(embed);
    }

}
