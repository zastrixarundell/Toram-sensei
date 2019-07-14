package com.github.zastrixarundell.torambot.objects;

import org.jsoup.nodes.Element;

import java.util.ArrayList;

public class MonsterObject
{
    private String name, hp, element, exp, tamable, location;
    private ArrayList<String> items = new ArrayList<>();

    public MonsterObject(Element monsterData)
    {
        monsterData = monsterData.getElementsByTag("td").first();

        StringBuilder nameBuilder = new StringBuilder();

        name =  monsterData.getElementsByTag("h4").text();
        name = name.replace("   ", " ");

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
}
