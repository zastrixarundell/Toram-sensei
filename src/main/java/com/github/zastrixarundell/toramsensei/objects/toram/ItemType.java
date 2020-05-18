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

public enum ItemType
{

    ARROW("7", "arrow"),
    BOW("9", "bow"),
    BOW_GUN("10", "bowgun", "gun"),
    DAGGER("11", "dagger"),
    HALBERD("26", "halberd", "hb"),
    KATANA("27", "katana", "kat"),
    KNUCKLES("13", "knuckles", "knuckle"),
    MAGIC_DEVICE("15", "magicdevice", "md"),
    ONE_HANDED_SWORD("4", "onehanded", "1h", "ohs"),
    STAFF("19", "staff"),
    TWO_HANDED_SWORD("5", "twohanded", "2h", "ths"),
    ADDITIONAL("6", "additional", "add"),
    ARMOR("8", "armor", "arm"),
    SHIELD("17", "shield"),
    SPECIAL("18", "special", "spec"),
    GEM("12", "gem"),
    ITEM(null, "item");

    String code, longText, name;
    String[] callers;

    ItemType(String code, String ... callers)
    {
        this.callers = callers;
        this.code = code;
        name = String.join(" ", name().toLowerCase().split("_"));
        longText = (name().toLowerCase().startsWith("a") ? "an " : "a ") + name;
    }

    public String getCode() { return code; }

    public String getLongText() { return longText; }

    public String getName() { return name; }

    public String[] getCallers() { return callers; }
}
