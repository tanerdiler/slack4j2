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

    public SlackApi send(SlackMessage message)
    {
        OutputStream out = null;
        try
        {
            out = connection.getOutputStream();
            out.write(message.toJson().getBytes("utf-8"));
            out.flush();
            int responseCode = connection.getResponseCode();
            System.out.println("response code " + responseCode);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (out != null)
            {
                try
                {
                    out.close();
                } catch (IOException e)
                {
                    // DO NOTHING
                }
            }
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

    public static void main(String[] args)
    {
        SlackMessage message = new SlackMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "Donec convallis nunc sed sem blandit tincidunt. Aliquam venenatis euismod mauris, a mattis turpis ultricies a. Sed at nunc nisl. Duis ex ipsum, maximus nec metus a, semper mattis neque. Maecenas vel velit interdum, mollis nisl sed, dictum sapien. Aenean quis arcu a neque ultricies porttitor in sed diam. Sed sed turpis neque. Suspendisse mollis tortor magna, sit amet mollis eros aliquam at. Sed posuere rutrum lectus eget aliquam. Mauris consequat feugiat tellus, vel pellentesque nunc aliquet et. Morbi vel est lacinia, luctus sapien at, luctus felis. Maecenas at orci quis massa sodales varius eu iaculis quam. Nullam scelerisque id justo id bibendum. Donec dignissim dolor eu odio fringilla scelerisque. Fusce vestibulum tortor vitae turpis accumsan sollicitudin. Interdum et malesuada fames ac ante ipsum primis in faucibus.", "warning", SlackField.by("Facility", "Çimsa Eskişehir"));
        SlackApi connector = null;
        try
        {
            connector = SlackApi.to(new URL("https://hooks.slack.com/services/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"))
                              .connect()
                              .send(message);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(connector != null)
            {
                try
                {
                    connector.close();
                } catch (Exception e)
                {
                    // DO NOTHING
                }
            }
        }
    }
}
