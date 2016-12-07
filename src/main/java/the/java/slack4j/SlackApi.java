package the.java.slack4j;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

/**
 * Created by taner on 05.12.2016.
 */
public class SlackApi
{
    private URL url;
    private HttpURLConnection connection;
    private OutputStream out;

    public SlackApi connect(String url) throws IOException
    {
        this.url = new URL(url);

        connection = (HttpURLConnection) this.url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        return this;
    }

    public final SlackApi post(String message) throws IOException
    {
        byte[] messageBytes = message.getBytes("utf-8");
        connection.setRequestProperty("Content-Length", String.valueOf(messageBytes.length));
        out = connection.getOutputStream();
        out.write(messageBytes);
        out.flush();
        return this;
    }

    public final void close()
    {
        try
        {
            connection.disconnect();
            out.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void doSomething()
    {
        String firstname = null;
        firstname.split(";");
    }

    public static String toString(Exception e){
        // this works much better
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }

    public static void main(String[] args) throws IOException
    {
        URL url = new URL("SLACK HOOKS URL");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/xml");
        connection.setDoOutput(true);
//        byte[] mesajBytes = ("{" +
//                                    "\"username\":\"Logger\"," +
//                                    "\"icon_url\": \"https://slack.com/img/icons/app-57.png\"," +
//                                     "\"text\": \"First attempt to send a message to Slack from Java! <https://alert-system.com/alerts/1234|Click here> for details!\"}").getBytes("utf-8");


//        byte[] mesajBytes =("{\n" +
//                "    \"attachments\": [\n" +
//                "        {\n" +
//                "            \"fallback\": \"Required plain-text summary of the attachment.\",\n" +
//                "            \"color\": \"#36a64f\",\n" +
//                "            \"pretext\": \"Optional text that appears above the attachment block\",\n" +
//                "            \"title\": \"Slack API Documentation\",\n" +
//                "            \"title_link\": \"https://api.slack.com/\",\n" +
//                "            \"text\": \"Optional text that appears within the attachment\",\n" +
//                "            \"fields\": [\n" +
//                "                {\n" +
//                "                    \"title\": \"Facility\",\n" +
//                "                    \"value\": \"Çimsa Eskişehir\",\n" +
//                "                    \"short\": true\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"title\": \"HashCode\",\n" +
//                "                    \"value\": \"1234RQWEF14QEFQ23RQFEQ\",\n" +
//                "                    \"short\": true\n" +
//                "                }\n" +
//                "            ],\n" +
//                "            \"image_url\": \"http://my-website.com/path/to/image.jpg\",\n" +
//                "            \"thumb_url\": \"http://example.com/path/to/thumb.png\",\n" +
//                "            \"footer\": \"Slack API\",\n" +
//                "            \"footer_icon\": \"https://platform.slack-edge.com/img/default_application_icon.png\",\n" +
//                "            \"ts\": 123456789\n" +
//                "        }\n" +
//                "    ]\n" +
//        "}").getBytes("utf-8");;

        try {
            doSomething();

        }
        catch (Exception e)
        {
                    byte[] mesajBytes =("{\n" +
                            "    \"attachments\": [\n" +
                            "        {\n" +
                            "            \"fallback\": \"Required plain-text summary of the attachment.\",\n" +
                            "            \"color\": \"#36a64f\",\n" +
                            "            \"pretext\": \"Optional text that appears above the attachment block\",\n" +
                            "            \"title\": \"Slack API Documentation\",\n" +
                            "            \"title_link\": \"https://api.slack.com/\",\n" +
                            "            \"text\": \""+ toString(e) +"\",\n" +
                            "            \"fields\": [\n" +
                            "                {\n" +
                            "                    \"title\": \"Facility\",\n" +
                            "                    \"value\": \"[FACILITY NAME]\",\n" +
                            "                    \"short\": true\n" +
                            "                },\n" +
                            "                {\n" +
                            "                    \"title\": \"HashCode\",\n" +
                            "                    \"value\": \"1234RQWEF14QEFQ23RQFEQ\",\n" +
                            "                    \"short\": true\n" +
                            "                }\n" +
                            "            ],\n" +
                            "            \"image_url\": \"http://my-website.com/path/to/image.jpg\",\n" +
                            "            \"thumb_url\": \"http://example.com/path/to/thumb.png\",\n" +
                            "            \"footer\": \"Slack API\",\n" +
                            "            \"footer_icon\": \"https://platform.slack-edge.com/img/default_application_icon.png\",\n" +
                            "            \"ts\": 123456789\n" +
                            "        }\n" +
                            "    ]\n" +
                    "}").getBytes("utf-8");;

            OutputStream out = connection.getOutputStream();
            out.write(mesajBytes);
            out.flush();

            int responseCode = connection.getResponseCode();
            System.out.println("response code " + responseCode);
            try {
                connection.disconnect();
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


    }
}