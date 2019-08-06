package com.github.zastrixarundell.torambot.entities;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.github.zastrixarundell.torambot.Values;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.security.Key;
import java.util.Base64;
import java.util.logging.Level;

public class ToramForumsUser implements Closeable
{

    private HtmlPage page;
    private WebClient webClient;

    public ToramForumsUser(String token) throws Exception
    {
        String key = token.substring(0, 32);

        AESHelper aesHelper = new AESHelper(key);

        String username, password;

        /*
            Using AES here so that multiple bots don't use this command as it will basically be spamming the site.

            AES just deciphers the credentials with my known bot tokens.
         */

        //Try for main first
        try
        {
            username = aesHelper.decryptData("TJHWV+WLyl296krsUDgyNPnumZs4K3h5C3B9AXftHSE=");
            password = aesHelper.decryptData("vv5/mS+exOxYwWaXBfsFoQ==");
        }
        //It is most likely the beta bot
        catch(Exception e)
        {
            username = aesHelper.decryptData("qCzlHTvYtB51mrv2eBpZYqmpE27WNzh+JvUcgs1GiMg=");
            password = aesHelper.decryptData("yglXjxiQk4x71B3S8ogorQ==");
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
                HtmlImage htmlImage = (HtmlImage) element.getElementsByTagName("img")
                        .get(element.getElementsByTagName("img").getLength() - 1);
                ImageReader reader = htmlImage.getImageReader();
                BufferedImage image = reader.read(0);
                Values.setDyeImage(image);
                break;
            }
    }

    @Override
    public void close() { webClient.close(); }

    private static class AESHelper
    {

        private static final String algorithm = "AES";

        private Key key;

        AESHelper(String aesKey)
        {
            key = new SecretKeySpec(aesKey.getBytes(), algorithm);
        }

        /*
        public String encryptData(String data) throws Exception
        {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        }
        */

        String decryptData(String data) throws Exception
        {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedBaseValues = Base64.getDecoder().decode(data);
            byte[] deciphered = cipher.doFinal(decodedBaseValues);
            return new String(deciphered);
        }

    }
}
