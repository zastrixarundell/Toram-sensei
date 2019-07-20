package com.github.zastrixarundell.torambot.objects;

import com.github.zastrixarundell.torambot.Parser;
import org.jsoup.nodes.Element;

import java.util.HashMap;

public class NPC_Object
{

    private String name, level, location, link;
    private HashMap<String, String> exp = new HashMap<>();

    public NPC_Object(Element npcData)
    {
        //Name, type and duration
        level = npcData.getElementsByTag("td").first().ownText();
        location = npcData.getElementsByTag("td").get(1).ownText();
        link = "http://coryn.club/" + npcData.getElementsByTag("td").get(1)
                .getElementsByTag("a").first()
                .attr("href");

        name = Parser.nameParser(npcData.getElementsByTag("td").get(1)
                .getElementsByTag("a").first().ownText());

        StringBuilder builder = new StringBuilder();
        builder.append(npcData.getElementsByTag("td").last().getElementsByTag("b").first().ownText());
        if(npcData.getElementsByTag("td").last().getElementsByTag("i") != null)
            if(npcData.getElementsByTag("td").last().getElementsByTag("i").first() != null)
                if(npcData.getElementsByTag("td").last().getElementsByTag("i").first().ownText() != null)
                {
                    builder.append(" ");
                    builder.append(npcData.getElementsByTag("td").last().getElementsByTag("i").first().ownText());
                }


        String value = npcData.getElementsByTag("td").last().getElementsByTag("small").first().ownText().substring(2);
        exp.put(builder.toString(), value);
    }

    public String getName() { return name; }

    public String getLevel() { return level; }

    public String getLocation() { return location; }

    public HashMap<String, String> getExp() { return exp; }

    public String getLink() { return link; }
}
