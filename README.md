![](https://toramonline.com/index.php?media/toram-online-logo.50/full&d=1463410056)

# ToramBot
A Java Discord bot for your Discord Toram guild.

# Setup
This bot is made to be run even by those who are not tech-savvy.

## Requirements
To run this bot you need to have Java installed on your computer. 
If you can run Minecraft you have Java.

## Creating the bot and invite to your server
I'd recommend going to [this](https://github.com/reactiflux/discord-irc/wiki/Creating-a-discord-bot-&-getting-a-token)
 page and create a bot. Copy the bot token!
 
## Starting up the bot
Download the jar file of the bot (I'd recommend using a folder only
for the bot although the bot will not create any new files). Open up
your text editor and write the following:
 
    java -jar [file].jar [token] {custom prefix}
 
 Explanation of the arguments is the following:
    
- **java** means that you will run the following program with Java
- **-jar** means that the following argument will be the jar file which
will be ran (in this case the bot).  
- **[file].jar** is the name of the bot jar file.
- **[token]** is the previously copied token.
- **{custom prefix}** you can add this if you want. It is not mandatory.
 This the prefix used by commands, **?** is the default one if none is 
 specified. 

## Stopping the bot
You just need to write **stop** in the console window of the bot.