package com.github.zastrixarundell.toramsensei.commands.player;

import com.github.zastrixarundell.toramsensei.Parser;
import com.github.zastrixarundell.toramsensei.Values;
import com.github.zastrixarundell.toramsensei.commands.DiscordCommand;
import com.github.zastrixarundell.toramsensei.objects.toram.monsters.LevelingMonster;
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

                Elements levelingTables = document.getElementsByClass("item-leveling");

                Element bossTable = levelingTables.first();
                Element minibossTable = levelingTables.get(1);
                Element monsterTable = levelingTables.last();


                //Start Boss
                LevelingMonster bossOne = null, bossTwo = null, bossThree = null;

                try
                {
                    Element[] elementsArray = getFirstThreeMonsterHtml(bossTable);

                    Element bossOneE = elementsArray[0];
                    Element bossTwoE = elementsArray[1];
                    Element bossThreeE = elementsArray[2];

                    if(bossOneE != null)
                        bossOne = new LevelingMonster(bossOneE);

                    if(bossOneE != null)
                        bossTwo = new LevelingMonster(bossTwoE);

                    if(bossOneE != null)
                        bossThree = new LevelingMonster(bossThreeE);
                }
                catch (Exception ignore)
                {

                }

                //Start MiniBoss
                LevelingMonster miniBossOne = null, miniBossTwo = null, miniBossThree = null;
                try
                {
                    Element[] elementsArray = getFirstThreeMonsterHtml(bossTable);

                    Element miniBossOneE = elementsArray[0];
                    Element miniBossTwoE = elementsArray[1];
                    Element miniBossThreeE = elementsArray[2];

                    if(miniBossOneE != null)
                        miniBossOne = new LevelingMonster(miniBossOneE);

                    if(miniBossTwoE != null)
                        miniBossTwo = new LevelingMonster(miniBossTwoE);

                    if(miniBossThreeE != null)
                        miniBossThree = new LevelingMonster(miniBossThreeE);
                }
                catch (Exception ignore)
                {

                }

                //Start Monster
                LevelingMonster monsterOne = null, monsterTwo = null, monsterThree = null;
                try
                {
                    Element[] elementsArray = getFirstThreeMonsterHtml(bossTable);

                    Element monsterOneE = elementsArray[0];
                    Element monsterTwoE = elementsArray[1];
                    Element monsterThreeE = elementsArray[2];

                    if(monsterOneE != null)
                        monsterOne = new LevelingMonster(monsterOneE);

                    if(monsterTwoE != null)
                        monsterTwo = new LevelingMonster(monsterTwoE);

                    if(monsterThreeE != null)
                        monsterThree = new LevelingMonster(monsterThreeE);
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

    public Element[] getFirstThreeMonsterHtml(Element table)
    {
        Element[] toReturn = new Element[]{null, null, null};

        Elements monsterTable = table.getElementsByClass("level-row");

        if(monsterTable.size() == 0)
            return new Element[]{null, null, null};

        for (int i = 0; i < 3 && i < monsterTable.size(); i++)
            toReturn[i] = monsterTable.get(i);

        return toReturn;
    }

    private void showNPC(MessageCreateEvent messageCreateEvent, LevelingMonster levelingMonsterOne, LevelingMonster levelingMonsterTwo, LevelingMonster levelingMonsterThree, String type)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(type)
                .addField(
                        levelingMonsterOne.getName() + " - " + levelingMonsterOne.getLevel() + " - " + levelingMonsterOne.getLocation(),
                        levelingMonsterOne.getExp().get(0)[0] + " - " + levelingMonsterOne.getExp().get(0)[1]);

        if(levelingMonsterTwo != null)
            embed.addField(
                    levelingMonsterTwo.getName() + " - " + levelingMonsterTwo.getLevel() + " - " + levelingMonsterTwo.getLocation(),
                    levelingMonsterTwo.getExp().get(0)[0] + " - " + levelingMonsterTwo.getExp().get(0)[1]);

        if(levelingMonsterThree != null)
            embed.addField(
                    levelingMonsterThree.getName() + " - " + levelingMonsterThree.getLevel() + " - " + levelingMonsterThree.getLocation(),
                    levelingMonsterThree.getExp().get(0)[0] + " - " + levelingMonsterThree.getExp().get(0)[1]);

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
