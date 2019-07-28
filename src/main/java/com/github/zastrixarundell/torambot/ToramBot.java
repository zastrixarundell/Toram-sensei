package com.github.zastrixarundell.torambot;

import com.github.zastrixarundell.torambot.commands.corynwebsite.ItemCommand;
import com.github.zastrixarundell.torambot.commands.corynwebsite.LevelCommand;
import com.github.zastrixarundell.torambot.commands.corynwebsite.MonsterCommand;
import com.github.zastrixarundell.torambot.commands.discord.DonateCommand;
import com.github.zastrixarundell.torambot.commands.discord.HelpCommand;
import com.github.zastrixarundell.torambot.commands.discord.InviteCommand;
import com.github.zastrixarundell.torambot.commands.discord.toramrelated.CookingCommand;
import com.github.zastrixarundell.torambot.commands.discord.toramrelated.PointsCommand;
import com.github.zastrixarundell.torambot.commands.discord.toramrelated.ProficiencyCommand;
import com.github.zastrixarundell.torambot.commands.toramforums.DyeCommand;
import com.github.zastrixarundell.torambot.entities.ToramForumsUser;
import com.github.zastrixarundell.torambot.commands.toramwebsite.EventsCommand;
import com.github.zastrixarundell.torambot.commands.toramwebsite.LatestCommand;
import com.github.zastrixarundell.torambot.commands.toramwebsite.MaintenanceCommand;
import com.github.zastrixarundell.torambot.commands.toramwebsite.NewsCommand;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ToramBot
{

    public static void main(String[] args)
    {

        if(args.length == 0)
        {
            System.out.println("The token is not specified... shutting down!");
            return;
        }

        if(args.length > 1) { Values.setPrefix(args[1]); }

        System.out.println("Prefix set to: " + Values.getPrefix());

        //Only if it is hosted for everyone
        try
        {
            Values.setRanOnHostingService(Boolean.parseBoolean(args[2]));
        }
        catch (Exception ignore)
        {

        }

        String token = args[0];
        DiscordApi bot;
        try
        {
            bot = new DiscordApiBuilder().setToken(token).login().join();
        }
        catch (Exception e)
        {
            System.out.println("Error! Is the token correct?");
            return;
        }

        Values.getMavenVersion();

        bot.updateActivity("Starting up! Please wait!");

        bot.addListener(new NewsCommand());
        bot.addListener(new LatestCommand());
        bot.addListener(new MaintenanceCommand());
        bot.addListener(new EventsCommand());
        bot.addListener(new ItemCommand());
        bot.addListener(new LevelCommand());
        bot.addListener(new HelpCommand());
        bot.addListener(new MonsterCommand());
        bot.addListener(new PointsCommand());
        bot.addListener(new ProficiencyCommand());
        bot.addListener(new InviteCommand());
        bot.addListener(new DonateCommand());
        bot.addListener(new CookingCommand());

        try
        {
            ToramForumsUser api = new ToramForumsUser(token);
            api.setDye();
            api.close();
        }
        catch (Exception e)
        {
            System.out.println("An error happened while setting the dye data!");
        }

        if(Values.getDyeImage() != null)
            bot.addListener(new DyeCommand());

        System.out.println("Started! Type in \"stop\" to stop the bot!");

        String input;
        Scanner scanner = new Scanner(System.in);

        Timer activity = updateActivity(bot);
        Timer dyeImage = updateDyesImage(bot, token);

        while(true)
        {
            System.out.print("User input: ");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("stop"))
            {
                bot.disconnect();
                activity.cancel();
                dyeImage.cancel();
                return;
            }
        }

    }

    private static Timer updateActivity(DiscordApi bot)
    {
        Timer timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                bot.updateActivity(Values.getPrefix() + "help | " + bot.getServers().size() + " servers!");
            }
        };

        timer.schedule(task, 0, 30000);
        return timer;
    }

    private static Timer updateDyesImage(DiscordApi bot, String token)
    {
        Timer timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                try
                {
                    ToramForumsUser api = new ToramForumsUser(token);
                    api.setDye();
                    api.close();

                    if(Values.getDyeImage() == null)
                    {
                        System.out.println("An error happened while updating the dye data!");
                        bot.removeListener(DyeCommand.instance);
                        DyeCommand.instance = null;
                    }
                }
                catch (Exception e)
                {
                    System.out.println("An error happened while updating the dye data!");
                    bot.removeListener(DyeCommand.instance);
                    DyeCommand.instance = null;
                }
            }
        };

        timer.schedule(task, 1000*60*60*24, 1000*60*60*24);
        return timer;
    }

}
