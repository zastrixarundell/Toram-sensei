package com.github.zastrixarundell.torambot.commands.discord;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.ArrayList;

public class Proficiency implements MessageCreateListener
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
                level = Integer.valueOf(Parser.argumentsParser(messageCreateEvent).get(0));
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

        Parser.parsePrimaryThumbnail(embed, messageCreateEvent);
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
                title = "Revita I"; description = "0 - 10";
            }

            if(type == 1)
            {
                title = "Revita II"; description = "10 - 30";
            }

            if(type == 2)
            {
                title = "Revita III"; description = "30 - 55/60";
            }

            if(type == 3)
            {
                title = "Regera III"; description = "30 - 65/70";
            }

            if(type == 4)
            {
                title = "Revita IV"; description = "55 - 70";
            }

            if(type == 5)
            {
                title = "Vaccine III"; description = "65 - 100";
            }
            if(type == 6)
            {
                title = "Revita V"; description = "70 - 100/105";
            }

            if(type == 7)
            {
                title = "Loincloth"; description = "105 - 120";
            }

            if(type == 8)
            {
                title = "Revita VI"; description = "120 - 132";
            }

            if(type == 9)
            {
                title = "Orichalcum"; description = "132 - 150";
            }

            if(type == 10)
            {
                title = "High Purity Orichalcum"; description = "150 - 200";
            }

            embed.addField(title, description);
        });

        Parser.parsePrimaryThumbnail(embed, messageCreateEvent);
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
                .addField("0 - 10", "Revita I")
                .addField("10 - 30", "Revita II")
                .addField("30 - 55/60", "Revita III")
                .addField("30 - 65/70", "Regera III")
                .addField("55 - 70", "Revita IV")
                .addField("65 - 100", "Vaccine III")
                .addField("70 - 100/105", "Revita V")
                .addField("105 - 120", "Loincloth")
                .addField("120 - 132", "Revita VI")
                .addField("132 - 150", "Orichalcum")
                .addField("150 - 200", "Hugh purity Orichalcum");

        Parser.parsePrimaryThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendError(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Alchemy proficiency guide")
                .setDescription("There was an error with the level, have you specified it?\n\nShowing the default guide.")
                .addField("0 - 10", "Revita I")
                .addField("10 - 30", "Revita II")
                .addField("30 - 55/60", "Revita III")
                .addField("30 - 65/70", "Regera III")
                .addField("55 - 70", "Revita IV")
                .addField("65 - 100", "Vaccine III")
                .addField("70 - 100/105", "Revita V")
                .addField("105 - 120", "Loincloth")
                .addField("120 - 132", "Revita VI")
                .addField("132 - 150", "Orichalcum")
                .addField("150 - 200", "Hugh purity Orichalcum");

        Parser.parsePrimaryThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }
}
