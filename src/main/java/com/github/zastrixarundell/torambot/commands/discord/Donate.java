package com.github.zastrixarundell.torambot.commands.discord;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Donate implements MessageCreateListener
{

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent)
    {

        if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(Values.getPrefix() + "donate"))
            return;

        if (!messageCreateEvent.getMessageAuthor().isRegularUser())
            return;

        String name = messageCreateEvent.getApi().getYourself().getName();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Donate for " + name)
                .setDescription("Thank you! This really helps " + name + " to be continued! You can donate on " +
                "[this link](" + Values.donationLink + ")!");

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseDonationImage(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);

    }

}
