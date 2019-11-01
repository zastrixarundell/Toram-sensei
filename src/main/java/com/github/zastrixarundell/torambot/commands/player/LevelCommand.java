package com.github.zastrixarundell.torambot.commands.player;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import com.github.zastrixarundell.torambot.commands.DiscordCommand;
import com.github.zastrixarundell.torambot.objects.toram.NPC;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class LevelCommand extends DiscordCommand
{

    public LevelCommand()
    {
        super("level", "leveling");
    }

    @Override
    protected void runCommand(MessageCreateEvent event)
    {
        ArrayList<String> arguments = Parser.argumentsParser(event);

        if (arguments.isEmpty())
        {
            sendCommandUsage(event);
            return;
        }

        int level, bonus;

        try
        {
            level = Integer.parseInt(arguments.get(0));
            bonus = arguments.size() > 1 ? Integer.parseInt(arguments.get(1)) : 0;
        }
        catch (NumberFormatException e)
        {
            sendError(event, "Can't determine level!", "Are you sure you formatted this correct? There was" +
                    " an error while turning the values into numbers!");
            return;
        }

        Runnable runnable = () ->
        {
            try
            {
                Document document = Jsoup.connect("http://coryn.club/leveling.php")
                        .data("lv", String.valueOf(level))
                        .data("gap", "9")
                        .data("bonusEXP", String.valueOf(bonus))
                        .get();

                Elements tables = document.getElementsByClass("table table-striped");

                NPC boss, miniboss, monster;
                boss = miniboss = monster = null;

                //Start Boss
                try
                {
                    Element bossTable = tables.first();
                    Element body = bossTable.getElementsByTag("tbody").first();
                    boss = new NPC(body.getElementsByTag("tr").first());
                }
                catch (Exception ignore)
                {

                }

                //Start MiniBoss
                try
                {
                    Element minibossTable = tables.get(1);
                    Element body = minibossTable.getElementsByTag("tbody").first();
                    miniboss = new NPC(body.getElementsByTag("tr").first());
                }
                catch (Exception ignore)
                {

                }

                //Start Monster
                try
                {
                    Element monsterTable = tables.last();
                    Element body = monsterTable.getElementsByTag("tbody").first();
                    monster = new NPC(body.getElementsByTag("tr").first());
                }
                catch (Exception ignore)
                {

                }

                if (boss != null)
                    showNPC(event, boss, "Boss");
                else
                    sendError(event, "There is no boss!",
                            "There is no Boss to farm for the specified level!");

                if (miniboss != null)
                    showNPC(event, miniboss, "Mini Boss");
                else
                    sendError(event, "There is no Mini Boss!",
                            "There is no Mini Boss to farm for the specified level!");

                if (monster != null)
                    showNPC(event, monster, "Normal Monster");
                else
                    sendError(event, "There is no Monster!",
                            "There is no Monster to farm for the specified level!");
            }
            catch (Exception e)
            {
                sendError(event, "Error while getting EXP!",
                        "An error happened when getting the level, did you put in the correct inputs?");
            }
        };

        executeRunnable(event, runnable);

    }

    private void showNPC(MessageCreateEvent messageCreateEvent, NPC npc, String type)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(type + ": " + npc.getName())
                .addField("Level:", npc.getLevel())
                .addField("Location:", npc.getLocation())
                .setUrl(npc.getLink());

                npc.getExp().forEach(exp -> embed.addField(exp[0], exp[1]));

        Parser.parseMonsterThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendCommandUsage(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Level Command: ")
                .setDescription("You can use this command to get " +
                        "what you need to farm to gain EXP the fastest!")
                .addField(Values.getPrefix() + "level [your level] (EXP boost)",
                        "Only [your level] needs to be present here. If the exp boost is not defined you will " +
                                "get the standard exp value.");

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendError(MessageCreateEvent messageCreateEvent, String name, String description)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(name)
                .setDescription(description);

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

}
