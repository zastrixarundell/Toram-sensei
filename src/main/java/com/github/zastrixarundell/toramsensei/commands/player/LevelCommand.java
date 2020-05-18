package com.github.zastrixarundell.toramsensei.commands.player;

import com.github.zastrixarundell.toramsensei.Parser;
import com.github.zastrixarundell.toramsensei.Values;
import com.github.zastrixarundell.toramsensei.commands.DiscordCommand;
import com.github.zastrixarundell.toramsensei.objects.toram.NPC;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
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


                //Start Boss
                NPC bossOne = null, bossTwo = null, bossThree = null;

                try
                {
                    Element bossTable = tables.first();
                    Element body = bossTable.getElementsByTag("tbody").first();

                    Element bossOneE = body.getElementsByTag("tr").get(0);
                    Element bossTwoE = body.getElementsByTag("tr").get(1);
                    Element bossThreeE = body.getElementsByTag("tr").get(2);

                    if(bossOneE != null)
                        bossOne = new NPC(bossOneE);

                    if(bossOneE != null)
                        bossTwo = new NPC(bossTwoE);

                    if(bossOneE != null)
                        bossThree = new NPC(bossThreeE);
                }
                catch (Exception ignore)
                {

                }

                //Start MiniBoss
                NPC miniBossOne = null, miniBossTwo = null, miniBossThree = null;
                try
                {
                    Element minibossTable = tables.get(1);
                    Element body = minibossTable.getElementsByTag("tbody").first();

                    Element miniBossOneE = body.getElementsByTag("tr").get(0);
                    Element miniBossTwoE = body.getElementsByTag("tr").get(1);
                    Element miniBossThreeE = body.getElementsByTag("tr").get(2);

                    if(miniBossOneE != null)
                        miniBossOne = new NPC(miniBossOneE);

                    if(miniBossTwoE != null)
                        miniBossTwo = new NPC(miniBossTwoE);

                    if(miniBossThreeE != null)
                        miniBossThree = new NPC(miniBossThreeE);
                }
                catch (Exception ignore)
                {

                }

                //Start Monster
                NPC monsterOne = null, monsterTwo = null, monsterThree = null;
                try
                {
                    Element monsterTable = tables.last();
                    Element body = monsterTable.getElementsByTag("tbody").first();

                    Element monsterOneE = body.getElementsByTag("tr").get(0);
                    Element monsterTwoE = body.getElementsByTag("tr").get(1);
                    Element monsterThreeE = body.getElementsByTag("tr").get(2);

                    if(monsterOneE != null)
                        monsterOne = new NPC(monsterOneE);

                    if(monsterTwoE != null)
                        monsterTwo = new NPC(monsterTwoE);

                    if(monsterThreeE != null)
                        monsterThree = new NPC(monsterThreeE);
                }
                catch (Exception ignore)
                {

                }

                if (bossOne != null)
                    showNPC(event, bossOne, bossTwo, bossThree, "Bosses:");
                else
                    sendError(event, "There is no boss!",
                            "There is no Boss to farm for the specified level!");

                if (miniBossOne != null)
                    showNPC(event, miniBossOne, miniBossTwo, miniBossThree, "Mini Bosses:");
                else
                    sendError(event, "There is no Mini Boss!",
                            "There is no Mini Boss to farm for the specified level!");

                if (monsterOne != null)
                    showNPC(event, monsterOne, monsterTwo, monsterThree, "Normal Monsters:");
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

    private void showNPC(MessageCreateEvent messageCreateEvent, NPC npcOne, NPC npcTwo, NPC npcThree, String type)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(type)
                .addField(
                        npcOne.getName() + " - " + npcOne.getLevel() + " - " + npcOne.getLocation(),
                        npcOne.getExp().get(0)[0] + " - " + npcOne.getExp().get(0)[1]);

        if(npcTwo != null)
            embed.addField(
                    npcTwo.getName() + " - " + npcTwo.getLevel() + " - " + npcTwo.getLocation(),
                    npcTwo.getExp().get(0)[0] + " - " + npcTwo.getExp().get(0)[1]);

        if(npcThree != null)
            embed.addField(
                    npcThree.getName() + " - " + npcThree.getLevel() + " - " + npcThree.getLocation(),
                    npcThree.getExp().get(0)[0] + " - " + npcThree.getExp().get(0)[1]);

        Parser.parseMonsterThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        embed.setFooter("P.S. The GIF is from Castlevania.");

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
