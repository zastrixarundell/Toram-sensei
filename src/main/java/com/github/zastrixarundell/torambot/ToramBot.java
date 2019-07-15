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
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Date;
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

    private static Date postDate = new Date();

    public static final String supportURL = "http://corneey.com/w2ObhY";

    public static boolean TimeOutCoryn(MessageCreateEvent messageCreateEvent)
    {
        boolean cancel = false;

        Date currentDate = new Date();

        if(currentDate.getTime() <= postDate.getTime() + 1000)
            cancel = true;

        if (cancel)
        {
            long remainingLong = postDate.getTime() + 1000 - currentDate.getTime();
            float remaining = remainingLong / 1000f;

            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Still on timeout!")
                    .setDescription("You need to wait for " + df.format(remaining) + " second(s) to perform a command." +
                            " This is here just so the site isn't spammed.")
                    .setThumbnail(ToramBot.logo());

            if(messageCreateEvent.getServer().isPresent())
                if(messageCreateEvent.getServer().get().getHighestRole(messageCreateEvent.getApi().getYourself()).isPresent())
                {
                    Role role = messageCreateEvent.getServer().get().getHighestRole(messageCreateEvent.getApi().getYourself()).get();
                    if(role.getColor().isPresent())
                    {
                        Color color = role.getColor().get();
                        embed.setColor(color);
                    }
                }

            messageCreateEvent.getChannel().sendMessage(embed);
        }

        return cancel;
    }

    public static boolean TimeOut(MessageCreateEvent messageCreateEvent)
    {
        boolean cancel = false;

        Date currentDate = new Date();

        if(currentDate.getTime() <= postDate.getTime() + 5000)
            cancel = true;

        if (cancel)
        {
            long remainingLong = postDate.getTime() + 5000 - currentDate.getTime();
            float remaining = remainingLong / 1000f;

            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Still on timeout!")
                    .setDescription("You need to wait for " + df.format(remaining) + " second(s) to perform a command." +
                            " This is here just so the site isn't spammed.")
                    .setThumbnail(ToramBot.logo());

            if(messageCreateEvent.getServer().isPresent())
                if(messageCreateEvent.getServer().get().getHighestRole(messageCreateEvent.getApi().getYourself()).isPresent())
                {
                    Role role = messageCreateEvent.getServer().get().getHighestRole(messageCreateEvent.getApi().getYourself()).get();
                    if(role.getColor().isPresent())
                    {
                        Color color = role.getColor().get();
                        embed.setColor(color);
                    }
                }

            messageCreateEvent.getChannel().sendMessage(embed);
        }

        return cancel;
    }

    public static void updateTime()
    {
        postDate = new Date();
    }

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
                bot.updateActivity(prefix + "help");
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
