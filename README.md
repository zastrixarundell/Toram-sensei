<p align="center"><img src="https://www.pngkit.com/png/full/383-3831540_-toram-online-logo.png" /></p>
<i>Not affiliated with Toram</i>

# Toram-sensei
A Java Discord bot for your Discord Toram guild.
<p align="center"><img src="https://raw.githubusercontent.com/ZastrixArundell/toram-sensei/master/images/ts-banner.png" /></p>

![Release version](https://img.shields.io/github/v/release/zastrixarundell/toram-sensei) [![Build Status](https://travis-ci.com/zastrixarundell/Toram-sensei.svg?branch=master)](https://travis-ci.com/zastrixarundell/Toram-sensei) [![Support server badge](https://img.shields.io/static/v1?label=Support%20server&message=Click%20me&logo=discord&logoColor=white&color=7289da)](https://discord.gg/MdASH22) [![Invite me](https://img.shields.io/static/v1?label=Invite%20me&message=Click%20me&logo=discord&logoColor=white&color=7289da)](https://discordapp.com/oauth2/authorize?client_id=600302983305101323&scope=bot&permissions=0)

# Setup

## Requirements
To run this bot you need to have JRE 8+ installed on your computer and to setup some environment variables which is not big deal!

 
## Starting up the bot
Download the jar file of the bot. Open up your text editor and write the following:
 
    java -jar [file].jar
    
After that you need to set up environment variables:

|Environment variable|usage|
|--|--|
|DISCORD_TOKEN|Standard bot token used for Discord bots.|
|BOT_COMMAND_PREFIX|The prefix used to separate commands from messages.|
|DISCORD_BOT_LIST_API|API key used for the discord bot list if you are using that.|
|SQL_URL|The URL for the SQL database used for automatic news updates. The syntax is: `mysql://<username>:<password>@<host>:<port>/<db_name>`|
|REDIS_URL|The URL for the Redis database. The syntax is `redis://<username>:<password>@<host>:<port>/<optional_database_name>`
  
## Stopping the bot
You just need to write **stop** in the console window of the bot.

# Additional info
- The bot uses Javacord as the API. 
