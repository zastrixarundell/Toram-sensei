package com.github.zastrixarundell.torambot;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

import static com.github.zastrixarundell.torambot.Values.footerMessage;

public class Parser
{

    public static void parseFooter(EmbedBuilder builder, MessageCreateEvent messageCreateEvent)
    {
        //messageCreateEvent might be used in the future.
        if(Values.isRanOnHostingService())
            builder.setFooter(footerMessage);
    }

    public static void parseColor(EmbedBuilder embed, MessageCreateEvent messageCreateEvent)
    {
        if (messageCreateEvent.getServer().isPresent())
            if (messageCreateEvent.getServer().get().getHighestRole(messageCreateEvent.getApi().getYourself()).isPresent())
            {
                Role role = messageCreateEvent.getServer().get().getHighestRole(messageCreateEvent.getApi().getYourself()).get();
                if (role.getColor().isPresent())
                {
                    Color color = role.getColor().get();
                    embed.setColor(color);
                }
            }
    }

}
