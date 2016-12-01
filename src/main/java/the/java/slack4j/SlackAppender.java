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
                                                   @PluginElement("Layout") Layout<? extends Serializable> layout,
                                                   @PluginElement("Filters") final Filter filter)
    {
        if (layout == null)
        {
            layout = PatternLayout.createDefaultLayout();
        }

        SlackAppender slackAppender = new SlackAppender(name, channel, username, webhookUrl, layout, filter);

        return slackAppender;
    }

    public void append(LogEvent logEvent)
    {
        System.out.println(logEvent.getMessage());
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
