package com.github.zastrixarundell.torambot;

import java.awt.image.BufferedImage;
import java.util.Properties;

public class Values
{

    private static String version = "";

    public final static String footerMessage = "Support me by going on: http://corneey.com/w2ObhY";

    public final static String toramLogo = "https://toramonline.com/index.php?media/toram-online-logo.50/full&d=1463410056";

    public final static String inviteLink = "http://ceesty.com/w2Ncfe";

    public final static String donationLink = "https://donatebot.io/checkout/602112468961067011";

    public static BufferedImage dyeImage = null;

    private static boolean ranOnHostingService = false;

    private static String prefix = ">";

    static void setPrefix(String prefix) { Values.prefix = prefix; }

    static void setRanOnHostingService(boolean ranOnHostingService) { Values.ranOnHostingService = ranOnHostingService; }

    static boolean isRanOnHostingService() { return ranOnHostingService; }

    public static String getPrefix() { return prefix; }

    public static BufferedImage getDyeImage() { return dyeImage; }

    public static void setDyeImage(BufferedImage dyeImage) { Values.dyeImage = dyeImage; }

    public static void getMavenVersion()
    {
        try
        {
            final Properties properties = new Properties();
            properties.load(Values.class.getClassLoader().getResourceAsStream("values.properties"));
            version = properties.getProperty("version");
        }
        catch (Exception ignore)
        {

        }

    }

    public static String getVersion() { return version; }
}
