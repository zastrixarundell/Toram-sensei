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

package com.github.zastrixarundell.torambot.objects.tasks;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.sql.*;
import java.util.Optional;
import java.util.TimerTask;

public class UpdateDisplayer extends TimerTask
{

    private DiscordApi api;

    public UpdateDisplayer(DiscordApi api)
    {
        this.api = api;
    }

    @Override
    public void run()
    {
        try
        {
            String sqlUrl = System.getenv("SQL_URL");
            Connection connection = DriverManager.getConnection("jdbc:" + sqlUrl);
            createTable(connection);
            Optional<String> cache = getCachedURL(connection);

            Document document = Jsoup.connect("http://en.toram.jp/information/")
                    .data("type_code", "event")
                    .get();

            Element section = document.getElementById("news");
            Element box = section.getElementsByClass("useBox").first();
            Element urlElement = box.getElementsByTag("a").first();
            String url = urlElement.attr("href");

            System.out.println(url);

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

                saveURL(connection, url);

                for (Server server : api.getServers())
                    for (ServerTextChannel channel  : server.getTextChannels())
                        if(channel.getName().equalsIgnoreCase("toram-sensei-news"))
                            channel.sendMessage(text);

            }

            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void createTable(Connection connection) throws SQLException
    {
        String statement = "CREATE TABLE IF NOT EXISTS news (" +
                "id int NOT NULL," +
                "value VARCHAR(256)," +
                "PRIMARY KEY (id)" +
                ");";

        Statement sqlStatement = connection.createStatement();
        sqlStatement.executeUpdate(statement);
    }

    private Optional<String> getCachedURL(Connection connection)
    {
        try
        {
            String statement = "SELECT * FROM news";
            Statement sqlStatement = connection.createStatement();
            ResultSet resultSet = sqlStatement.executeQuery(statement);

            resultSet.next();

            return Optional.of(resultSet.getString("value"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private void saveURL(Connection connection, String url) throws SQLException
    {
        String command = "INSERT INTO news(id, value)\n" +
                "VALUES (?, ?)\n" +
                "ON DUPLICATE KEY UPDATE\n" +
                "id = VALUES(id)," +
                "value = VALUES(value);";

        PreparedStatement pstmt = connection.prepareStatement(command);
        pstmt.setInt(1, 1);
        pstmt.setString(2, url);
        pstmt.executeUpdate();
    }

    private boolean shouldBroadcast(Optional<String> oldUrl, String newUrl)
    {
        return oldUrl.map(s -> !s.equals(newUrl)).orElse(true);
    }

}
