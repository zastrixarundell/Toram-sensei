package com.github.zastrixarundell.torambot;

import com.github.zastrixarundell.torambot.commands.toramwebsite.Events;
import com.github.zastrixarundell.torambot.commands.toramwebsite.Latest;
import com.github.zastrixarundell.torambot.commands.toramwebsite.Maintenance;
import com.github.zastrixarundell.torambot.commands.toramwebsite.News;
import com.github.zastrixarundell.torambot.commands.corynwebsite.Item;
import com.github.zastrixarundell.torambot.commands.corynwebsite.Level;
import com.github.zastrixarundell.torambot.commands.corynwebsite.Monster;
import com.github.zastrixarundell.torambot.commands.discord.Help;
import com.github.zastrixarundell.torambot.commands.discord.Points;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ToramBot
{

    private static String prefix = "?";

    public static String getPrefix()
    {
        return prefix;
    }

    public static final String supportURL = "http://corneey.com/w2ObhY";

    public static String logo()
    {
            return "https://toramonline.com/index.php?media/toram-online-logo.50/full&d=1463410056";
    }

    private static boolean ranOnHostingService = false;

    public static void main(String[] args)
    {

        if(args.length == 0)
        {
            System.out.println("The token is not specified... shutting down!");
            return;
        }

        if(args.length > 1)
        {
            prefix = args[1];
            System.out.println("Prefix set to: " + prefix);
        }

        try
        {
            ranOnHostingService = Boolean.valueOf(args[2]);
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

        bot.addListener(new News());
        bot.addListener(new Latest());
        bot.addListener(new Maintenance());
        bot.addListener(new Events());
        bot.addListener(new Item());
        bot.addListener(new Level());
        bot.addListener(new Help());
        bot.addListener(new Monster());
        bot.addListener(new Points());

        System.out.println("Started! Type in \"stop\" to stop the bot!");

        String input;
        Scanner scanner = new Scanner(System.in);



        Timer timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                bot.updateActivity(prefix + "help | " + bot.getServers().size() + " servers!");
            }
        };

        timer.schedule(task, 0, 30000);

        while(true)
        {
            System.out.print("User input: ");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("stop"))
            {
                bot.disconnect();
                timer.cancel();
                return;
            }
        }

    }

    public static boolean isRanOnHostingService()
    {
        return ranOnHostingService;
    }
}
