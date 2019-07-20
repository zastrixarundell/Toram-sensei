package com.github.zastrixarundell.torambot;

public class Values
{

    final static String footerMessage = "Support me by going on: http://corneey.com/w2ObhY";

    public final static String toramLogo = "https://toramonline.com/index.php?media/toram-online-logo.50/full&d=1463410056";

    private static boolean ranOnHostingService = false;

    private static String prefix = ">";

    static void setPrefix(String prefix) { Values.prefix = prefix; }

    static void setRanOnHostingService(boolean ranOnHostingService) { Values.ranOnHostingService = ranOnHostingService; }

    static boolean isRanOnHostingService() { return ranOnHostingService; }

    public static String getPrefix() { return prefix; }

}
