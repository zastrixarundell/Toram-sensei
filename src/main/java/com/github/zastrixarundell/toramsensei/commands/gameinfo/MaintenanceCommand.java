package com.github.zastrixarundell.toramsensei.commands.gameinfo;

import com.github.zastrixarundell.toramsensei.Parser;
import com.github.zastrixarundell.toramsensei.Values;
import com.github.zastrixarundell.toramsensei.commands.DiscordCommand;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MaintenanceCommand extends DiscordCommand
{

    public MaintenanceCommand()
    {
        super("maint", "maintenance");
    }

    @Override
    protected void runCommand(MessageCreateEvent event)
    {
        Runnable runnable = () ->
        {
            try
            {
                Document document = Jsoup.connect("https://en.toram.jp/?type_code=update#contentArea").get();
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
                                        .setFooter("Publish Date: " + link.getElementsByTag("time").text())
                                        .setUrl("https://en.toram.jp" + link.attr("href"))
                                        .setThumbnail(Values.toramLogo)
                                        .setFooter("Publish Date: " + document.getElementsByTag("time").first().text());

                                Parser.parseFooter(embed, event);
                                Parser.parseColor(embed, event);

                                event.getChannel().sendMessage(embed);
                                System.out.println(embed.toString());
                                break;
                            }
                }

            }
            catch (Exception exception)
            {
                sendErrorMessage(event);
            }
        };

        executeRunnable(event, runnable);

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
