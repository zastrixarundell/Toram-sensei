<p align="center"><img src="https://toramonline.com/index.php?media/toram-online-logo.50/full&d=1463410056" /></p>

# ToramBot
A Java Discord bot for your Discord Toram guild.
<p align="center"><img src="https://raw.githubusercontent.com/ZastrixArundell/ToramBot/master/images/profile.png" /></p>

# Setup
This bot is made to be run even by those who are not tech-savvy.

## Requirements
To run this bot you need to have Java installed on your computer.

## Creating the bot and invite to your server
I'd recommend going to [this](https://github.com/reactiflux/discord-irc/wiki/Creating-a-discord-bot-&-getting-a-token)
 page and create a bot. Copy the bot token!
 
## Starting up the bot
Download the jar file of the bot (I'd recommend using a folder only
for the bot although the bot will not create any new files). Open up
your text editor and write the following:
 
    java -jar [file].jar
    
After that you need to set up environment variables:


- CLOUDINARY_API_SECRET
  This will be needed when you want to use monthly dyes. Search on google how to get your own free
  cloudinary API key (the secret one is usually the longer).
  
- CLOUDINARY_NAME
  This is the name used to log in the cloud, usually just the username you use. 
  
- CLOUDINARY_API_KEY
  It's the more public api, usually the shorter one.
  
- FORUMS_USERNAME
  As which profile do you want to log in to the forums for the dye update.
  
- FORUMS_PASSWORD
  Which password to use with FORUMS_USERNAME
  
- DISCORD_TOKEN
  Standard bot token used for Discord bots.
  
- BOT_COMMAND_PREFIX
  The prefix used to separate commands from messages.
  
- DISCORD_BOT_LIST_API
  API key used for the discord bot list if you are using that.
  
- SQL_URL
  THe URL for the SQL database used for automatic news updates. The syntax is: mysql://<username>:<password>@<host>:<port>/<db_name>
  
## Stopping the bot
You just need to write **stop** in the console window of the bot.

# Additional info
- The bot uses Javacord as the API. 
