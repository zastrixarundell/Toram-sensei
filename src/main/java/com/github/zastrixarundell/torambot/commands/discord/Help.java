package com.github.zastrixarundell.torambot.commands.discord;

import com.github.zastrixarundell.torambot.ToramBot;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;

public class Help implements MessageCreateListener
{

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent)
    {

        if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(ToramBot.getPrefix() + "help"))
            return;

        if (!messageCreateEvent.getMessageAuthor().isRegularUser())
            return;

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("List of commands:")
                .setDescription("Hi, I am a bot created by [Zastrix](https://toramonline.com/index.php?members/zastrix.100975/) " +
                        "solely for the purpose of assisting " +
                        "players in Toram. Here is the list of my commands:")
                .addField(ToramBot.getPrefix() + "monster [monster]", "This command is used for getting info about a monster!")
                .addField(ToramBot.getPrefix() + "item [Item Name]", "You can use this command to search " +
                        "for an item on coryn.club!")
                .addField(ToramBot.getPrefix() + "level [your level] (level range) (EXP boost)",
                        "Only [your level] needs to be present here, if the arguments " +
                                "in normal brackets aren't specified the commands uses 5 for the level " +
                                "range value and 0 for the EXP boost value.")
                .addField(ToramBot.getPrefix() + "points [current skill points] [your level] [target level]",
                        "All of the arguments need to be present or else it will not work!")
                .addField(ToramBot.getPrefix() + "events", "This command is used to show the latest event posted on the official " +
                        "Toram news site!")
                .addField(ToramBot.getPrefix() + "latest", "This command is used to show the overall latest news posted on the " +
                        "official Toram news site!")
                .addField(ToramBot.getPrefix() + "maintenance", "This command is used to show the latest maintenance data.")
                .addField(ToramBot.getPrefix() + "news", "This command is used to show the latest big news on the site. Big events, " +
                        "new chapter in the story line, etc.")
                .setThumbnail(messageCreateEvent.getApi().getYourself().getAvatar());

        if(ToramBot.isRanOnHostingService())
            embed.setFooter("Support me by going on the link: " + ToramBot.supportURL);


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

        messageCreateEvent.getChannel().sendMessage(embed);

    }
}
