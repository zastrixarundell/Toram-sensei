package com.github.zastrixarundell.torambot.entities;

import com.cloudinary.Cloudinary;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.github.zastrixarundell.torambot.Values;
import com.github.zastrixarundell.torambot.utils.AESHelper;
import org.joda.time.DateTime;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class ToramForumsUser implements Closeable
{

    private HtmlPage page;
    private WebClient webClient;
    private Cloudinary cloudinary;
    private AESHelper aesHelper;

    public ToramForumsUser(String token) throws Exception
    {
        aesHelper = new AESHelper(token);

        String username, password;

        username = aesHelper.decryptData("81CjhzuvgfRKambn82RJ7/kjZQwia4ihURY1evbP20I=");
        password = aesHelper.decryptData("rrXWhM2/o14taNAPd0XGfg==");

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
                ArrayList<String> images = new ArrayList<>();

                setupCloudinary();

                cloudinary.api().deleteAllResources(null);

                for(int i = 1; i < element.getElementsByTagName("img").size(); i++)
                {
                    HtmlImage htmlImage = (HtmlImage) element.getElementsByTagName("img").get(i);
                    ImageReader reader = htmlImage.getImageReader();
                    BufferedImage image = reader.read(0);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    ImageIO.write(image, "png", stream);
                    String base = Base64.getEncoder().encodeToString(stream.toByteArray());
                    Map response = cloudinary.uploader().upload("data:image/png;base64," + base, null);
                    images.add((String) response.get("url"));
                }

                Values.setDyeImages(images);
                break;
            }

        Values.setLastDyeUpdate(new DateTime());
    }

    private void setupCloudinary() throws Exception
    {
        Map<String, String> map = new HashMap<>();

        String secret = aesHelper.decryptData("zpxkrVMylScivPMiapdGpJ84eg/nhcqccGws1vd7dYY=");

        map.put("cloud_name", "zastrix");
        map.put("api_key", "922812555643244");
        map.put("api_secret", secret);

        cloudinary = new Cloudinary(map);
    }

    @Override
    public void close()
    {
        webClient.close();
    }
}
