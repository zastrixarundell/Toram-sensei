package com.github.zastrixarundell.torambot.commands.torambot;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import com.github.zastrixarundell.torambot.commands.DiscordCommand;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

public class DonateCommand extends DiscordCommand
{

    public DonateCommand() { super("donate"); }

    @Override
    protected void runCommand(MessageCreateEvent event)
    {
        String name = event.getApi().getYourself().getName();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Donate to suport: " + name)
                .setDescription("Thank you! This really helps " + name + " to be continued! You can donate on " +
                "[this link](" + Values.donationLink + ")!");

        Parser.parseFooter(embed, event);
        Parser.parseDonationThumbnail(embed, event);
        Parser.parseColor(embed, event);

        event.getChannel().sendMessage(embed);

    }

}
