package com.github.zastrixarundell.torambot.commands.gameinfo;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EventsCommand implements MessageCreateListener
{

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent)
    {

        if (!messageCreateEvent.getMessageAuthor().isRegularUser())
            return;

        if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(Values.getPrefix() + "events"))
            return;

        Runnable runnable = () ->
        {
            try
            {
                Document document = Jsoup.connect("https://en.toram.jp/information/?type_code=event").get();
                Elements links = document.select("a[href]");

                for (Element link : links)
                {
                    if (link.attr("href").startsWith("/information/detail/?information_id="))
                        if (link.text() != null)
                            if (!link.text().isEmpty())
                            {

                                document = Jsoup.connect("https://en.toram.jp" + link.attr("href")).get();

                                Elements divs = document.getElementsByClass("useBox newsBox");
                                Elements Headers = document.getElementsByTag("h1");
                                Element header = Headers.first();
                                Element div = divs.first();

                                String text = div.text();
                                text = text.substring(header.ownText().length() + 12, 267 + header.ownText().length());
                                text = text + "... open to read more!";

                                EmbedBuilder embed = new EmbedBuilder()
                                        .setTitle(header.ownText())
                                        .setDescription(text)
                                        .setUrl("https://en.toram.jp" + link.attr("href"))
                                        .setThumbnail(Values.toramLogo)
                                        .setFooter("Publish Date: " + link.getElementsByTag("time").text());

                                Parser.parseFooter(embed, messageCreateEvent);
                                Parser.parseColor(embed, messageCreateEvent);

                                messageCreateEvent.getChannel().sendMessage(embed);
                                break;
                            }
                }

            }
            catch (Exception exception)
            {
                sendErrorMessage(messageCreateEvent);
            }
        };

        (new Thread(runnable)).start();

    }

    private void sendErrorMessage(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Error while getting info!")
                .setDescription("An error happened! Is the site maybe down?")
                .setThumbnail(Values.toramLogo);

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

}
