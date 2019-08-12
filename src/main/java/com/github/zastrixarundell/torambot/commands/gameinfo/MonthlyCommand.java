package com.github.zastrixarundell.torambot.commands.gameinfo;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.joda.time.DateTime;
import org.joda.time.Period;

public class MonthlyCommand implements MessageCreateListener
{
    public static MonthlyCommand instance = null;

    public MonthlyCommand()
    {
        instance = this;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent)
    {
        if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(Values.getPrefix() + "monthly"))
            if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(Values.getPrefix() + "month"))
            return;

        if (!messageCreateEvent.getMessageAuthor().isRegularUser())
            return;

        Runnable runnable = () ->
        {
            DateTime time = new DateTime();
            Period period = new Period(Values.getLastDyeUpdate(), time);

            for(int i = 0; i < Values.getDyeImages().length; i++)
            {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Latest monthly dyes" + (Values.getDyeImages().length <= 1 ? "" : " (" + (i+1) + "/" + Values.getDyeImages().length + ")"))
                        .setDescription("Here is the image of the latest monthly dyes!\n\n\n" +
                                "Note: This can be late so check the title of the image.");

                Parser.parseColor(embed, messageCreateEvent);
                Parser.parseThumbnail(embed, messageCreateEvent);
                embed.setImage(Values.getDyeImages()[i]);

                int hours = period.getHours();
                int minutes = period.getMinutes();

                embed.setFooter("Last check was " + hours + (hours == 1 ? " hour" : " hours") + " and " +
                        minutes + (minutes == 1 ? " minute" : " minutes") + " ago.");

                messageCreateEvent.getChannel().sendMessage(embed);
            }

        };

        (new Thread(runnable)).start();

    }

}
