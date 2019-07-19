package com.github.zastrixarundell.torambot.commands.corynwebsite;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import com.github.zastrixarundell.torambot.objects.ItemObject;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Item implements MessageCreateListener
{

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent)
    {

        //Cancel if the sender is a bot
        if (!messageCreateEvent.getMessageAuthor().isRegularUser())
            return;

        //Cancel if the command is not <prefix>item
        if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(Values.getPrefix() + "item"))
            return;

        ArrayList<String> arguments = new ArrayList<>();

        for (int i = 1; i < messageCreateEvent.getMessageContent().split(" ").length; i++)
            arguments.add(messageCreateEvent.getMessageContent().split(" ")[i]);

        if (arguments.isEmpty())
        {
            emptySearch(messageCreateEvent);
            return;
        }

        String data = String.join(" ", arguments);

        Runnable runnable = () ->
        {
            try
            {
                Document document = Jsoup.connect("http://coryn.club/item.php").data("name", data).get();
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

    private ArrayList<ItemObject> getItems(Element body)
    {

        Elements trs = body.getElementsByTag("tr");

        ArrayList<ItemObject> listOfItems = new ArrayList<>();

        for(int size = 0, count = 0; size < trs.size() && count < 5; size++)
        {
            System.out.println(count + " " + trs.size());
            if(trs.get(size).parent() == body)
            {
                listOfItems.add(new ItemObject(trs.get(size)));
                count++;
            }
        }

        return listOfItems;
    }

    private void sendItemEmbed(ItemObject item, MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(item.getName())
                .setThumbnail("http://coryn.club/images/cc_logo.gif")
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
            embed.setThumbnail(Values.corynLogo);

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void emptySearch(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Empty search!")
                .setThumbnail(Values.corynLogo)
                .setDescription("You can not find an item on coryn.club without specifying which item!");

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendErrorMessage(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Error while getting item!")
                .setDescription("An error happened! Does the item even exist? It may not be yet added into " +
                        "coryn.club!")
                .setThumbnail("http://coryn.club/images/cc_logo.gif");

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

}
