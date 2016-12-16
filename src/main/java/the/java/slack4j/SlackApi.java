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

// "https://hooks.slack.com/services/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"
    public static void main(String[] args) throws IOException
    {
        URL url = new URL("https://hooks.slack.com/services/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");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/xml");
        connection.setDoOutput(true);
        try {
            try{

                doSomething();
            }
            catch (Exception ex)
            {
                throw new IllegalStateException("Exception on doing something",ex);
            }
        }
        catch (Exception e)
        {
                    byte[] mesajBytes =("{\n" +
                            "    \"attachments\": [\n" +
                            "        {\n" +
                            "            \"fallback\": \"Required plain-text summary of the attachment.\",\n" +
                            "            \"color\": \"#DF0101\",\n" +
                            "            \"title\": \""+e.getMessage()+"\",\n" +
                            "            \"fields\": [\n" +
                            "                {\n" +
                            "                    \"title\": \"Facility\",\n" +
                            "                    \"value\": \"Çimsa Eskişehir\",\n" +
                            "                    \"short\": true\n" +
                            "                },\n" +
                            "                {\n" +
                            "                    \"title\": \"HashCode\",\n" +
                            "                    \"value\": \"1234RQWEF14QEFQ23RQFEQ\",\n" +
                            "                    \"short\": true\n" +
                            "                }\n" +
                            "            ],\n" +
                           // "            \"text\": \""+ toString(e) +"\"\n" +
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
