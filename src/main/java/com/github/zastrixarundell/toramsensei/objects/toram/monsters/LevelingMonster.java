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

package com.github.zastrixarundell.toramsensei.objects.toram.monsters;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class LevelingMonster
{

    private String name, level, location, link;
    private List<String[]> exp = new ArrayList<>();

    public LevelingMonster(Element npcData)
    {
        //Name, type and duration
        level = npcData.getElementsByClass("level-col-1").first().text().split(" ")[1];
        //level = npcData.getElementsByTag("td").first().ownText();

        Element nameAndLocationCol = npcData.getElementsByClass("level-col-2").first();

        name = nameAndLocationCol.getElementsByTag("p").first().text();
        location = nameAndLocationCol.getElementsByTag("p").last().text();

        link = "http://coryn.club/" + npcData.getElementsByTag("a").first()
                .attr("href");

        String stringExp = npcData.getElementsByClass("level-col-3").first().text();
        stringExp = stringExp.replaceAll("%", "\t\n");
        stringExp = stringExp.replaceAll("\t", "%");

        String[] lines = stringExp.split("\n");

        for (String line : lines)
            exp.add(line.split(" ★ "));
    }

    public String getName() { return name; }

    public String getLevel() { return level; }

    public String getLocation() { return location; }

    public List<String[]> getExp() { return exp; }

    public String getLink() { return link; }

}
