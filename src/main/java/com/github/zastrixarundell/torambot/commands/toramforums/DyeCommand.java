package com.github.zastrixarundell.torambot.commands.toramforums;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class DyeCommand implements MessageCreateListener
{

    public static DyeCommand instance = null;

    public DyeCommand() { instance = this; }

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent)
    {
        if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(Values.getPrefix() + "dye"))
            return;

        if (!messageCreateEvent.getMessageAuthor().isRegularUser())
            return;

        Runnable runnable = () ->
        {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Latest monthly dyes")
                    .setDescription("Here is the image of the latest monthly dyes!\n\n\n" +
                            "Note: This can be late so check the title of the image.");

            Parser.parseFooter(embed, messageCreateEvent);
            Parser.parseColor(embed, messageCreateEvent);
            embed.setImage(Values.getDyeImage());

            messageCreateEvent.getChannel().sendMessage(embed);
        };

        (new Thread(runnable)).start();

    }

}
