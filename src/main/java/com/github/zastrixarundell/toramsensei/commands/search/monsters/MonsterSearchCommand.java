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

public class MonsterSearchCommand extends DiscordCommand
{

    public MonsterSearchCommand(Monster.MonsterType type)
    {
        super(type.getCallers());
        this.type = type;
    }

    private final Monster.MonsterType type;

    @Override
    protected void runCommand(MessageCreateEvent event)
    {
        ArrayList<String> arguments = Parser.argumentsParser(event);

        if (arguments.isEmpty())
        {
            sendError(event, "Empty Search", "You can't search for a monster without specifying which one!");
            return;
        }

        String data = String.join(" ", arguments);

        Runnable runnable = () ->
        {
            try
            {
                Document document = Jsoup.connect("http://coryn.club/monster.php")
                        .data("name", data)
                        .data("type", type.getType())
                        .data("show", "5")
                        .get();

                Element cardContainer = document.getElementsByClass("card-container").first();

                generateMonsters(cardContainer).forEach(monster -> sendMonsterMessage(monster, event));

            }
            catch (Exception e)
            {
                sendError(event, "Error while getting the monster!",
                        "An error happened while getting the monster info! Does the specified monster even " +
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

    private ArrayList<Monster> generateMonsters(Element cardContainer)
    {
        Elements divs = cardContainer.getElementsByTag("div");

        ArrayList<Monster> listOfMonsters = new ArrayList<>();

        for(int size = 0, count = 0; size < divs.size() && count < 5; size++)
        {
            Element div = divs.get(size);

            if (div.parent() == cardContainer)
                if (!div.hasClass("card-adsense"))
                {
                    listOfMonsters.add(new Monster(div));
                    count++;
                }

        }

        return listOfMonsters;
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
