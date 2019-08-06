package com.github.zastrixarundell.torambot.commands.search.items.gear;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import com.github.zastrixarundell.torambot.objects.Item;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ShieldCommand implements MessageCreateListener
{

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent)
    {

        //Cancel if the sender is a bot
        if (!messageCreateEvent.getMessageAuthor().isRegularUser())
            return;

        //Cancel if the command is not <prefix>item
        if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(Values.getPrefix() + "shield"))
            return;

        ArrayList<String> arguments = Parser.argumentsParser(messageCreateEvent);

        if (arguments.isEmpty())
        {
            emptySearch(messageCreateEvent);
            return;
        }

        String data = String.join(" ", arguments);

        Runnable runnable;
        runnable = () ->
        {
            try
            {
                Document document = Jsoup.connect("http://coryn.club/item.php")
                        .data("name", data)
                        .data("type", "17")
                        .get();

                Element table = document.getElementsByClass("table table-striped").first();
                Element body = table.getElementsByTag("tbody").first();

                getItems(body).forEach(item -> sendItemEmbed(item, messageCreateEvent));
            }
            catch (Exception e)
            {
                sendErrorMessage(messageCreateEvent);
            }
        };

        (new Thread(runnable)).start();
    }

    private ArrayList<Item> getItems(Element body)
    {

        Elements trs = body.getElementsByTag("tr");

        ArrayList<Item> listOfItems = new ArrayList<>();

        for(int size = 0, count = 0; size < trs.size() && count < 5; size++)
            if(trs.get(size).parent() == body)
            {
                listOfItems.add(new Item(trs.get(size)));
                count++;
            }

        return listOfItems;
    }

    private void sendItemEmbed(Item item, MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(item.getName())
                .addInlineField("NPC sell price:", item.getPrice())
                .addInlineField("Processed into:", item.getProc());

        String stats = String.join("\n", item.getStats());

        embed.addField("Stats/Effect:", stats);

        StringBuilder stringBuilder = new StringBuilder();

        {

            for (int i = 0; i < item.getObtainedFrom().size() && i < 10; i++)
            {
                String key = item.getObtainedFrom().get(i);
                stringBuilder.append(i == 0 ? key : "\n" + key);
            }
        }

        embed.addInlineField("Obtained from:", stringBuilder.toString());

        if (item.getApp() != null)
            embed.setThumbnail(item.getApp());
        else
            Parser.parseThumbnail(embed, messageCreateEvent);

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void emptySearch(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Empty search!")
                .setDescription("You can not find an item without specifying the item!");

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendErrorMessage(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Error while getting item!")
                .setDescription("An error happened! Does the item even exist? The item may not be added yet.");

        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

}
