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

public class Item
{

    private String name, price, proc, app;
    private ArrayList<String> stats = new ArrayList<>();
    private ArrayList<String> mats = new ArrayList<>();
    private ArrayList<String> obtainedFrom = new ArrayList<>();

    public Item(Element itemData)
    {
        //Name, type and duration
        name = Parser.nameParser(itemData.getElementsByClass("card-title").first().text());

        //Price and proc
        Element itemPropMini = itemData.getElementsByClass("item-prop").first();
        Elements divElements = new Elements();

        for (Element element : itemPropMini.getElementsByTag("div"))
            if(element.parent() == itemPropMini)
                divElements.add(element);

        price = divElements.first().getElementsByTag("p").last().text();
        proc = divElements.last().getElementsByTag("p").last().text();;

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
            Element myTabContent = itemData.getElementById("myTabContent");
            Element realStatTable = myTabContent.getElementsByClass("stat-table").last();
            Element statBody = realStatTable.getElementsByTag("tbody").first();

            for (Element trElement : statBody.getElementsByTag("tr"))
                stats.add(trElement.getElementsByTag("td").first().ownText() + ": " +
                        trElement.getElementsByTag("td").get(1).text());
        }
        catch (Exception e)
        {
            stats.add("N/A");
        }

        //ObtainedFrom
        //pad5-table
        try
        {
            Element myTabContent = itemData.getElementById("myTabContent");
            Element obtainedFromTable = myTabContent.getElementsByClass("pad5-table").first();
            Element obtainedFromBody = obtainedFromTable.getElementsByTag("tbody").last();

            for (Element trElement : obtainedFromBody.getElementsByTag("tr"))
            {
                Element tdElement = trElement.getElementsByTag("td").first();

                String value = tdElement.getElementsByTag("font").first().ownText();

                try
                {
                    value = value + " " +
                            tdElement.getElementsByTag("font").last()
                                    .getElementsByTag("a").first().ownText();
                }
                catch (Exception e)
                {
                    value = value + " " +
                            tdElement.getElementsByTag("font").last().ownText();
                }

                obtainedFrom.add(value);
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
