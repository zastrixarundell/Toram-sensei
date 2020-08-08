package com.github.zastrixarundell.toramsensei;

import com.github.zastrixarundell.toramsensei.objects.tasks.MonthlyDyesTask;
import gui.ava.html.image.generator.HtmlImageGenerator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Optional;
import java.util.stream.Stream;

public class Helpers
{

    public static MonthlyHashObject getMonthlyImage()
    {
        Optional<BufferedImage> imageOptional = Optional.empty();
        String hash = "";

        try
        {
            Document document = Jsoup.connect("https://toram-id.info/dye").get();

            Element colorTable = document.getElementsByClass("card-table").first();

            Element header = colorTable.getElementsByTag("th").first();
            header.text("Boss Name");

            File file = new File(MonthlyDyesTask.class.getResource(File.separator + "bosslist.css").getFile());

            StringBuilder builder = new StringBuilder();

            try (Stream<String> lines = Files.lines(Paths.get(file.getAbsolutePath())))
            {
                lines.forEach(builder::append);
            }

            for (int i = 0; i < colorTable.getElementsByTag("tr").size(); i++)
                if (i % 2 == 0)
                    colorTable.getElementsByTag("tr").get(i).addClass("tr-even");

            String html =
                    "<style>" + builder.toString() + "</style>\n" +
                            colorTable.toString();

            HtmlImageGenerator generator = new HtmlImageGenerator();
            generator.loadHtml(html);

            imageOptional = Optional.of(generator.getBufferedImage());

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(colorTable.toString().getBytes());
            hash = new String(messageDigest.digest());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return new MonthlyHashObject(imageOptional, hash);
    }

    public static class MonthlyHashObject
    {
        public final Optional<BufferedImage> imageOptional;
        public final String htmlHash;

        public MonthlyHashObject(Optional<BufferedImage> imageOptional, String htmlHash)
        {
            this.imageOptional = imageOptional;
            this.htmlHash = htmlHash;
        }
    }

}
