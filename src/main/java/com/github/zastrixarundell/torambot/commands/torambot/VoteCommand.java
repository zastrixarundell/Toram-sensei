package com.github.zastrixarundell.torambot.commands.torambot;

import com.github.zastrixarundell.torambot.Parser;
import com.github.zastrixarundell.torambot.Values;
import org.discordbots.api.client.DiscordBotListAPI;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.concurrent.atomic.AtomicBoolean;

public class VoteCommand implements MessageCreateListener
{

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent)
    {

        if (!messageCreateEvent.getMessageContent().toLowerCase().startsWith(Values.getPrefix() + "vote"))
            return;

        if (!messageCreateEvent.getMessageAuthor().isRegularUser())
            return;

        DiscordBotListAPI api = Values.getApi();

        sendToVoteEmbed(messageCreateEvent, api);

    }

    private void sendToVoteEmbed(MessageCreateEvent messageCreateEvent, DiscordBotListAPI api)
    {
        String name = messageCreateEvent.getApi().getYourself().getName();

        AtomicBoolean multiplier = new AtomicBoolean(false);

        api.getVotingMultiplier().whenComplete(((votingMultiplier, throwable) -> multiplier.set(votingMultiplier.isWeekend())));

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Vote for: " + name)
                .setDescription("Every vote helps this bot to show people how useful it is! To vote, just go to [this link](https://discordbots.org/bot/600302983305101323). " +
                        "And remember, you can vote every 12 hours (during the weekends your vote is worth 2x more)!")
                .addField("Vote multiplier:", multiplier.get() ? "Active (votes are worth 2x more)." : "Not active.");

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

}
