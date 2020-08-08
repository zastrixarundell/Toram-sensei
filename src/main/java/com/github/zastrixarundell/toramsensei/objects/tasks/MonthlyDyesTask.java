package com.github.zastrixarundell.toramsensei.objects.tasks;

import com.github.zastrixarundell.toramsensei.Database;
import com.github.zastrixarundell.toramsensei.Helpers;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class MonthlyDyesTask extends TimerTask
{

    private final DiscordApi bot;
    private Optional<String> imageUrl = Optional.empty();

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

            if(!monthlyImage.imageOptional.isPresent())
                throw new Exception();

            System.out.println("Old hash is: " + cachedHash.orElse("undefined"));
            System.out.println("New hash is: " + monthlyImage.htmlHash);

            if(cachedHash.isPresent())
            {
                if(!monthlyImage.htmlHash.equals(cachedHash.get()))
                    write(connection, monthlyImage);
            }
            else write(connection, monthlyImage);
        }
        catch (Exception ignore)
        {

        }
    }

    private void write(Connection connection, Helpers.MonthlyHashObject hashObject) throws SQLException
    {
        bot.getServers().forEach(server -> server.getTextChannels().forEach(channel -> {
            if(channel.getName().equalsIgnoreCase("toram-sensei-dyes"))
                try
                {
                    sendMessage(channel, hashObject);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
        }));

        Database.setHashElement(connection, hashObject.htmlHash);
    }

    private void sendMessage(TextChannel channel, Helpers.MonthlyHashObject hashObject) throws ExecutionException, InterruptedException
    {
        if(!imageUrl.isPresent())
        {
            EmbedBuilder builder = new EmbedBuilder()
                    .setTitle("Current last monthly dyes!")
                    .setUrl("https://toram-id.info/dye")
                    .setDescription("This is the latest monthly dye drop. The current language is Indonesian but will be " +
                            "translated to English soon!")
                    .setImage(hashObject.imageOptional.get());

            Message message = channel.sendMessage(builder).get();

            if (message.getEmbeds().size() == 0)
                return;

            Embed embed = message.getEmbeds().get(0);

            embed.getImage().ifPresent(embedImage -> imageUrl = Optional.of(embedImage.getUrl().toString()));
        }
        else
        {
            EmbedBuilder builder = new EmbedBuilder()
                    .setTitle("Current last monthly dyes!")
                    .setUrl("https://toram-id.info/dye")
                    .setDescription("This is the latest monthly dye drop. The current language is Indonesian but will be " +
                            "translated to English soon!")
                    .setImage(imageUrl.get());

            channel.sendMessage(builder);
        }
    }
}
