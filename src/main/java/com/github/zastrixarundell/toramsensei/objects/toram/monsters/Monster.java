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

import java.util.ArrayList;

public class Monster
{
    private String name, hp, element, exp, tamable, location, weakness;
    private ArrayList<String> items = new ArrayList<>();

    public Monster(Element monsterData)
    {
        monsterData = monsterData.getElementsByTag("td").first();

        name = Parser.nameParser(monsterData.getElementsByTag("h4").text());

        Element stats = monsterData.getElementsByClass("stat-table").first();
        Element statBody = stats.getElementsByTag("tbody").first();

        Element tr;

        tr = statBody.getElementsByTag("tr").first();
        hp = tr.getElementsByTag("td").first().ownText();
        element = tr.getElementsByTag("td").last().ownText();

        tr = statBody.getElementsByTag("tr").get(1);
        exp = tr.getElementsByTag("td").first().ownText();
        tamable = tr.getElementsByTag("td").last().ownText();

        tr = statBody.getElementsByTag("tr").last();
        location = tr.getElementsByTag("td").first().text();

        Element drops = monsterData.getElementsByClass("pad5-table").first();
        Element dropBody = drops.getElementsByTag("tbody").first();

        dropBody.getElementsByTag("tr").forEach(elementTr ->
            {
                Element element = elementTr.getElementsByTag("td").first();
                items.add(element.text());
            }
        );

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
