package com.github.zastrixarundell.toramsensei.commands.search.monsters;

import com.github.zastrixarundell.toramsensei.Parser;
import com.github.zastrixarundell.toramsensei.commands.DiscordCommand;
import com.github.zastrixarundell.toramsensei.objects.toram.monsters.Monster;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class BossCommand extends DiscordCommand
{

    public BossCommand() { super("boss"); }

    @Override
    protected void runCommand(MessageCreateEvent event)
    {
        ArrayList<String> arguments = Parser.argumentsParser(event);

        if (arguments.isEmpty())
        {
            sendError(event, "Empty Search", "You can't search for a boss without specifying which one!");
            return;
        }

        String data = String.join(" ", arguments);

        Runnable runnable = () ->
        {
            try
            {
                Document document = Jsoup.connect("http://coryn.club/monster.php")
                        .data("name", data)
                        .data("type", "B")
                        .data("show", "5")
                        .get();
                Elements tables = document.getElementsByClass("table table-striped");
                Element body = tables.first().getElementsByTag("tbody").first();

                generateMonsters(body).forEach(monster -> sendMonsterMessage(monster, event));

            }
            catch (Exception e)
            {
                sendError(event, "Error while getting the boss!",
                        "An error happened while getting the boss info! Does the specified boss even " +
                                "exist?");
            }
        };

        executeRunnable(event, runnable);
    }

    private void sendMonsterMessage(Monster object, MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(object.getName())
                .addInlineField("HP:", object.getHp())
                .addInlineField("EXP:", object.getExp())
                .addInlineField("Element:", object.getElement())
                .addInlineField("Weakness:", object.getWeakness())
                .addInlineField("Spawns at:", object.getLocation())
                .addInlineField("Tamable:", object.getTamable());

        String drops = String.join("\n", object.getItems());

        embed.addField("Drops:", drops);

        Parser.parseMonsterThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private ArrayList<Monster> generateMonsters(Element body)
    {

        ArrayList<Monster> monsters = new ArrayList<>();

        body.getElementsByTag("tr").forEach(element ->
        {
            if(element.parent() == body)
                try
                {
                    monsters.add(new Monster(element));
                }
                catch (Exception ignore)
                {

                }
        });

        return monsters;
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
