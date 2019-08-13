package com.github.zastrixarundell.torambot.commands;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.ArrayList;

public class HelpCommand implements MessageCreateListener
{

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent)
    {

        if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(Values.getPrefix() + "help"))
            return;

        if (!messageCreateEvent.getMessageAuthor().isRegularUser())
            return;

        ArrayList<String> arguments = Parser.argumentsParser(messageCreateEvent);

        if(arguments.size() == 0)
        {
            sendGeneralHelp(messageCreateEvent);
            return;
        }

        String category = arguments.get(0).toLowerCase();

        switch(category)
        {
            case "items":
                sendItemSearchCommands(messageCreateEvent);
                return;
            case "monsters":
                sendMonsterCommands(messageCreateEvent);
                return;
            case "player":
                sendPlayerCommands(messageCreateEvent);
                return;
            case "game":
                sendGameInfoCommands(messageCreateEvent);
                return;
            case "bot":
                sendBotInfoCommands(messageCreateEvent);
                return;
            case "crafting":
                sendCraftingCommands(messageCreateEvent);
                return;
        }

        sendCategoryError(messageCreateEvent, category);

    }

    private void sendGeneralHelp(MessageCreateEvent messageCreateEvent)
    {
        String title = "General help";

        if(!Values.getVersion().isEmpty())
            title += " | v" + Values.getVersion();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(title)
                .setDescription("Hi, I am a bot created by [Zastrix](https://toramonline.com/index.php?members/zastrix.100975/) " +
                        "solely for the purpose of assisting players in Toram. I currently have " +
                        messageCreateEvent.getApi().getMessageCreateListeners().size() + " commands. " +
                        " You can search for the commands by their categories:")

                .addField("Item Search Commands", Values.getPrefix() + "help items")
                .addField("Monster Search Commands:", Values.getPrefix() + "help monsters")
                .addField("Player Info Commands:", Values.getPrefix() + "help player")
                .addField("Crafting Commands:", Values.getPrefix() + "help crafting")
                .addField("Game Info Commands:", Values.getPrefix() + "help game")
                .addField("Bot Info Commands:", Values.getPrefix() + "help bot");

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendCategoryError(MessageCreateEvent messageCreateEvent, String category)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Error while getting category type: " + category)
                .setDescription("Does the category even exist?");

        Parser.parseThumbnail(embed, messageCreateEvent);

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendItemSearchCommands(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Item Search Commands")
                .setDescription("Commands which allow you to search for Toram items. Note that if the item you are searching for " +
                        "is not categorized on the list you can still search with the general command!")

                .addField(Values.getPrefix() + "item [name]", "You can use this general command to search for items!")

                .addField("Weapons:", "The following commands are used for searching weapons.")

                .addInlineField(Values.getPrefix() + "onehanded|1h [name]", "Search for one handed swords!")
                .addInlineField(Values.getPrefix() + "twohanded|2h [name]", "Search for two handed swords!")
                .addInlineField(Values.getPrefix() + "bow [name]", "Search for bows!")
                .addInlineField(Values.getPrefix() + "bowgun|gun [name]", "Search for bowguns!")
                .addInlineField(Values.getPrefix() + "arrow [name]", "Search for arrows!")
                .addInlineField(Values.getPrefix() + "dagger [name]", "Search for daggers!")
                .addInlineField(Values.getPrefix() + "halberd|hb [name]", "Search for halberds!")
                .addInlineField(Values.getPrefix() + "katana|kat [name]", "Search for katanas!")
                .addInlineField(Values.getPrefix() + "knuckles [name]", "Search for knuckles!")
                .addInlineField(Values.getPrefix() + "magicdevice|md [name]", "Search for magic devices!")
                .addInlineField(Values.getPrefix() + "staff [name]", "Search for staffs!")

                .addField("Gear:", "The following commands are used for searching through gear!")

                .addInlineField(Values.getPrefix() + "additional|add [name]", "Search for additional gear!")
                .addInlineField(Values.getPrefix() + "armor|arm [name]", "Search for armor!")
                .addInlineField(Values.getPrefix() + "shield [name]", "Search for shields!")
                .addInlineField(Values.getPrefix() + "special [name]", "Search for special gear!")

                .addField("Extra:", "Some extra search options.")

                .addInlineField(Values.getPrefix() + "gem [name]", "Search for gems!")
                .addInlineField(Values.getPrefix() + "xtal|crysta|crystal [name]", "Search for xtals!")
                .addInlineField(Values.getPrefix() + "upgrade|enhance [name]", "Search for the upgraded version of an xtal!");

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendMonsterCommands(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Monster Search Commands")
                .setDescription("Commands which allow you to search for Toram items.")

                .addField(Values.getPrefix() + "monster [name]", "Get info about any monster type!")

                .addField(Values.getPrefix() + "normalmonster|nmonster [name]", "Get info about a normal monster!")

                .addField(Values.getPrefix() + "miniboss|mboss|mini|mb [name]", "Get info about a miniboss!")

                .addField(Values.getPrefix() + "boss [name]", "Get info about a boss!");

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendPlayerCommands(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Player Info Commands")
                .setDescription("Commands which allow players to get info about player-related info.")

                .addField(Values.getPrefix() + "level [your level] (level range) (EXP boost)",
                        "Get what you need to farm to level up fast. " +
                                "Only [your level] needs to be present here, if the arguments " +
                                "in normal brackets aren't specified the commands uses 9 for the level " +
                                "range value and 0 for the EXP boost value.")

                .addField(Values.getPrefix() + "points [current skill points] [your level] [target level]",
                        "Calculate skill points. All of the arguments need to " +
                                "be present or else it will not work!")

                .addField(Values.getPrefix() + "food|cooking|cook (food EXP)", "Depending on your food EXP " +
                        "this will show you what level you are. Leave blank for overall list.");

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendGameInfoCommands(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Game Info Commands")
                .setDescription("Commands which give you the latest info regarding Toram.")
                .addField(Values.getPrefix() + "events", "This command is used to show the latest " +
                        "event posted on the official Toram news site!")

                .addField(Values.getPrefix() + "latest", "This command is used to show the overall latest news posted on the " +
                        "official Toram news site!")

                .addField(Values.getPrefix() + "maintenance|maint", "This command is used to show the latest maintenance data.")

                .addField(Values.getPrefix() + "news", "This command is used to show the latest big news on the site. Big events, " +
                        "new chapter in the story line, etc.")

                .addField(Values.getPrefix() + "dye|dyes (value)", "This command is used to show all of the dyes or see details about a specific color!");

        if (Values.getDyeImages() != null)
            embed.addField(Values.getPrefix() + "monthly|month", "Get the latest monthly dyes!");

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendBotInfoCommands(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Bot Info Commands")
                .setDescription("Commands which give you basic info about " + messageCreateEvent.getApi().getYourself().getName() + ".")

                .addField(Values.getPrefix() + "invite", "You can use this command to get the invite link for this bot!")
                .addField(Values.getPrefix() + "donate", "You can use this command to donate to the developer (it would help)!")
                .addField(Values.getPrefix() + "support", "You can use this command to get the support sever for this bot!");

                if(Values.getApi() != null)
                    embed.addField(Values.getPrefix() + "vote", "You can use this command to vote for this bot!");

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendCraftingCommands(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Crafting Commands")
                .setDescription("Commands which give you basic info about crafting/creating items.")

                .addField(Values.getPrefix() + "proficiency|prof (proficiency level)", "Depending on your proficiency level " +
                        "this will show you what to synthesize in order to gain most proficiency. Leave black for overall list.")

                .addField(Values.getPrefix() + "recipe|mats [item]", "Find the recipe of the searched item.");


        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }
}
