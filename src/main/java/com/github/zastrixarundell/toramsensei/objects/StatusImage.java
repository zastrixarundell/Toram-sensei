package com.github.zastrixarundell.toramsensei.objects;

import com.github.zastrixarundell.toramsensei.Values;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class StatusImage
{

    //Will be used in the future

    /*
        Heroku uses the $PORT environment variable and it listens to the port *80*.
        This means that REST api applications can only work on port 80.
     */

    private BufferedImage bufferedImage;

    public StatusImage() throws Exception
    {
        bufferedImage = new BufferedImage(700, 400, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();

        graphics.setColor(new Color(39,40,45));
        graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        graphics.setColor(new Color(47, 48, 54));
        graphics.fillRect(39, 39, 322, 322);

        BufferedImage image = ImageIO.read(new URL(Values.profileImageURL));

        graphics.drawImage(image, 50, 50, null);

        graphics.setColor(Color.white);

        Font font = new Font("DejaVu Sans", Font.BOLD, 24);
        graphics.setFont(font);

        graphics.drawString("Toram-sensei", 452, 62);

        graphics.drawString("Guilds: " + Values.getGuildCount(), 393, 180+20);
        graphics.drawString("Users: " + Values.getUserCount(), 393, 240+20);

        graphics.dispose();
    }

    public void drawFrame()
    {
        JFrame jFrame = new JFrame("Image");
        JLabel label = new JLabel(new ImageIcon(bufferedImage));
        jFrame.setSize(bufferedImage.getWidth() + 100, bufferedImage.getHeight() + 100);
        jFrame.add(label);
        jFrame.setVisible(true);
    }

    public BufferedImage getBufferedImage()
    {
        return bufferedImage;
    }
}
