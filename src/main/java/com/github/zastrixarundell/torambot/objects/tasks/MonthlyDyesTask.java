package com.github.zastrixarundell.torambot.objects.tasks;

import com.github.zastrixarundell.torambot.Values;
import com.github.zastrixarundell.torambot.commands.gameinfo.MonthlyCommand;
import com.github.zastrixarundell.torambot.entities.ToramForumsUser;
import org.javacord.api.DiscordApi;

import java.util.TimerTask;

public class MonthlyDyesTask extends TimerTask
{

    private DiscordApi bot;

    public MonthlyDyesTask(DiscordApi bot) { this.bot = bot; }

    @Override
    public void run()
    {
        try
        {
            System.out.println("Starting forums user!");
            ToramForumsUser user = new ToramForumsUser(bot.getToken());
            System.out.println("Starting monthly dyes!");
            user.setDye();
            System.out.println("Finished monthly dyes!");
            user.close();

            if(Values.getDyeImages() == null)
            {
                System.out.println("There are monthly dyes!");

                if(MonthlyCommand.instance != null)
                {
                    bot.removeListener(MonthlyCommand.instance);
                    MonthlyCommand.instance = null;
                }
            }
            else
            if(MonthlyCommand.instance == null)
            {
                bot.addListener(new MonthlyCommand());
                System.out.println("Updated monthly dyes!");
            }
        }
        catch (Exception e)
        {
            Values.setDyeImages(null);
            System.out.println("An error happened while updating the monthly dye data!");
            e.printStackTrace();

            if(MonthlyCommand.instance != null)
            {
                bot.removeListener(MonthlyCommand.instance);
                MonthlyCommand.instance = null;
            }
        }
    }
}
