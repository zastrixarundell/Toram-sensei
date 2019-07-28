package com.github.zastrixarundell.torambot.commands.discord.toramrelated;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.ArrayList;

public class ProficiencyCommand implements MessageCreateListener
{

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent)
    {

        if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(Values.getPrefix() + "proficiency"))
            if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(Values.getPrefix() + "prof"))
                return;

        if (!messageCreateEvent.getMessageAuthor().isRegularUser())
            return;

        if(Parser.argumentsParser(messageCreateEvent).size() > 0)
        {
            int level;

            try
            {
                level = Integer.parseInt(Parser.argumentsParser(messageCreateEvent).get(0));
            }
            catch (Exception e)
            {
                sendError(messageCreateEvent);
                return;
            }

            sendMessage(level, getWhichType(level), messageCreateEvent);
        }
        else
            sendDefault(messageCreateEvent);

        sendBetaInfo(messageCreateEvent);

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

    private void sendMessage(int level, ArrayList<Integer> types, MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Alchemy proficiency guide")
                .setDescription("Showing what to create on level " + level + ".");

        types.forEach(type ->
        {
            String title = "", description = "";

            if(type == 0)
            {
                title = "Item: Revita I"; description = "Proficiency: 0 - 10";
            }

            if(type == 1)
            {
                title = "Item: Revita II"; description = "Proficiency: 10 - 30";
            }

            if(type == 2)
            {
                title = "Item: Revita III"; description = "Proficiency: 30 - 55/60";
            }

            if(type == 3)
            {
                title = "Item: Regera III"; description = "Proficiency: 30 - 65/70";
            }

            if(type == 4)
            {
                title = "Item: Revita IV"; description = "Proficiency: 55 - 70";
            }

            if(type == 5)
            {
                title = "Item: Vaccine III"; description = "Proficiency: 65 - 100";
            }
            if(type == 6)
            {
                title = "Item: Revita V"; description = "Proficiency: 70 - 100/105";
            }

            if(type == 7)
            {
                title = "Item: Loincloth"; description = "Proficiency: 105 - 120";
            }

            if(type == 8)
            {
                title = "Item: Revita VI"; description = "Proficiency: 120 - 132";
            }

            if(type == 9)
            {
                title = "Item: Orichalcum"; description = "Proficiency: 132 - 150";
            }

            if(type == 10)
            {
                title = "Item: High Purity Orichalcum"; description = "Proficiency: 150 - 200";
            }

            embed.addField(title, description);
        });

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private ArrayList<Integer> getWhichType(int level)
    {
        ArrayList<Integer> message = new ArrayList<>();

        if(level >= 0 && level < 10)
            message.add(0);

        if(level >= 10 && level < 30)
            message.add(1);

        if(level >= 30 && level < 60)
            message.add(2);

        if(level >= 30 && level < 70)
            message.add(3);

        if(level >= 55 && level < 70)
            message.add(4);

        if(level >= 65 && level < 100)
            message.add(5);

        if(level >= 70 && level < 105)
            message.add(6);

        if(level >= 105 && level < 120)
            message.add(7);

        if(level >= 120 && level < 132)
            message.add(8);

        if(level >= 132 && level < 150)
            message.add(9);

        if(level >= 150 && level < 200)
            message.add(10);

        return message;
    }

    private void sendDefault(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Alchemy proficiency guide")
                .setDescription("Showing the default guide.")
                .addField("Proficiency: 0 - 10", "Item: Revita I")
                .addField("Proficiency: 10 - 30", "Item: Revita II")
                .addField("Proficiency: 30 - 55/60", "Item: Revita III")
                .addField("Proficiency: 30 - 65/70", "Item: Regera III")
                .addField("Proficiency: 55 - 70", "Item: Revita IV")
                .addField("Proficiency: 65 - 100", "Item: Vaccine III")
                .addField("Proficiency: 70 - 100/105", "Item: Revita V")
                .addField("Proficiency: 105 - 120", "Item: Loincloth")
                .addField("Proficiency: 120 - 132", "Item: Revita VI")
                .addField("Proficiency: 132 - 150", "Item: Orichalcum")
                .addField("Proficiency: 150 - 200", "Item: Hugh purity Orichalcum");

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendError(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Alchemy proficiency guide")
                .setDescription("There was an error with the level, have you specified it?\n\nShowing the default guide.")
                .addField("Proficiency: 0 - 10", "Item: Revita I")
                .addField("Proficiency: 10 - 30", "Item: Revita II")
                .addField("Proficiency: 30 - 55/60", "Item: Revita III")
                .addField("Proficiency: 30 - 65/70", "Item: Regera III")
                .addField("Proficiency: 55 - 70", "Item: Revita IV")
                .addField("Proficiency: 65 - 100", "Item: Vaccine III")
                .addField("Proficiency: 70 - 100/105", "Item: Revita V")
                .addField("Proficiency: 105 - 120", "Item: Loincloth")
                .addField("Proficiency: 120 - 132", "Item: Revita VI")
                .addField("Proficiency: 132 - 150", "Item: Orichalcum")
                .addField("Proficiency: 150 - 200", "Item: Hugh purity Orichalcum");

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }
}
