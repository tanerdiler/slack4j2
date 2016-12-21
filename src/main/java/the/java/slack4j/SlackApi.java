package the.java.slack4j;

import org.apache.logging.log4j.core.LogEvent;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by taner on 13.12.2016.
 */
public class SlackApi implements AutoCloseable
{
    private URL webhook;
    private HttpURLConnection connection;

    private SlackApi(URL webhook)
    {
        this.webhook = webhook;
    }

    public static final SlackApi to(URL webhook)
    {
        return new SlackApi(webhook);
    }

    public SlackApi connect() throws SlackConnectionException
    {
        URL url = null;
        try
        {
            connection = (HttpURLConnection) webhook.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/xml");
            connection.setDoOutput(true);
        }
        catch (MalformedURLException e)
        {
            throw new SlackConnectionException("WebHook {" + webhook + "} is malformed.");
        }
        catch (IOException e)
        {
            throw new SlackConnectionException(
                    "Connection couldn't be established to server that serves webhook url{" + webhook + "}");
        }
        return this;
    }

    public SlackApi send(SlackMessage message) throws SlackCommunicationException
    {
        int statusCode = 202;

        try (OutputStream out = connection.getOutputStream())
        {
            out.write(message.toJson().getBytes("utf-8"));
            out.flush();
            statusCode = connection.getResponseCode();
        }
        catch (IOException e)
        {
            throw new SlackCommunicationException(
                    "Communication problem with slack server. HTTP Status Code : " + statusCode, e);
        }
        return this;
    }

    @Override
    public void close() throws Exception
    {
        if (connection != null)
        {
            connection.disconnect();
        }
    }
}
