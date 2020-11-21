package com.github.zastrixarundell.toramsensei.objects.tasks;

import com.github.zastrixarundell.toramsensei.Values;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TimerTask;
import java.util.stream.Stream;

public class MessageTask extends TimerTask
{

    private DiscordApi bot;
    private int status = 0;

    public MessageTask(DiscordApi bot) { this.bot = bot; }

    @Override
    public void run()
    {
        updateCount(bot);

        if(Values.getApi() != null)
            Values.getApi().setStats(bot.getServers().size());

        status = status % (Values.getApi() != null ? 3 : 2);

        switch(status)
        {
            case 0:
                bot.updateActivity(Values.getPrefix() + "help | " + Values.getUserCount() + " users!");
                break;
            case 1:
                bot.updateActivity(Values.getPrefix() + "invite | " + Values.getGuildCount() + " servers!");
                break;
            case 2:
                Values.getApi().getBot("600302983305101323").whenComplete((bot1, throwable) -> bot.updateActivity(Values.getPrefix() + "vote | " + bot1.getMonthlyPoints() + " votes this month!"));
        }

        status ++;
    }

    private static void updateCount(DiscordApi bot)
    {
        Collection<Server> servers = bot.getServers();
        Values.setGuildCount(((int) servers.stream().distinct().count()));
        Values.setUserCount(servers.stream().mapToInt(Server::getMemberCount).sum());
    }
}
