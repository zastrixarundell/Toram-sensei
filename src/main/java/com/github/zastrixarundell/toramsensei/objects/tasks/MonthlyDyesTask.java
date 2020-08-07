package com.github.zastrixarundell.toramsensei.objects.tasks;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.swing.*;
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

            File file = new File(MonthlyDyesTask.class.getResource(File.separator + "bootstrap.min.css").getFile());

            StringBuilder builder = new StringBuilder();

            try (Stream<String> lines = Files.lines(Paths.get(file.getAbsolutePath()))) {
                lines.forEach(builder::append);
            }

            String html =
                    " <!DOCTYPE html>\n" +
                    "<html>\n" +
                        "<head>\n" +
                        "<title>Title of the document</title>\n" +
                        "<style>" + builder.toString() + "</style>\n" +
                        "</head>\n" +
                    "\n" +
                    "<body>\n" +
                        colorTable.toString() + "\n" +
                    "</body>\n" +
                    "</html>";



            //load the webpage into the editor
            JEditorPane pane = new JEditorPane();
            pane.setContentType("text/html");
            pane.setText(html);
            pane.setSize(1920, 1080);

            //create a new image
            BufferedImage image = new BufferedImage(pane.getWidth(), pane.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);

            //paint the editor onto the image
            SwingUtilities.paintComponent(image.createGraphics(),
                    pane,
                    new JPanel(),
                    0, 0, image.getWidth(), image.getHeight());

            /*
            HtmlImageGenerator generator = new HtmlImageGenerator();
            generator.loadHtml(html);
            BufferedImage image = generator.getBufferedImage();

             */

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

        /*
        try
        {
            System.out.println("Starting forums user!");
            ToramForumsUser user = new ToramForumsUser(bot.getToken());
            System.out.println("Starting monthly dyes!");
            user.setDye();
            System.out.println("Finished monthly dyes!");
            user.close();

            if(Values.getDyeImages() == null)
            {
                System.out.println("There are monthly dyes!");

                if(MonthlyCommand.instance != null)
                {
                    bot.removeListener(MonthlyCommand.instance);
                    MonthlyCommand.instance = null;
                }
            }
            else
            if(MonthlyCommand.instance == null)
            {
                bot.addListener(new MonthlyCommand());
                System.out.println("Updated monthly dyes!");
            }
        }
        catch (Exception e)
        {
            Values.setDyeImages(null);
            System.out.println("An error happened while updating the monthly dye data!");
            e.printStackTrace();

            if(MonthlyCommand.instance != null)
            {
                bot.removeListener(MonthlyCommand.instance);
                MonthlyCommand.instance = null;
            }
        }*/
    }
}
