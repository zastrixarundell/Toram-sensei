package com.github.zastrixarundell.toramsensei.objects.tasks;

import com.github.zastrixarundell.toramsensei.Database;
import com.github.zastrixarundell.toramsensei.Helpers;
import org.javacord.api.DiscordApi;

import java.sql.Connection;
import java.util.Optional;
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
            Connection connection = Database.getConnection();

            Database.createHashTable(connection);

            Optional<String> cachedHash = Database.getCachedHash(connection);

            Helpers.MonthlyHashObject monthlyImage = Helpers.getMonthlyImage();

            Database.setHashElement(connection, "AAAAAA");
        }
        catch (Exception e)
        {

        }
    }
}
