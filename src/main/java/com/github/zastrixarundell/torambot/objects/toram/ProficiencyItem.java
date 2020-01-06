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

package com.github.zastrixarundell.torambot.objects.toram;

public class ProficiencyItem
{

    private int startProficiency, stopProficiency;
    private String name;
    private ProficiencyType type;

    public ProficiencyItem(int startProficiency, int stopProficiency, String name, ProficiencyType type)
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

    public ProficiencyType getType()
    {
        return type;
    }

    public enum ProficiencyType
    {
        SYNTHESIST, BLACKSMITH
    }

}
