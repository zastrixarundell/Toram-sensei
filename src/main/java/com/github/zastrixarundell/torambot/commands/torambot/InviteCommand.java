package com.github.zastrixarundell.torambot.commands.torambot;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import com.github.zastrixarundell.torambot.commands.DiscordCommand;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

public class InviteCommand extends DiscordCommand
{

    public InviteCommand() { super("invite"); }

    @Override
    protected void runCommand(MessageCreateEvent event)
    {
        String name = event.getApi().getYourself().getName();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Invite link for " + name)
                .setDescription("Each invite helps this bot grow and will ultimately help this bot be used on a lot of " +
                        "Discord guilds, so every invite is appreciated! You can invite " + name + " on your server with [this link](" +
                        Values.inviteLink + ").");

        Parser.parseFooter(embed, event);
        Parser.parseThumbnail(embed, event);
        Parser.parseColor(embed, event);

        event.getChannel().sendMessage(embed);
    }

}
