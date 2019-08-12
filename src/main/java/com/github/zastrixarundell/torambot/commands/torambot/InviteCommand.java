package com.github.zastrixarundell.torambot.commands.torambot;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class InviteCommand implements MessageCreateListener
{

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent)
    {

        if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(Values.getPrefix() + "invite"))
            return;

        if (!messageCreateEvent.getMessageAuthor().isRegularUser())
            return;

        String name = messageCreateEvent.getApi().getYourself().getName();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Invite link for " + name)
                .setDescription("Each invite helps this bot grow and will ultimately help this bot be used on a lot of " +
                        "Discord guilds, so every invite is appreciated! You can invite " + name + " on your server with [this link](" +
                        Values.inviteLink + ").");

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);

    }

}
