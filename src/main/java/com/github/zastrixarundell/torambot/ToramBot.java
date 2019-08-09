package com.github.zastrixarundell.torambot;

import com.github.zastrixarundell.torambot.commands.HelpCommand;
import com.github.zastrixarundell.torambot.commands.gameinfo.*;
import com.github.zastrixarundell.torambot.commands.player.CookingCommand;
import com.github.zastrixarundell.torambot.commands.crafting.MatsCommand;
import com.github.zastrixarundell.torambot.commands.crafting.ProficiencyCommand;
import com.github.zastrixarundell.torambot.commands.player.LevelCommand;
import com.github.zastrixarundell.torambot.commands.player.PointsCommand;
import com.github.zastrixarundell.torambot.commands.search.items.ItemCommand;
import com.github.zastrixarundell.torambot.commands.search.items.weapons.*;
import com.github.zastrixarundell.torambot.commands.search.monsters.BossCommand;
import com.github.zastrixarundell.torambot.commands.search.monsters.MiniBossCommand;
import com.github.zastrixarundell.torambot.commands.search.monsters.MonsterCommand;
import com.github.zastrixarundell.torambot.commands.search.monsters.NormalMonsterComand;
import com.github.zastrixarundell.torambot.commands.torambot.DonateCommand;
import com.github.zastrixarundell.torambot.commands.torambot.InviteCommand;
import com.github.zastrixarundell.torambot.commands.torambot.SupportCommand;
import com.github.zastrixarundell.torambot.commands.search.items.extra.GemCommand;
import com.github.zastrixarundell.torambot.commands.search.items.extra.UpgradeCommand;
import com.github.zastrixarundell.torambot.commands.search.items.extra.XtalCommand;
import com.github.zastrixarundell.torambot.commands.search.items.gear.AdditionalCommand;
import com.github.zastrixarundell.torambot.commands.search.items.gear.ArmorCommand;
import com.github.zastrixarundell.torambot.commands.search.items.gear.ShieldCommand;
import com.github.zastrixarundell.torambot.commands.search.items.gear.SpecialCommand;

import com.github.zastrixarundell.torambot.entities.ToramForumsUser;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.*;

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

        //Crafting
        bot.addListener(new ProficiencyCommand());
        bot.addListener(new CookingCommand());
        bot.addListener(new MatsCommand());

        //items
        bot.addListener(new ItemCommand());
        bot.addListener(new AdditionalCommand());
        bot.addListener(new ArmorCommand());
        bot.addListener(new ArrowCommand());
        bot.addListener(new BowCommand());
        bot.addListener(new BowGunCommand());
        bot.addListener(new DaggerCommand());
        bot.addListener(new GemCommand());
        bot.addListener(new HalberdCommand());
        bot.addListener(new KatanaCommand());
        bot.addListener(new KnucklesCommand());
        bot.addListener(new MagicDeviceCommand());
        bot.addListener(new OneHandedSwordCommand());
        bot.addListener(new ShieldCommand());
        bot.addListener(new SpecialCommand());
        bot.addListener(new StaffCommand());
        bot.addListener(new TwoHandedSwordCommand());
        bot.addListener(new XtalCommand());
        bot.addListener(new UpgradeCommand());

        //monsters
        bot.addListener(new MonsterCommand());
        bot.addListener(new NormalMonsterComand());
        bot.addListener(new MiniBossCommand());
        bot.addListener(new BossCommand());

        //player
        bot.addListener(new LevelCommand());
        bot.addListener(new PointsCommand());

        //torambot
        bot.addListener(new HelpCommand());
        bot.addListener(new InviteCommand());
        bot.addListener(new DonateCommand());
        bot.addListener(new SupportCommand());

        //gameinfo
        bot.addListener(new NewsCommand());
        bot.addListener(new LatestCommand());
        bot.addListener(new MaintenanceCommand());
        bot.addListener(new EventsCommand());

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

            int status = 0;

            @Override
            public void run()
            {

                switch(status)
                {
                    case 0:

                        List<String> doNotCheckThese =Arrays.asList
                                (
                                    "264445053596991498",
                                    "446425626988249089"
                                );

                        long userCount = 0;

                        for (Server server : bot.getServers())
                            if(!doNotCheckThese.contains(server.getIdAsString()))
                                for (User user : server.getMembers())
                                    if (!user.isBot())
                                        userCount++;

                        bot.updateActivity(Values.getPrefix() + "help | " + userCount + " users!");
                        break;
                    case 1:
                        bot.updateActivity(Values.getPrefix() + "invite | " + bot.getServers().size() + " servers!");
                        break;
                }

                status ++;
                status = status % 2;
            }
        };

        timer.schedule(task, 0, 1000*60);
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
                    System.out.println("Starting dye!");
                    ToramForumsUser user = new ToramForumsUser(token);
                    user.setDye();
                    user.close();

                    if(Values.getDyeImage() == null)
                    {
                        System.out.println("An error happened while updating the dye data!");

                        if(DyeCommand.instance != null)
                        {
                            bot.removeListener(DyeCommand.instance);
                            DyeCommand.instance = null;
                        }
                    }
                    else
                        if(DyeCommand.instance == null)
                        {
                            bot.addListener(new DyeCommand());
                            System.out.println("Updated dyes!");
                        }
                }
                catch (Exception e)
                {
                    System.out.println("An error happened while updating the dye data!");
                    e.printStackTrace();

                    if(DyeCommand.instance != null)
                    {
                        bot.removeListener(DyeCommand.instance);
                        DyeCommand.instance = null;
                    }
                }
            }
        };

        timer.schedule(task,0, 250*60*60*24);
        return timer;
    }

}
