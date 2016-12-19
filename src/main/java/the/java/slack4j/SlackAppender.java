package the.java.slack4j;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;
import java.net.URL;
import java.util.LinkedList;

/**
 * Created by taner on 30.11.2016.
 * <p>
 * https://github.com/tobias-/slack-appender
 */

@Plugin(name = "Slack", category = "Core", elementType = "appender", printObject = true)
public class SlackAppender extends AbstractAppender
{

    private final String channel;
    private final String username;
    private final URL webhookUrl;

    protected SlackAppender(String name, String channel, String username, URL webhookUrl,
                                Layout<? extends Serializable> layout, Filter filter)
    {
        super(name, filter, layout, true);

        this.channel = channel;
        this.username = username;
        this.webhookUrl = webhookUrl;
    }

    @PluginFactory
    public static SlackAppender createAppender(@PluginAttribute("name") String name,
                                                   @PluginAttribute("channel") String channel,
                                                   @PluginAttribute(value = "username", defaultString = "Log4j") String username,
                                                   @PluginAttribute("webhookUrl") URL webhookUrl,
                                                   @PluginAttribute("fieldProviderClass", defaultString = "the.java.slack4j.DefaultFieldProvider") String fieldProviderClassName,
                                                   @PluginElement("Layout") Layout<? extends Serializable> layout,
                                                   @PluginElement("Filters") final Filter filter)
    {
        if (layout == null)
        {
            layout = PatternLayout.createDefaultLayout();
        }

        SlackAppender slackAppender = null;
        try
        {
            FieldProvider fieldProvider = (FieldProvider) SlackAppender.class.getClassLoader().loadClass(fieldProviderClassName).newInstance();
            slackAppender = new SlackAppender(name, channel, username, webhookUrl, layout, filter);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        }

        return slackAppender;
    }

    public void append(LogEvent logEvent)
    {
        SlackMessage message = SlackMessage.wrap(logEvent);
        SlackApi.to(webhookUrl).connect().send(message).close();
    }

    public String getChannel()
    {
        return channel;
    }

    public String getUsername()
    {
        return username;
    }

    public URL getWebhookUrl()
    {
        return webhookUrl;
    }
}
