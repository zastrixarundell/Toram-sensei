package com.github.zastrixarundell.toramsensei;

import org.discordbots.api.client.DiscordBotListAPI;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.net.URI;
import java.util.Objects;
import java.util.Properties;

public class Values
{

    private static DiscordBotListAPI api = null;

    private static String version = "";

    private static int userCount, guildCount;

    public final static String toramLogo = "https://toramonline.com/index.php?media/toram-online-logo.50/full&d=1463410056";

    public final static String inviteLink = "https://discordapp.com/oauth2/authorize?client_id=600302983305101323&scope=bot&permissions=0";

    public final static String donationLink = "https://www.patreon.com/zastrix_arundell";

    public final static String supportLink = "https://discord.gg/MdASH22";

    public final static String donationLogo = "https://raw.githubusercontent.com/ZastrixArundell/Toram-sensei/master/images/patreon.png";

    private static String prefix = ">";

    public static void setPrefix(String prefix) { Values.prefix = prefix; }

    public static String getPrefix() { return prefix; }

    private final static JedisPool jedisPool = buildJedisPool();

    private static JedisPool buildJedisPool() {
        final JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(10);
        config.setMaxIdle(5);
        config.setMinIdle(1);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        config.setTestWhileIdle(true);

        final URI redisURI = URI.create(System.getenv("REDIS_URL"));

        String password;
        if ((password = redisURI.getUserInfo()) != null)
            password = password.split(":")[1];


        return password == null ?
                new JedisPool(config, redisURI.getHost(), redisURI.getPort(), Protocol.DEFAULT_TIMEOUT) :
                new JedisPool(config, redisURI.getHost(), redisURI.getPort(), Protocol.DEFAULT_TIMEOUT, password);
    }

    public static Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();

        jedis.clientGetname();

        return jedis;
    }

    public static void closePool() {
        jedisPool.close();
    }

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


    public static int getUserCount() { return userCount; }

    public static void setUserCount(int userCount) { Values.userCount = userCount; }

    public static int getGuildCount() { return guildCount; }

    public static void setGuildCount(int guildCount) { Values.guildCount = guildCount; }

    public static DiscordBotListAPI getApi() { return api; }

    public static void setApi(DiscordBotListAPI api) { Values.api = api; }
}
