/*
 *             DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *                     Version 2, December 2004
 *
 * Copyright (C) 2020, Zastrix Arundell, https://github.com/ZastrixArundell
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

import java.util.ArrayList;

public class ProficiencyItem implements Comparable<ProficiencyItem>
{

    private int startProficiency, stopProficiency;
    private String name;

    public ProficiencyItem(int startProficiency, int stopProficiency, String name)
    {
        this.startProficiency = startProficiency;
        this.stopProficiency = stopProficiency;
        this.name = name;
    }

    public int getStartProficiency()
    {
        return startProficiency;
    }

    public int getStopProficiency()
    {
        return stopProficiency;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public int compareTo(ProficiencyItem proficiencyItem)
    {
        return this.startProficiency - proficiencyItem.getStartProficiency();
    }

    public enum ProficiencyType
    {
        ALCHEMY("Alchemy","synth", "alch", "alchemy", "synthesis"),
        BLACKSMITH("Blacksmiths","bs", "blacksmith");

        String[] callers;
        String name;

        ProficiencyType(String name, String ... callers)
        {
            this.callers = callers;
            this.name = name;
        }

        public String[] getCallers()
        {
            return callers;
        }

        public String getName()
        {
            return name;
        }
    }

    public static ArrayList<ProficiencyItem> getForType(ProficiencyType type)
    {
        ArrayList<ProficiencyItem> items = new ArrayList<>();

        if(type == ProficiencyType.ALCHEMY)
        {
            items.add(new ProficiencyItem(0, 10, "Revita I"));
            items.add(new ProficiencyItem(10, 30, "Revita II"));
            items.add(new ProficiencyItem(30, 60, "Revita III"));
            items.add(new ProficiencyItem(30, 70, "Regera III"));
            items.add(new ProficiencyItem(55, 70, "Revita IV"));
            items.add(new ProficiencyItem(65, 100, "Vaccine III"));
            items.add(new ProficiencyItem(70, 105, "Revita V"));
            items.add(new ProficiencyItem(100, 155, "Flower Nectar x10"));
            items.add(new ProficiencyItem(105, 120, "Loincloth"));
            items.add(new ProficiencyItem(120, 132, "Revita VI"));
            items.add(new ProficiencyItem(132, 150, "Orichalcum"));
            items.add(new ProficiencyItem(155, 200, "High Purity Orichalcum"));
        }
        else
        {
            items.add(new ProficiencyItem(0, 10, "Shortsword"));
            items.add(new ProficiencyItem(10, 15, "Longsword"));
            items.add(new ProficiencyItem(15, 20, "Adventurer's Garb"));
            items.add(new ProficiencyItem(20, 30, "Gladius"));
            items.add(new ProficiencyItem(30, 35, "Water Staff"));
            items.add(new ProficiencyItem(35, 40, "Plate Armor"));
            items.add(new ProficiencyItem(40, 50, "Brutal Dragon Armor"));
            items.add(new ProficiencyItem(50, 60, "Rapier"));
            items.add(new ProficiencyItem(60, 65, "Mace of Destruction"));
            items.add(new ProficiencyItem(65, 70, "Killer Coat"));
            items.add(new ProficiencyItem(70, 75, "Knight Lance"));
            items.add(new ProficiencyItem(75, 80, "Jade Wings"));
            items.add(new ProficiencyItem(80, 85, "Devil Staff"));
            items.add(new ProficiencyItem(85, 90, "Brigandine"));
        }

        return items;
    }

}
