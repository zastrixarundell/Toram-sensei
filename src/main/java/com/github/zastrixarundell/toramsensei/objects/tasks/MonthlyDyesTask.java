package com.github.zastrixarundell.toramsensei.objects.tasks;

import org.javacord.api.DiscordApi;

import java.util.TimerTask;

public class MonthlyDyesTask extends TimerTask
{

    private DiscordApi bot;

    public MonthlyDyesTask(DiscordApi bot) { this.bot = bot; }

    @Override
    public void run()
    {
    }
}
