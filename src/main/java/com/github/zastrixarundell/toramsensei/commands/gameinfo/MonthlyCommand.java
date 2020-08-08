package com.github.zastrixarundell.toramsensei.commands.gameinfo;

import com.github.zastrixarundell.toramsensei.Helpers;
import com.github.zastrixarundell.toramsensei.Parser;
import com.github.zastrixarundell.toramsensei.Values;
import com.github.zastrixarundell.toramsensei.commands.DiscordCommand;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.image.BufferedImage;
import java.util.Optional;

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
            Optional<BufferedImage> imageOptional = Helpers.getMonthlyImage();

            if(imageOptional.isPresent())
                sendDyeMessage(event, imageOptional.get());
            else
                sendErrorMessage(event);
        };

        executeRunnable(event, runnable);
    }

    private void sendDyeMessage(MessageCreateEvent messageCreateEvent, BufferedImage image)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Currently monthly dye drops!")
                .setDescription("This is the latest monthly dye drop. The current lanauge is Indonesian but will be" +
                        "translated to English soon!")
                .setImage(image);

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendErrorMessage(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Error while getting monthly dye!")
                .setDescription("An error happened! Is the site maybe down?")
                .setThumbnail(Values.toramLogo);

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

}
