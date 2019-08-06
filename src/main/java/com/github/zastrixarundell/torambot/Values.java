package com.github.zastrixarundell.torambot;

import java.awt.image.BufferedImage;
import java.util.Properties;

public class Values
{

    private static String version = "";

    public final static String footerMessage = "Support me by going on: http://corneey.com/w2ObhY";

    public final static String toramLogo = "https://toramonline.com/index.php?media/toram-online-logo.50/full&d=1463410056";

    public final static String inviteLink = "http://ceesty.com/w2Ncfe";

    public final static String donationLink = "https://www.patreon.com/zastrix_arundell";

    public final static String supportLink = "https://discord.gg/MdASH22";

    public final static String patreonLogo = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/Patreon_logo.svg/541px-Patreon_logo.svg.png";

    private static BufferedImage dyeImage = null;

    private static String prefix = ">";

    static void setPrefix(String prefix) { Values.prefix = prefix; }

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
