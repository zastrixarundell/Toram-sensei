package com.github.zastrixarundell.torambot;

import org.discordbots.api.client.DiscordBotListAPI;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Values
{

    private static DiscordBotListAPI api = null;

    private static String version = "";

    private static int userCount, guildCount;

    public static final String profileImageURL = "https://raw.githubusercontent.com/ZastrixArundell/ToramBot/master/images/profile.png";

    public final static String toramLogo = "https://toramonline.com/index.php?media/toram-online-logo.50/full&d=1463410056";

    public final static String inviteLink = "https://discordapp.com/oauth2/authorize?client_id=600302983305101323&scope=bot&permissions=0";

    public final static String donationLink = "https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=9U8Z5E9L485G2&source=url";

    public final static String supportLink = "https://discord.gg/MdASH22";

    public final static String donationLogo = "http://pngimg.com/uploads/paypal/paypal_PNG22.png";

    private static DateTime lastDyeUpdate;

    private static List<String> dyeImages = null;

    private static String prefix = ">";

    public static void setPrefix(String prefix) { Values.prefix = prefix; }

    public static String getPrefix() { return prefix; }

    public static List<String> getDyeImages() { return dyeImages; }

    public static void setDyeImages(List<String> dyeImages) { Values.dyeImages = dyeImages; }

    static void getMavenVersion()
    {
        try
        {
            final Properties properties = new Properties();
            properties.load(Objects.requireNonNull(Values.class.getClassLoader().getResourceAsStream("values.properties")));
            version = properties.getProperty("version");
        }
        catch (Exception ignore)
        {

        }
    }

    public static String getVersion() { return version; }

    public static DateTime getLastDyeUpdate() { return lastDyeUpdate; }

    public static void setLastDyeUpdate(DateTime lastDyeUpdate) { Values.lastDyeUpdate = lastDyeUpdate; }

    public static int getUserCount() { return userCount; }

    public static void setUserCount(int userCount) { Values.userCount = userCount; }

    public static int getGuildCount() { return guildCount; }

    public static void setGuildCount(int guildCount) { Values.guildCount = guildCount; }

    public static DiscordBotListAPI getApi() { return api; }

    public static void setApi(DiscordBotListAPI api) { Values.api = api; }
}
