package com.github.zastrixarundell.toramsensei.commands.gameinfo;

import com.github.zastrixarundell.toramsensei.Parser;
import com.github.zastrixarundell.toramsensei.Values;
import com.github.zastrixarundell.toramsensei.commands.DiscordCommand;
import com.github.zastrixarundell.toramsensei.objects.tasks.MonthlyDyesTask;
import gui.ava.html.image.generator.HtmlImageGenerator;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class MonthlyCommand extends DiscordCommand
{

    public static MonthlyCommand instance = null;

    public MonthlyCommand()
    {
        super("monthly", "month");
    }

    @Override
    protected void runCommand(MessageCreateEvent event)
    {
        Runnable runnable = () ->
        {

            try
            {
                Document document = Jsoup.connect("https://toram-id.info/dye").get();

                Element colorTable = document.getElementsByClass("card-table").first();

                Element header = colorTable.getElementsByTag("th").first();
                header.text("Boss Name");

                File file = new File(MonthlyDyesTask.class.getResource(File.separator + "bosslist.css").getFile());

                StringBuilder builder = new StringBuilder();

                try (Stream<String> lines = Files.lines(Paths.get(file.getAbsolutePath()))) {
                    lines.forEach(builder::append);
                }

                for(int i = 0; i < colorTable.getElementsByTag("tr").size(); i++)
                    if(i % 2 == 0)
                        colorTable.getElementsByTag("tr").get(i).addClass("tr-even");

                String html =
                        "<style>" + builder.toString() + "</style>\n" +
                                colorTable.toString();

                HtmlImageGenerator generator = new HtmlImageGenerator();
                generator.loadHtml(html);
                BufferedImage image = generator.getBufferedImage();

                sendDyeMessage(event, image);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                sendErrorMessage(event);
            }
        };

        executeRunnable(event, runnable);
    }

    private void sendDyeMessage(MessageCreateEvent messageCreateEvent, BufferedImage image)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Currently monthly dye drops!")
                .setDescription("This is the latest monthly dye drop. The current lanauge is Indonesian but will be" +
                        "translated to English soon!")
                .setImage(image);

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendErrorMessage(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Error while getting monthly dye!")
                .setDescription("An error happened! Is the site maybe down?")
                .setThumbnail(Values.toramLogo);

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

}
