package com.github.zastrixarundell.toramsensei.commands.gameinfo;

import com.github.zastrixarundell.toramsensei.Parser;
import com.github.zastrixarundell.toramsensei.commands.DiscordCommand;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class DyeCommand extends DiscordCommand
{

    public DyeCommand()
    {
        super("dye", "dyes");
    }

    @Override
    protected void runCommand(MessageCreateEvent event)
    {
        List<String> arguments = Parser.argumentsParser(event);

        if(arguments.isEmpty())
        {
            sendDyeList(event);
            return;
        }

        try
        {
            sendCustomColorEmbed(event, Integer.parseInt(arguments.get(0)));
        }
        catch (Exception exception)
        {
            errorOnFindingColor(event);
        }

    }

    private void sendCustomColorEmbed(MessageCreateEvent messageCreateEvent, int number)
    {
        Color color = colors[number - 1];

        BufferedImage bufferedImage = new BufferedImage(500, 200, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();

        graphics.setColor(color);
        graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        graphics.dispose();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Dye: " + number)
                .setDescription("Details about the selected Dye.")
                .addInlineField("Red:", String.valueOf(color.getRed()))
                .addInlineField("Green:", String.valueOf(color.getGreen()))
                .addInlineField("Blue:", String.valueOf(color.getBlue()))
                .addInlineField("Hex code:", String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()))
                .setImage(bufferedImage);

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void sendDyeList(MessageCreateEvent messageCreateEvent)
    {

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Dye list for Toram")
                .setDescription("As you have not specified a dye, you will now be shown the list of all of the dyes which Toram has.");

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColorList(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private void errorOnFindingColor(MessageCreateEvent messageCreateEvent)
    {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Error while searching for dye")
                .setDescription("Uh oh, looks like the dye you were searching for doesn't exist, you will now be shown the list of all of the dyes which Toram has.");

        Parser.parseFooter(embed, messageCreateEvent);
        Parser.parseThumbnail(embed, messageCreateEvent);
        Parser.parseColorList(embed, messageCreateEvent);
        Parser.parseColor(embed, messageCreateEvent);

        messageCreateEvent.getChannel().sendMessage(embed);
    }

    private final Color[] colors =
            {
                    new Color(255, 255, 255),     //1
                    new Color(191, 191, 191),     //2
                    new Color(128, 128, 128),     //3
                    new Color(63, 63, 63),        //4
                    new Color(0, 0, 0),           //5
                    new Color(248, 179, 174),     //6
                    new Color(252, 202, 177),     //7
                    new Color(255, 217, 172),     //8
                    new Color(244, 235, 166),     //9
                    new Color(252, 255, 178),     //10
                    new Color(217, 254, 174),     //11
                    new Color(176, 255, 171),     //12
                    new Color(176, 255, 210),     //13
                    new Color(174, 254, 245),     //14
                    new Color(174, 237, 246),     //15
                    new Color(177, 219, 244),     //16
                    new Color(171, 205, 243),     //17
                    new Color(180, 175, 239),     //18
                    new Color(210, 177, 246),     //19
                    new Color(246, 179, 246),     //20
                    new Color(240, 187, 215),     //21
                    new Color(247, 103, 95),      //22
                    new Color(248, 155, 88),      //23
                    new Color(243, 180, 100),     //24
                    new Color(243, 215, 89),      //25
                    new Color(252, 253, 89),      //26
                    new Color(173, 255, 90),      //27
                    new Color(96, 251, 97),       //28
                    new Color(98, 250, 177),      //29
                    new Color(95, 250, 236),      //30
                    new Color(97, 217, 242),      //31
                    new Color(96, 179, 245),      //32
                    new Color(93, 141, 239),      //33
                    new Color(92, 104, 238),      //34
                    new Color(171, 99, 245),      //35
                    new Color(249, 103, 240),     //36
                    new Color(246, 105, 174),     //37
                    new Color(243, 4, 3),         //38
                    new Color(245, 86, 5),        //39
                    new Color(245, 130, 1),       //40
                    new Color(250, 189, 21),      //41
                    new Color(250, 250, 1),       //42
                    new Color(125, 254, 6),       //43
                    new Color(0, 255, 0),         //44
                    new Color(5, 250, 121),       //45
                    new Color(0, 253, 242),       //46
                    new Color(3, 191, 226),       //47
                    new Color(12, 124, 210),      //48
                    new Color(1, 69, 240),        //49
                    new Color(2, 6, 238),         //50
                    new Color(120, 1, 243),       //51
                    new Color(245, 2, 254),       //52
                    new Color(238, 11, 129),      //53
                    new Color(148, 3, 0),         //54
                    new Color(145, 50, 2),        //55
                    new Color(147, 80, 1),        //56
                    new Color(146, 116, 4),       //57
                    new Color(146, 155, 4),       //58
                    new Color(69, 151, 5),        //59
                    new Color(2, 152, 1),         //60
                    new Color(8, 146, 69),        //61
                    new Color(0, 155, 141),       //62
                    new Color(2, 122, 138),       //63
                    new Color(4, 76, 148),        //64
                    new Color(3, 37, 135),        //65
                    new Color(1, 3, 126),         //66
                    new Color(71, 2, 147),        //67
                    new Color(146, 4, 124),       //68
                    new Color(145, 4, 73),        //69
                    new Color(73, 2, 6),          //70
                    new Color(76, 24, 2),         //71
                    new Color(69, 35, 0),         //72
                    new Color(72, 54, 4),         //73
                    new Color(72, 79, 2),         //74
                    new Color(31, 71, 0),         //75
                    new Color(0, 79, 0),          //76
                    new Color(0, 75, 34),         //77
                    new Color(1, 74, 67),         //78
                    new Color(0, 60, 75),         //79
                    new Color(0, 40, 73),         //80
                    new Color(0,22, 63),          //81
                    new Color(0, 0, 66),          //82
                    new Color(23, 0, 47),         //83
                    new Color(76, 0, 58),         //84
                    new Color(64, 5, 35),         //85
            };

}
