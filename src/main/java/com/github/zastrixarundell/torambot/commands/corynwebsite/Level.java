package com.github.zastrixarundell.torambot.commands.corynwebsite;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import com.github.zastrixarundell.torambot.objects.NPC_Object;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Level implements MessageCreateListener
{

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent)
    {

        if (!messageCreateEvent.getMessageAuthor().isRegularUser())
            return;

        if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(Values.getPrefix() + "level"))
            return;

        ArrayList<String> arguments = new ArrayList<>();

        for (int i = 1; i < messageCreateEvent.getMessageContent().split(" ").length; i++)
            arguments.add(messageCreateEvent.getMessageContent().split(" ")[i]);

        if (arguments.isEmpty())
        {
            sendCommandUsage(messageCreateEvent);
            return;
        }

        int level, range, bonus;

        try
        {
            level = Integer.valueOf(arguments.get(0));
            range = arguments.size() >= 2 ? Integer.valueOf(arguments.get(1)) : 5;
            bonus = arguments.size() >= 3 ? Integer.valueOf(arguments.get(2)) : 0;
        }
        catch (NumberFormatException e)
        {
            sendError(messageCreateEvent, "Can't determine level!", "Are you sure you formatted this correct? There was" +
                    " an error while turning the values into numbers!");
            return;
        }

        String url = "http://coryn.club/leveling.php?lv=" +
                level + "&gap=" + range + "&bonusEXP=" + bonus;

        Runnable runnable = () ->
        {
            try
            {
                Document document = Jsoup.connect(url).get();
                Elements tables = document.getElementsByClass("table table-striped");

                NPC_Object boss, miniboss, monster;
                boss = miniboss = monster = null;

                //Start Boss
                try
                {
                    Element bossTable = tables.first();
                    Element body = bossTable.getElementsByTag("tbody").first();
                    boss = new NPC_Object(body.getElementsByTag("tr").first());
                }
                catch (Exception ignore)
                {
                }

                //Start MiniBoss
                try
                {
                    Element minibossTable = tables.get(1);
                    Element body = minibossTable.getElementsByTag("tbody").first();
                    miniboss = new NPC_Object(body.getElementsByTag("tr").first());
                }
                catch (Exception ignore)
                {
                }

                //Start Monster
                try
                {
                    Element monsterTable = tables.last();
                    Element body = monsterTable.getElementsByTag("tbody").first();
                    monster = new NPC_Object(body.getElementsByTag("tr").first());
                }
                catch (Exception ignore)
                {
                }

                if (boss != null)
                    showNPC(messageCreateEvent, boss, "Boss");
                else
                    sendError(messageCreateEvent, "There is no boss!",
                            "There is no Boss to farm for the specified level!");

                if (miniboss != null)
                    showNPC(messageCreateEvent, miniboss, "Mini Boss");
                else
                    sendError(messageCreateEvent, "There is no Mini Boss!",
                            "There is no Mini Boss to farm for the specified level!");

                if (monster != null)
                    showNPC(messageCreateEvent, monster, "Normal Monster");
                else
                    sendError(messageCreateEvent, "There is no Monster!",
                            "There is no Monster to farm for the specified level!");
            }
            catch (Exception e)
            {
                sendError(messageCreateEvent, "Error while getting EXP!",
                        "An error happened when getting the level, did you put in the correct inputs?");
            }
        };

        (new Thread(runnable)).start();

    }

    private void showNPC(MessageCreateEvent messageCreateEvent, NPC_Object npc, String type)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(type + ": " + npc.getName())
                .addField("Level:", npc.getLevel())
                .setThumbnail(Values.corynLogo)
                .addField("Location:", npc.getLocation())
                .setUrl(npc.getLink());

        if (!npc.getExp().isEmpty())
            for (String key : npc.getExp().keySet())
                embed.addField(key + ":", npc.getExp().get(key));

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendCommandUsage(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Level Command: ")
                .setThumbnail(Values.corynLogo)
                .setDescription("You can use this command to get " +
                        "what you need to farm to gain EXP the fastest!")
                .addField(Values.getPrefix() + "level [your level] (level range) (EXP boost)",
                        "Only [your level] needs to be present here, if the arguments " +
                                "in normal brackets aren't specified the commands uses 5 for the level " +
                                "range value and 0 for the EXP boost value.");

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendError(MessageCreateEvent messageCreateEvent, String name, String description)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(name)
                .setThumbnail(Values.corynLogo)
                .setDescription(description);

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

}
