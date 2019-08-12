package com.github.zastrixarundell.torambot;

import org.discordbots.api.client.DiscordBotListAPI;
import org.joda.time.DateTime;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Values
{

    private static DiscordBotListAPI api = null;

    private static String version = "";

    private static int userCount, guildCount, commandCount;

    public static final String profileImageURL = "https://raw.githubusercontent.com/ZastrixArundell/ToramBot/master/images/profile.png";

    public final static String footerMessage = "Support me by going on: http://corneey.com/w2ObhY";

    public final static String toramLogo = "https://toramonline.com/index.php?media/toram-online-logo.50/full&d=1463410056";

    public final static String inviteLink = "http://ceesty.com/w2Ncfe";

    public final static String donationLink = "https://www.patreon.com/zastrix_arundell";

    public final static String supportLink = "https://discord.gg/MdASH22";

    public final static String patreonLogo = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/Patreon_logo.svg/541px-Patreon_logo.svg.png";

    private static DateTime lastDyeUpdate;

    private static BufferedImage[] dyeImages = null;

    private static String prefix = ">";

    public static void setPrefix(String prefix) { Values.prefix = prefix; }

    public static String getPrefix() { return prefix; }

    public static BufferedImage[] getDyeImages() { return dyeImages; }

    public static void setDyeImages(BufferedImage[] dyeImage) { Values.dyeImages = dyeImage; }

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

    public static DateTime getLastDyeUpdate() { return lastDyeUpdate; }

    public static void setLastDyeUpdate(DateTime lastDyeUpdate) { Values.lastDyeUpdate = lastDyeUpdate; }

    public static int getUserCount()
    {
        return userCount;
    }

    public static void setUserCount(int userCount)
    {
        Values.userCount = userCount;
    }

    public static int getGuildCount()
    {
        return guildCount;
    }

    public static void setGuildCount(int guildCount)
    {
        Values.guildCount = guildCount;
    }

    public static int getCommandCount()
    {
        return commandCount;
    }

    public static void setCommandCount(int commandCount)
    {
        Values.commandCount = commandCount;
    }

    public static DiscordBotListAPI getApi() { return api;     }

    public static void setApi(DiscordBotListAPI api) { Values.api = api; }
}
