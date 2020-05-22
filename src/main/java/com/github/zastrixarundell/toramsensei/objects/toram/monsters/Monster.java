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

import com.github.zastrixarundell.toramsensei.Parser;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Monster
{
    private final String name, hp, element, exp, tamable, location, weakness, level;
    private final ArrayList<String> items = new ArrayList<>();

    public Monster(Element monsterData)
    {
        String name = Parser.nameParser(monsterData.getElementsByClass("card-title-inverse").first().text());

        // Stats

        Element statDiv = monsterData.getElementsByClass("monster-no-pic").first();

        Elements stats = getChildrenElements(getChildrenElements(getChildrenElements(statDiv).first()).first());

        level = stats.first().getElementsByTag("p").last().text();
        String type = stats.get(1).getElementsByTag("p").last().text();

        if(type.equalsIgnoreCase("-"))
            type = "";
        else
            type = " " + type;

        this.name = name + type + " - Level: " + level;

        hp = stats.get(2).getElementsByTag("p").last().text();

        element = stats.get(3).getElementsByTag("p").last().text();

        switch(element.toLowerCase())
        {
            case "earth":
                weakness = "Fire";
                break;
            case "fire":
                weakness = "Water";
                break;
            case "water":
                weakness = "Wind";
                break;
            case "wind":
                weakness = "Earth";
                break;
            case "dark":
                weakness = "Light";
                break;
            case "light":
                weakness = "Dark";
                break;
            default:
                weakness = "No weakness";
        }

        exp = stats.get(4).getElementsByTag("p").last().text();
        tamable = stats.get(5).getElementsByTag("p").last().text();

        Element spawnAtDiv = monsterData.getElementsByClass("item-prop").get(1);

        location = getChildrenElements(spawnAtDiv).last().text();

        Elements drops = getChildrenElements(monsterData.getElementsByClass("monster-drop-list").first());

        drops.forEach(dropRow -> items.add(dropRow.text()));
    }

    private Elements getChildrenElements(Element element)
    {
        Elements elements = new Elements();

        for (Element child : element.children())
            if(child.parent() == element)
                elements.add(child);

        return elements;
    }

    public String getName()
    {
        return name;
    }

    public String getHp()
    {
        return hp;
    }

    public String getElement()
    {
        return element;
    }

    public String getExp()
    {
        return exp;
    }

    public String getTamable()
    {
        return tamable;
    }

    public String getLocation()
    {
        return location;
    }

    public ArrayList<String> getItems()
    {
        return items;
    }

    public String getWeakness()
    {
        return weakness;
    }

    public enum MonsterType
    {
        MONSTER("N", "monster"),
        MINIBOSS("M", "miniboss", "mb", "mboss", "mini"),
        BOSS("B", "boss");

        private final String type;
        private final String[] callers;

        MonsterType(String type, String ... callers)
        {
            this.type = type;
            this.callers = callers;
        }

        public String getType()
        {
            return type;
        }

        public String[] getCallers()
        {
            return callers;
        }
    }
}
