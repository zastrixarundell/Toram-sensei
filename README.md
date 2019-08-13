<p align="center"><img src="https://toramonline.com/index.php?media/toram-online-logo.50/full&d=1463410056" /></p>

# ToramBot
A Java Discord bot for your Discord Toram guild.
<p align="center"><img src="https://raw.githubusercontent.com/ZastrixArundell/ToramBot/master/images/profile.png" /></p>

# Commands

## Item search commands
This bot has commands to search for items and most categories of items. You can
search for any item via the general command or if you want specific categories you can
search for swords, bow(gun)s, arrows, daggers, halberds, katanas, knuckles, magic devices,
staffs, additional gear, armors, shields, special gear, gems, crystals and upgrade cystals!

## Monster search commands
These commands are used for specific monster search, you can search for general monsters,
bosses and minibosses.

## Player commands
You can search for what you need to farm in order to level up, calculate skill points which
you will have ona targeted level, and get your cooking level from cooking points.

## Crafting commands
You can search what you need to create in order to level up your synthesis and get mats/recipe
for searched items.

## Game info commands
You can get the latest events, maintenance, news, dyes or info off from the web.

## Bot info commands
You can get the link to invite the bot to your server, donate to the bot or get the link
to the support server.

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
## Making it easier
### Linux
If you're a Linux user this doesn't need any explanation. Just make a 
shell script and flag it as runnable, ez.

### Windows
For windows users (AKA most likely not a dev) fire up yer' notepad app 
add write the previous line of code for starting up the bot. Save that file
with the .bat extension and you can just double click it to run it. 
>Double click the .bat, that is.

But this works for single versions, ie. you need to update the .bat
file every time. If you wish to make it run the highest version in the 
folder, just paste this badboy in:

    @echo off
    setlocal enabledelayedexpansion
    set max=0
    for %%x in (*-shaded.jar) do (
      set "FN=%%~nx"
      set "FN=!FN:version-=!"
      if !FN! GTR !max! set max=!FN!
    )
    
    echo Starting: %max%.jar
    
    java -jar %max%.jar [token] {custom prefix}

## Stopping the bot
You just need to write **stop** in the console window of the bot.

# Additional info
- The bot uses Javacord as the API. 
