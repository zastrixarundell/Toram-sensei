package com.github.zastrixarundell.torambot.commands.corynwebsite;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import com.github.zastrixarundell.torambot.objects.MonsterObject;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Monster implements MessageCreateListener
{

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent)
    {

        if (!messageCreateEvent.getMessageAuthor().isRegularUser())
            return;

        if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(Values.getPrefix() + "monster"))
            return;

        ArrayList<String> arguments = new ArrayList<>();

        for (int i = 1; i < messageCreateEvent.getMessageContent().split(" ").length; i++)
            arguments.add(messageCreateEvent.getMessageContent().split(" ")[i]);

        if (arguments.isEmpty())
        {
            sendCommandUsage(messageCreateEvent);
            return;
        }

        String data = String.join(" ", arguments);

        Runnable runnable = () ->
        {
            try
            {
                Document document = Jsoup.connect("http://coryn.club/monster.php").data("name", data).get();
                Elements tables = document.getElementsByClass("table table-striped");
                Element body = tables.first().getElementsByTag("tbody").first();

                generateMonsters(body).forEach(monsterObject -> sendMonsterMessage(monsterObject, messageCreateEvent));

            }
            catch (Exception e)
            {
                sendError(messageCreateEvent, "Error while getting the monster!",
                        "An error happened while getting the monster info! Does the specified monster even " +
                                "exist?");
            }
        };

        (new Thread(runnable)).start();

    }

    private void sendMonsterMessage(MonsterObject object, MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(object.getName())
                .setThumbnail(Values.corynLogo)
                .addInlineField("HP:", object.getHp())
                .addInlineField("Element:", object.getElement())
                .addInlineField("EXP:", object.getExp())
                .addInlineField("Tamable:", object.getTamable())
                .addInlineField("Spawns at:", object.getLocation());

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        String drops = String.join("\n", object.getItems());

        embed.addField("Drops:", drops);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private ArrayList<MonsterObject> generateMonsters(Element body)
    {

        ArrayList<MonsterObject> monsterObjects = new ArrayList<>();

        body.getElementsByTag("tr").forEach(element ->
        {
            if(element.parent() == body)
                try
                {
                    monsterObjects.add(new MonsterObject(element));
                }
                catch (Exception ignore)
                {

                }
        });

        return monsterObjects;
    }

    private void sendCommandUsage(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Monster Command: ")
                .setThumbnail("http://coryn.club/images/cc_logo.gif")
                .setDescription("You can use this command to get " +
                        "info about a monster!")
                .addField(Values.getPrefix() + "monster [monster]", "This command is used to info about a monster!");

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendError(MessageCreateEvent messageCreateEvent, String name, String description)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(name)
                .setThumbnail("http://coryn.club/images/cc_logo.gif")
                .setDescription(description);

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

}
