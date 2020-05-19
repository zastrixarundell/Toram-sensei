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

package com.github.zastrixarundell.toramsensei.objects.toram;

import com.github.zastrixarundell.toramsensei.Parser;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class Item
{

    private final String name, price, proc;
    private final ArrayList<String> stats = new ArrayList<>();
    private final ArrayList<String> obtainedFrom = new ArrayList<>();

    private String app;
    private ArrayList<String> mats = new ArrayList<>();

    public Item(Element itemData)
    {
        //Name, type and duration
        name = capitalize(Parser.nameParser(itemData.getElementsByClass("card-title").first().text()));

        //Price and proc
        Element itemPropMini = itemData.getElementsByClass("item-prop").first();
        Elements divElements = getChildrenElements(itemPropMini);

        price = capitalize(divElements.first().getElementsByTag("p").last().text());
        proc = capitalize(divElements.last().getElementsByTag("p").last().text());

        //Image
        try
        {
            Element appDiv = itemData.getElementsByClass("app-div").first();
            Element imageTd = appDiv.getElementsByTag("td").first();
            String appUrl = imageTd.attr("background");
            app = "http://coryn.club/" + appUrl.replace(" ", "%20");
        }
        catch (Exception e)
        {
            app = null;
        }

        //Stats
        try
        {
            Element statsList = itemData.getElementsByClass("item-basestat").first();
            Elements statData = getChildrenElements(statsList);

            for (int i = 1; i < statData.size(); i++)
            {
                Elements statChildren = getChildrenElements(statData.get(i));
                stats.add(statChildren.first().text() + ": " + statChildren.last().text());
            }
        }
        catch (Exception e)
        {
            stats.add("N/A");
        }

        //ObtainedFrom
        try
        {
            Elements obtainedFromContent = getChildrenElements(itemData.getElementsByClass("item-obtainfrom").first().parent());

            Optional<Element> obtainedSourceListOptional = getFirstChildWithClassPartial(obtainedFromContent, "js-pagination");

            if(!obtainedSourceListOptional.isPresent())
                throw new Exception();

            Element innerDiv = getChildrenElements(obtainedSourceListOptional.get()).first();

            for (Element sourceRow : getChildrenElements(innerDiv))
            {
                Elements divChildren = getChildrenElements(sourceRow);

                String monsterName = divChildren.first().text();
                String monsterLocation = divChildren.last().text();

                obtainedFrom.add(monsterName + " - " + monsterLocation);
            }
        }
        catch (Exception e)
        {
            obtainedFrom.add("N/A");
        }

        //Recipe
        try
        {
            Element myTabContent = itemData.getElementById("myTabContent");

            for (Element element : myTabContent.getAllElements())
            {
                if(element.id() == null)
                    continue;

                if(element.id().contains("recipe"))
                {
                    Element trElement = element.getElementsByTag("td").last();
                    this.mats = new ArrayList<>(Arrays.asList(trElement.text().split("- ")));
                }
            }

        }
        catch (Exception e)
        {
            mats.add("N/A");
        }
    }

    private String capitalize(String string)
    {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    private Elements getChildrenElements(Element element)
    {
        Elements elements = new Elements();

        for (Element child : element.children())
            if(child.parent() == element)
                elements.add(child);

        return elements;
    }

    private Optional<Element> getFirstChildWithClassPartial(Elements elements, String classPartial)
    {
        for (Element element : elements)
            for (String className : element.classNames())
                if(className.contains(classPartial))
                    return Optional.of(element);

        return Optional.empty();
    }

    public String getName()
    {
        return name;
    }

    public String getPrice()
    {
        return price;
    }

    public String getProc()
    {
        return proc;
    }

    public String getApp()
    {
        return app;
    }

    public ArrayList<String> getStats()
    {
        return stats;
    }

    public ArrayList<String> getObtainedFrom()
    {
        return obtainedFrom;
    }

    public ArrayList<String> getMats()
    {
        return mats;
    }

}
