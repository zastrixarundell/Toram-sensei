![](https://toramonline.com/index.php?media/toram-online-logo.50/full&d=1463410056)

# ToramBot
A Java Discord bot for your Discord Toram guild.

# Commands
>Note that the default prefix is ">"
   
- **\>monster [monster]**
  * This command is used for getting info about a monster!
- **\>item [name]**
  * You can use this command to search for an item!
- **\>level [your level] (level range) (EXP boost)**
  * Get what you need to farm to level up fast. Only [your level] needs
    to be present here, if the arguments in normal brackets aren't
    specified the commands uses 5 for the level range value and 0 for
    the EXP boost value.
- **\>points [current skill points] [your level] [target level]**
  * Calculate skill points. All of the arguments need to be present
    or else it will not work!
- **\>proficiency|prof (proficiency level)**
  * Depending on your proficiency level this will show you what to
    synthesize in order to gain most proficiency.
- **\>events**
  * This command is used to show the latest event posted on the official
    Toram news site!
- **\>latest**
  * This command is used to show the overall latest news posted on the
    official Toram news site!
- **\>maintenance|maint**
  * This command is used to show the latest maintenance data.
- **\>news**
  * This command is used to show the latest big news on the site.
    Big events, new chapter in the story line, etc.

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
    
    java -jar [file].jar [token] {custom prefix}

## Stopping the bot
You just need to write **stop** in the console window of the bot.

# Additional info
- The bot uses JavaCord as the API. 