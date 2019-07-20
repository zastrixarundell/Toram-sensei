package com.github.zastrixarundell.torambot.commands.discord;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.Properties;

public class Help implements MessageCreateListener
{

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent)
    {

        if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(Values.getPrefix() + "help"))
            return;

        if (!messageCreateEvent.getMessageAuthor().isRegularUser())
            return;

        String title = "General help";

        try
        {
            final Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("values.properties"));
            title += " | v" + properties.getProperty("version");
        }
        catch (Exception ignore)
        {

        }

        try
        {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle(title)
                    .setDescription("Hi, I am a bot created by [Zastrix](https://toramonline.com/index.php?members/zastrix.100975/) " +
                            "solely for the purpose of assisting " +
                            "players in Toram. Here is the list of my commands:")

                    .addField(Values.getPrefix() + "monster [monster]", "This command is used for getting info about a monster!")

                    .addField(Values.getPrefix() + "item [name]", "You can use this command to search " +
                            "for an item on coryn.club!")

                    .addField(Values.getPrefix() + "level [your level] (level range) (EXP boost)",
                            "Get what you need to farm to level up fast. " +
                                    "Only [your level] needs to be present here, if the arguments " +
                                    "in normal brackets aren't specified the commands uses 5 for the level " +
                                    "range value and 0 for the EXP boost value.")

                    .addField(Values.getPrefix() + "points [current skill points] [your level] [target level]",
                            "Calculate skill points. All of the arguments need to " +
                                    "be present or else it will not work!")

                    .addField(Values.getPrefix() + "proficiency|prof (proficiency level)", "Depending on your proficiency level " +
                            "this will show you what to synthesize in order to gain most proficiency.")

                    .addField(Values.getPrefix() + "events", "This command is used to show the latest " +
                            "event posted on the official Toram news site!")

                    .addField(Values.getPrefix() + "latest", "This command is used to show the overall latest news posted on the " +
                            "official Toram news site!")

                    .addField(Values.getPrefix() + "maintenance|maint", "This command is used to show the latest maintenance data.")

                    .addField(Values.getPrefix() + "news", "This command is used to show the latest big news on the site. Big events, " +
                            "new chapter in the story line, etc.");

            Parser.parsePrimaryThumbnail(embed, messageCreateEvent);
            Parser.parseFooter(embed, messageCreateEvent);
            Parser.parseColor(embed, messageCreateEvent);

            messageCreateEvent.getChannel().sendMessage(embed);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
