package com.github.zastrixarundell.torambot.commands.torambot;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class SupportCommand implements MessageCreateListener
{

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent)
    {

        if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(Values.getPrefix() + "support"))
            return;

        if (!messageCreateEvent.getMessageAuthor().isRegularUser())
            return;

        String name = messageCreateEvent.getApi().getYourself().getName();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Support link for: " + name)
                .setDescription("You can [join this Discord server](" + Values.supportLink + ") if you have any" +
                        " issues, want to suggest a feature or just join out of fun!");


        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);

    }

}
