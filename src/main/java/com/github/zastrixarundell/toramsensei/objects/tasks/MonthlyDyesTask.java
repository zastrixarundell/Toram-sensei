package com.github.zastrixarundell.toramsensei.objects.tasks;

import gui.ava.html.image.generator.HtmlImageGenerator;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.TimerTask;
import java.util.stream.Stream;

public class MonthlyDyesTask extends TimerTask
{

    private DiscordApi bot;

    public MonthlyDyesTask(DiscordApi bot) { this.bot = bot; }

    @Override
    public void run()
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

            Optional<Channel> channelOptional = bot.getChannelById("604090683304837123");

            if (!channelOptional.isPresent())
                return;

            Channel channel = channelOptional.get();

            EmbedBuilder embed = new EmbedBuilder().setTitle("Image").setImage(image);

            channel.asServerTextChannel().get().sendMessage(embed);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
