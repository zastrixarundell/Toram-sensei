# Changelog

### 6.2.2
- Added shutdown hook.

### 6.2.1
- Showing multiple results with **_level_** command.

### 6.2.0
- Is now automatically deployed on each master push.

### 6.1.1
- Fixed typos.
- Showing time on all gameinfo commands from the toram website.

### 6.1.0
- Using system env. variables
- Fixed typos.

### 6.0.3
- Fixed some text format issues for automatic news.

### 6.0.2
- Removed " Back" from the automatic news.
- Fixed the issue where it would spam the servers with the notifications.

### 6.0.0
- Made abstract classes for the commands and drastically improvised the code.
- Using Javacords' thread pool rather than creating new threads.
- Auto-generated item commands.
- Added automatic news updates! 
- Connecting to a MySQL database for cached data.
- Fixed the mats command.

### 5.5.3
- Fixed Ad URL's... again...
- Not showing vote multiplier (doesn't seem to work any way).


### 5.5.2
- Minimized Jar.
- Set donations to paypal.
- TimerTasks are under objects.tasks
- Removed command count.
- Added cloudinary with AES encryption.

### 5.5.1
- Fixed level boost on **_level_** command.
- Removed level range on **_level_**.

### 5.5.0
- Changed **_dye_** to **_monthly_**.
- Added a new **_dye_** command.

### 5.4.0
- Added **_vote_** command.
- Added discord bot list api.
- Added support for multiple dye images.

### 5.3.6
- Added the StatusImage object, will need to add a
RESTful api in order to use it, most likely will use
spring boot for this.

### 5.3.5
- Renamed bot and changes some picture URLs.

### 5.3.4
- Fixed errors on showed time with **_dye_** command.

### 5.3.3
- Added checkers to not count servers which are bot verifiers.

### 5.3.2
- Added more messages on activity.
- Added last check time on **_dye_** command.

### 5.3.1
- Fixed to send messages when no upgrades are found.
- Saved the upgrade xtal list page in a buffered variable for faster access.

### 5.3.0
- Added the **_enhance|upgrade_** search command.

### 5.2.0
- Changed the dye update rate to 12 hours.
- Changed the donation link to a Patreon one.
- Added a support command.
- Made parser method blank.

### 5.1.0
- Added the **_mats_** command.

### 5.0.0
- Renamed packages.
- Added a lot of commands for specific search queries.

### 4.0.0
- Proper class re-namings.
- Changed the **_dye_** client timeout from 20000 to 30000.
- Fixed logical error for dye updating.
- Fixed typo when starting up.

### 3.5.0
- Added the **_dye_** command.

### 3.4.1
- Fixed typos.
- Changed monster GIF from image to thumbnail.

### 3.4.0
- Added **_donate_**.
- Added **_invite_**.
- Added **_cooking_**.

### 3.3.2
- Fixed missing item thumbnails.

### 3.3.1
- Re-rolled to using pre-uploaded images.

### 3.3.0
- Fixed major bug with InputStreams.

### 3.2.3
- Removed accidental google guava from pom.xml.

### 3.2.2
- Made images local.
- Updated the name parser.

### 3.2.1
- Added weaknesses on monsters.
- Added parser for names (for excess spaces).

### 3.2.0
- Added **_proficiency_** command.
- Added version on the help command.
- Fixed monster command.
- Fixed some errors on item search.
- **_Maintenance_** can be used as **_maint_** as well
- Added Argument Parser.

### 3.1.1
- Removed the logical error on items.

### 3.1.0
- All of the commands are multi-threaded.
- Made custom classes for custom methods.
- Using lambda expressions and uses less resources on methods.