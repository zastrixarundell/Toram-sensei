package com.github.zastrixarundell.torambot.commands.crafting;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import com.github.zastrixarundell.torambot.commands.DiscordCommand;
import com.github.zastrixarundell.torambot.objects.toram.ProficiencyItem;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.*;

public class ProficiencyCommand extends DiscordCommand
{

    private static ArrayList<ProficiencyItem> synthItems = new ArrayList<>();

    public ProficiencyCommand()
    {
        super("proficiency", "prof");

        ProficiencyItem.ProficiencyType type = ProficiencyItem.ProficiencyType.SYNTHESIST;

        synthItems.add(new ProficiencyItem(0, 10, "Revita I", type));
        synthItems.add(new ProficiencyItem(10, 30, "Revita II", type));
        synthItems.add(new ProficiencyItem(30, 60, "Revita III", type));
        synthItems.add(new ProficiencyItem(30, 70, "Regera III", type));
        synthItems.add(new ProficiencyItem(55, 70, "Revita IV", type));
        synthItems.add(new ProficiencyItem(65, 100, "Vaccine III", type));
        synthItems.add(new ProficiencyItem(70, 105, "Revita V", type));
        synthItems.add(new ProficiencyItem(100, 154, "Flower Nectar x10", type));
        synthItems.add(new ProficiencyItem(105, 120, "Loincloth", type));
        synthItems.add(new ProficiencyItem(120, 132, "Revita VI", type));
        synthItems.add(new ProficiencyItem(132, 150, "Orichalcum", type));
        synthItems.add(new ProficiencyItem(154, 200, "High Purity Orichalcum", type));

        Collections.sort(synthItems);
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
                .setTitle("Info about alchemy")
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
                .setTitle("Alchemy proficiency guide")
                .setDescription("Showing what to create on level " + level + ".");

        for (ProficiencyItem item : synthItems)
            if(level >= item.getStartProficiency() && level < item.getStopProficiency())
                embed.addField("Proficiency: " + item.getStartProficiency() +
                        " - " + item.getStopProficiency(), "Item: " + item.getName());

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void addAllSynthItem(EmbedBuilder embed)
    {
        synthItems.forEach(item -> embed.addField("Proficiency: " + item.getStartProficiency() +
                " - " + item.getStopProficiency(), "Item: " + item.getName()));
    }

    private void sendDefault(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Alchemy proficiency guide")
                .setDescription("Showing the default guide.");

        addAllSynthItem(embed);

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendError(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Alchemy proficiency guide")
                .setDescription("There was an error with the level, have you specified it?\n\nShowing the default guide.");

        addAllSynthItem(embed);

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }
}
