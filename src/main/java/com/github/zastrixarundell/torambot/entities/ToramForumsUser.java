package com.github.zastrixarundell.torambot.entities;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.github.zastrixarundell.torambot.Values;
import com.github.zastrixarundell.torambot.utils.AESHelper;
import net.coobird.thumbnailator.Thumbnails;
import org.joda.time.DateTime;

import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

public class ToramForumsUser implements Closeable
{

    private HtmlPage page;
    private WebClient webClient;

    public ToramForumsUser(String token) throws Exception
    {

        AESHelper aesHelper = new AESHelper(token);

        String username, password;

        /*
            Using AES here so that multiple bots don't use this command as it will basically be spamming the site.

            AES just deciphers the credentials with my known bot tokens.
         */

        //Try for main first
        try
        {
            username = aesHelper.decryptData("81CjhzuvgfRKambn82RJ7/kjZQwia4ihURY1evbP20I=");
            password = aesHelper.decryptData("rrXWhM2/o14taNAPd0XGfg==");
        }
        //It is most likely the beta bot
        catch(Exception e)
        {
            username = aesHelper.decryptData("vhiSaT+EaYprsfOqwV6IcciaE/bTVHcGry3SjaFatJ4=");
            password = aesHelper.decryptData("ZJcf6FCERMvGSEg/qEFIMA==");
        }

        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);

        webClient = new WebClient();
        webClient.getOptions().setTimeout(30000);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        page = webClient.getPage("https://toramonline.com/index.php?login");

        HtmlForm loginForm = (HtmlForm) page.getElementById("pageLogin");

        HtmlTextInput usernameArea = loginForm.getInputByName("login");
        usernameArea.type(username);
        HtmlPasswordInput passwordArea = loginForm.getInputByName("password");
        passwordArea.type(password);

        HtmlSubmitInput button = loginForm.getInputByValue("Log in");

        button.click();
    }

    public void setDye() throws Exception
    {
        page = webClient.getPage("https://toramonline.com/index.php?threads/weapon-shield-dyes-july-2019-white.23149/");

        HtmlElement ol = page.getHtmlElementById("messageList");
        HtmlElement li = ol.getElementsByTagName("li").get(0);

        for(HtmlElement element : li.getElementsByTagName("div"))
            if(element.getAttribute("class").equalsIgnoreCase("messagecontent"))
            {
                ArrayList<BufferedImage> images = new ArrayList<>();

                for(int i = 1; i < element.getElementsByTagName("img").size(); i++)
                {
                    HtmlImage htmlImage = (HtmlImage) element.getElementsByTagName("img").get(i);
                    ImageReader reader = htmlImage.getImageReader();
                    BufferedImage image = reader.read(0);
                    images.add(resize(image, image.getWidth() / 2, image.getHeight() / 2));
                }

                Values.setDyeImages(images.toArray(new BufferedImage[0]));
                break;
            }

        Values.setLastDyeUpdate(new DateTime());
    }

    static BufferedImage resize(BufferedImage img, int newW, int newH) throws IOException
    {
        return Thumbnails.of(img).size(newW, newH).asBufferedImage();
    }

    @Override
    public void close() { webClient.close(); }
}
