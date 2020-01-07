/*
 *             DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *                     Version 2, December 2004
 *
 * Copyright (C) 2020, Zastrix Arundell, https://github.com/ZastrixArundell
 *
 *  Everyone is permitted to copy and distribute verbatim or modified
 *  copies of this license document, and changing it is allowed as long
 *  as the name is changed.
 *
 *             DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *    TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *   0. You just DO WHAT THE FUCK YOU WANT TO.
 *
 *
 */

package com.github.zastrixarundell.torambot.commands.crafting;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import com.github.zastrixarundell.torambot.commands.DiscordCommand;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class CookingCommand extends DiscordCommand
{

    public CookingCommand()
    {
        super("food", "cooking", "cook");
    }

    @Override
    protected void runCommand(MessageCreateEvent event)
    {
        if(Parser.argumentsParser(event).size() > 0)
        {
            int exp;

            try
            {
                exp = Integer.parseInt(Parser.argumentsParser(event).get(0));
            }
            catch (Exception e)
            {
                sendError(event);
                return;
            }

            if(exp < 0)
            {
                sendError(event);
                return;
            }

            int level = getWhichLevel(exp);

            sendMessage(exp, level, event);
        }
        else
            sendDefault(event);

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
        for (int i = FoodLevels.values().length - 1; i >= 0; i--)
            if(exp >= FoodLevels.values()[i].exp)
                return FoodLevels.values()[i].level;

        return 0;
    }

    private void sendDefault(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Food EXP list")
                .setDescription("Showing levels for cooking EXP.");

        addAllLevels(embed);

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendError(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Food EXP list")
                .setDescription("There was an error with the exp, have you specified it?\n\n" +
                        "Negative or too big numbers don't work.\n\n" +
                        "Showing the default levels.");

        addAllLevels(embed);

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    public void addAllLevels(EmbedBuilder embed)
    {
        for (FoodLevels value : FoodLevels.values())
            embed.addField("Level: " + value.level, "Exp needed: " + value.exp);
    }

    private enum FoodLevels
    {
        ONE(1, 1),
        TWO(2, 3),
        THREE(3, 9),
        FOUR(4, 21),
        FIVE(5, 45),
        SIX(6, 93),
        SEVEN(7, 189),
        EIGHT(8, 381),
        NINE(9, 765),
        TEN(10, 1533);

        int level, exp;

        FoodLevels(int level, int exp)
        {
            this.level = level;
            this.exp = exp;
        }

        public int getExp()
        {
            return exp;
        }

        public int getLevel()
        {
            return level;
        }
    }
}
