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

package com.github.zastrixarundell.toramsensei.commands;

import com.github.zastrixarundell.toramsensei.Values;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class DiscordCommand implements MessageCreateListener
{
    private Set<String> callers;

    protected DiscordCommand(String ... callers)
    {
        for (int i = 0; i < callers.length; i++)
            callers[i] = Values.getPrefix() + callers[i];

        this.callers = new HashSet<>(Arrays.asList(callers));
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event)
    {
        if(!callers.contains(getCommandName(event).toLowerCase()))
            return;

        if (!event.getMessageAuthor().isRegularUser())
            return;

        runCommand(event);
    }

    private String getCommandName(MessageCreateEvent event) { return event.getMessageContent().split(" ")[0]; }

    protected abstract void runCommand(MessageCreateEvent event);

    protected void executeRunnable(MessageCreateEvent event, Runnable runnable)
    {
        event.getApi().getThreadPool().getExecutorService().execute(runnable);
    }
}
