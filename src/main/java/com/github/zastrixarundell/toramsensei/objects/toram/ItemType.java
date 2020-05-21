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

    ARROW("7", "atk", "arrow"),
    BOW("9", "atk","bow"),
    BOW_GUN("10", "atk", "bowgun", "gun"),
    DAGGER("11", "atk", "dagger"),
    HALBERD("26", "atk", "halberd", "hb"),
    KATANA("27", "atk","katana", "kat"),
    KNUCKLES("13", "atk","knuckles", "knuckle"),
    MAGIC_DEVICE("15", "atk","magicdevice", "md"),
    ONE_HANDED_SWORD("4", "atk","onehanded", "1h", "ohs"),
    STAFF("19", "atk","staff"),
    TWO_HANDED_SWORD("5", "atk", "twohanded", "2h", "ths"),
    ADDITIONAL("6", "def","additional", "add"),
    ARMOR("8", "def","armor", "arm"),
    SHIELD("17", "def","shield"),
    SPECIAL("18", "def","special", "spec"),
    GEM("12", null,"gem"),
    CRYSTA(null, null, "xtal", "crysta", "crystal"),
    ITEM(null, null,"item");

    String code, longText, name, type;
    String[] callers;

    ItemType(String code, String type, String ... callers)
    {
        this.callers = callers;
        this.code = code;
        this.type = type;
        name = String.join(" ", name().toLowerCase().split("_"));
        longText = (name().toLowerCase().startsWith("a") ? "an " : "a ") + name;
    }

    public String getCode() { return code; }

    public String getLongText() { return longText; }

    public String getName() { return name; }

    public String[] getCallers() { return callers; }

    public String getType() { return type; }
}
