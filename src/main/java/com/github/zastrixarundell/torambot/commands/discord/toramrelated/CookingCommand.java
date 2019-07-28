package com.github.zastrixarundell.torambot.commands.discord.toramrelated;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class CookingCommand implements MessageCreateListener
{

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent)
    {

        if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(Values.getPrefix() + "food"))
            if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(Values.getPrefix() + "cooking"))
                if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(Values.getPrefix() + "cook"))
                    return;

        if (!messageCreateEvent.getMessageAuthor().isRegularUser())
            return;

        if(Parser.argumentsParser(messageCreateEvent).size() > 0)
        {
            int exp;

            try
            {
                exp = Integer.parseInt(Parser.argumentsParser(messageCreateEvent).get(0));
            }
            catch (Exception e)
            {
                sendError(messageCreateEvent);
                return;
            }

            int level = getWhichLevel(exp);

            if(level == -1)
                sendError(messageCreateEvent);

            sendMessage(exp, level, messageCreateEvent);
        }
        else
            sendDefault(messageCreateEvent);

    }

    private void sendMessage(int exp, int level, MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Food EXP")
                .setDescription("Showing the data for the specified EXP.");

        //set with exp
        embed.addField("Your EXP:", String.valueOf(exp));
        embed.addField("Your level:", String.valueOf(level));

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private int getWhichLevel(int exp)
    {
        if(exp == 0)
            return 0;

        if(exp >= 1 && exp < 3)
            return 1;

        if(exp >= 3 && exp < 9)
            return 2;

        if(exp >= 9 && exp < 21)
            return 3;

        if(exp >= 21 && exp < 45)
            return 4;

        if(exp >= 45 && exp < 93)
            return 5;

        if(exp >= 93 && exp < 189)
            return 6;

        if(exp >= 189 && exp < 381)
            return 7;

        if(exp >= 381 && exp < 765)
            return 8;

        if(exp >= 765 && exp < 1533)
            return 9;

        if(exp >= 1533)
            return 10;

        return -1;
    }

    private void sendDefault(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Food EXP list")
                .setDescription("Showing levels for cooking EXP.")
                .addField("Level: 1", "Exp needed: 1")
                .addField("Level: 2", "Exp needed: 3")
                .addField("Level: 3", "Exp needed: 9")
                .addField("Level: 4", "Exp needed: 21")
                .addField("Level: 5", "Exp needed: 45")
                .addField("Level: 6", "Exp needed: 93")
                .addField("Level: 7", "Exp needed: 189")
                .addField("Level: 8", "Exp needed: 381")
                .addField("Level: 9", "Exp needed: 765")
                .addField("Level: 10", "Exp needed: 1533");

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendError(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Alchemy proficiency guide")
                .setDescription("There was an error with the level, have you specified it?\n\nShowing the default levels.")
                .addField("Level: 1", "Exp needed: 1")
                .addField("Level: 2", "Exp needed: 3")
                .addField("Level: 3", "Exp needed: 9")
                .addField("Level: 4", "Exp needed: 21")
                .addField("Level: 5", "Exp needed: 45")
                .addField("Level: 6", "Exp needed: 93")
                .addField("Level: 7", "Exp needed: 189")
                .addField("Level: 8", "Exp needed: 381")
                .addField("Level: 9", "Exp needed: 765")
                .addField("Level: 10", "Exp needed: 1533");

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }
}
