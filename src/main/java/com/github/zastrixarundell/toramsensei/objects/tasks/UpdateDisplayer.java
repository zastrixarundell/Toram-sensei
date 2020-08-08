/*
 *             DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *                     Version 2, December 2004
 *
 * Copyright (C) 2019, Zastrix Arundell, https://github.com/ZastrixArundell
 *
 *  Everyone is permitted to copy and distribute verbatim or modified
 *  copies of this license document, and changing it is allowed as long
 *  as the name is changed.
 *
 *             DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *    TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *   0. You just DO WHAT THE FUCK YOU WANT TO.
 *
 *
 */

package com.github.zastrixarundell.toramsensei.objects.tasks;

import com.github.zastrixarundell.toramsensei.Database;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.sql.Connection;
import java.util.Optional;
import java.util.TimerTask;

public class UpdateDisplayer extends TimerTask
{

    private final DiscordApi api;

    public UpdateDisplayer(DiscordApi api)
    {
        this.api = api;
    }

    @Override
    public void run()
    {
        try(Connection connection = Database.getConnection())
        {
            Database.createNewsTable(connection);

            Optional<String> cache = Database.getCachedNews(connection);

            Document document = Jsoup.connect("http://en.toram.jp/information/")
                    .data("type_code", "all")
                    .get();

            Element section = document.getElementById("news");
            Element box = section.getElementsByClass("useBox").first();

            Element urlElement = box.getElementsByTag("a").first();
            String url = urlElement.attr("href");

            /*
                Nuked this parts but going to keep it just in case.

                Element imageElement = urlElement.getElementsByTag("img").first();
                String imageUrl = imageElement.attr("src");

                if(imageUrl.contains("other.png"))
                {
                    System.out.println("Orb shop url: " + url);
                    saveURL(connection, url);
                    connection.close();
                    return;
                }
             */

            System.out.println("New url: " + url);

            if(cache.isPresent())
                System.out.println("Old: " + cache.get());
            else
                System.out.println("Old: none");

            if(shouldBroadcast(cache, url))
            {
                document = Jsoup.connect("http://en.toram.jp" + url).get();
                document.select("br").append("\\n");
                Element id = document.getElementById("news");
                String text = id.text().replaceAll("\\\\n", "\n").replaceAll("\\*", "");

                text = text.replaceAll("\n ", "\n");

                if(text.endsWith(" Back"))
                    text = text.substring(0, text.length() - 5);

                if(text.length() > 2000)
                {
                    text = text.substring(0, 1800);
                    text += "...\n\n***There are too much characters for this message. Go to http://en.toram.jp"
                            + url + " for more info!***";
                }

                Database.setNewsElement(connection, url);

                for (Server server : api.getServers())
                    for (ServerTextChannel channel  : server.getTextChannels())
                        if(channel.getName().equalsIgnoreCase("toram-sensei-news"))
                            channel.sendMessage(text);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private boolean shouldBroadcast(Optional<String> oldUrl, String newUrl)
    {
        return oldUrl.map(s -> !s.equals(newUrl)).orElse(true);
    }

}
