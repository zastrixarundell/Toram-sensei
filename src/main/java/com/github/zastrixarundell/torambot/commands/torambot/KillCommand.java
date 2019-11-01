/*
 *             DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *                     Version 2, December 2004
 *
 * Copyright (C) 2019, Zastrix Arundell, https://github.com/ZastrixArundell
 *
 *  Everyone is permitted to copy and distribute verbatim or modified
 *  copies of this license document, and changing it is allowed as long
 *  as the name is changed.
 *
 *             DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *    TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *   0. You just DO WHAT THE FUCK YOU WANT TO.
 *
 *
 */

package com.github.zastrixarundell.torambot.commands.torambot;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import com.github.zastrixarundell.torambot.commands.DiscordCommand;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.ArrayList;

public class KillCommand extends DiscordCommand
{

    public KillCommand() { super("kill"); }

    @Override
    protected void runCommand(MessageCreateEvent messageCreateEvent)
    {
        if(messageCreateEvent.getMessageAuthor().getIdAsString().equals("192300733234675722"))
        {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Killing bot!")
                    .setDescription("Safety procedure has been activated!");

            Parser.parseFooter(embed, messageCreateEvent);
            Parser.parseThumbnail(embed, messageCreateEvent);
            Parser.parseColor(embed, messageCreateEvent);

            messageCreateEvent.getChannel().sendMessage(embed);
            messageCreateEvent.getApi().disconnect();
        }
    }
}
