package com.github.zastrixarundell.torambot.commands.torambot;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.commands.DiscordCommand;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

public class VoteCommand extends DiscordCommand
{

    public VoteCommand() { super("vote"); }

    @Override
    protected void runCommand(MessageCreateEvent event)
    {
        sendToVoteEmbed(event);
    }

    private void sendToVoteEmbed(MessageCreateEvent messageCreateEvent)
    {
        String name = messageCreateEvent.getApi().getYourself().getName();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Vote for: " + name)
                .setDescription("Every vote helps this bot to show people how useful it is! To vote, just go to [this link](https://top.gg/bot/600302983305101323/vote). " +
                        "And remember, you can vote every 12 hours (during the weekends your vote is worth 2x more)!");

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

}
