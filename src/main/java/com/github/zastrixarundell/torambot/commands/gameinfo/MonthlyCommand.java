package com.github.zastrixarundell.torambot.commands.gameinfo;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import com.github.zastrixarundell.torambot.commands.DiscordCommand;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.joda.time.DateTime;
import org.joda.time.Period;

public class MonthlyCommand extends DiscordCommand
{

    public static MonthlyCommand instance = null;

    public MonthlyCommand()
    {
        super("monthly", "month");
    }

    @Override
    protected void runCommand(MessageCreateEvent event)
    {
        Runnable runnable = () ->
        {
            DateTime time = new DateTime();
            Period period = new Period(Values.getLastDyeUpdate(), time);

            for(int i = 0; i < Values.getDyeImages().size(); i++)
            {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Latest monthly dyes" + (Values.getDyeImages().size() <= 1 ? "" : " (" + (i+1) + "/" + Values.getDyeImages().size() + ")"))
                        .setDescription("Here is the image of the latest monthly dyes!\n\n\n" +
                                "Note: This can be late so check the title of the image.");

                Parser.parseColor(embed, event);
                Parser.parseThumbnail(embed, event);
                embed.setImage(Values.getDyeImages().get(i));

                int hours = period.getHours();
                int minutes = period.getMinutes();

                embed.setUrl("https://toramonline.com/index.php?threads/weapon-shield-dyes-july-2019-white.23149/");

                embed.setFooter("Last check was " + hours + (hours == 1 ? " hour" : " hours") + " and " +
                        minutes + (minutes == 1 ? " minute" : " minutes") + " ago.");

                event.getChannel().sendMessage(embed);
            }

        };

        executeRunnable(event, runnable);
    }

}
