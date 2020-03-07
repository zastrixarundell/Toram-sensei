package com.github.zastrixarundell.torambot.commands.crafting;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.commands.DiscordCommand;
import com.github.zastrixarundell.torambot.objects.toram.ProficiencyItem;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.*;

public class ProficiencyCommand extends DiscordCommand
{

    private ArrayList<ProficiencyItem> items;
    private ProficiencyItem.ProficiencyType type;

    public ProficiencyCommand(ProficiencyItem.ProficiencyType type)
    {
        super(type.getCallers());

        this.type = type;
        items = ProficiencyItem.getForType(type);
    }

    @Override
    protected void runCommand(MessageCreateEvent event)
    {
        if (Parser.argumentsParser(event).isEmpty())
            sendDefault(event);
        else
        {
            int level;

            try
            {
                level = Integer.parseInt(Parser.argumentsParser(event).get(0));
            }
            catch (Exception e)
            {
                sendError(event);
                return;
            }

            sendMessage(level, event);
        }

        sendBetaInfo(event);
    }

    private void sendBetaInfo(MessageCreateEvent messageCreateEvent)
    {
        //https://toramonline.com/index.php?threads/synth-prof-guide.48219/#post-497698
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Info about " + type.getName().toLowerCase())
                .setDescription("I'm not sure if this guide is correct. If you have any suggestions, do post them " +
                        "on [this direct link](https://toramonline.com/index.php?threads/synth-prof-guide.48219/#post-497698).");

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendMessage(int level, MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(type.getName() + " proficiency guide")
                .setDescription("Showing what to create on level " + level + ".");

        for (ProficiencyItem item : items)
            if(level >= item.getStartProficiency() && level < item.getStopProficiency())
                embed.addField("Proficiency: " + item.getStartProficiency() +
                        " - " + item.getStopProficiency(), "Item: " + item.getName());

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void addAllItems(EmbedBuilder embed)
    {
        items.forEach(item -> embed.addField("Proficiency: " + item.getStartProficiency() +
                " - " + item.getStopProficiency(), "Item: " + item.getName()));
    }

    private void sendDefault(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(type.getName() + " proficiency guide")
                .setDescription("Showing the default guide.");

        addAllItems(embed);

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendError(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(type.getName() + " proficiency guide")
                .setDescription("There was an error with the level, have you specified it?\n\nShowing the default guide.");

        addAllItems(embed);

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }
}
