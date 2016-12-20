package the.java.slack4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import static the.java.slack4j.SlackApi.to;

/**
 * Created by taner on 30.11.2016.
 * <p>
 * https://github.com/tobias-/slack-appender
 */

@Plugin(name = "Slack", category = "Core", elementType = "appender", printObject = true)
public class SlackAppender extends AbstractAppender
{
    private static final Logger LOGGER = LogManager.getLogger(SlackAppender.class);

    private final String channel;
    private final String username;
    private final URL webhookUrl;
    private final FieldProvider fieldProvider;

    protected SlackAppender(String name, String channel, String username, URL webhookUrl, FieldProvider fieldProvider,
                                Layout<? extends Serializable> layout, Filter filter)
    {
        super(name, filter, layout, true);

        this.channel = channel;
        this.username = username;
        this.webhookUrl = webhookUrl;
        this.fieldProvider = fieldProvider;
    }

    @PluginFactory
    public static SlackAppender createAppender(@PluginAttribute("name") String name,
                                                   @PluginAttribute("channel") String channel,
                                                   @PluginAttribute(value = "username", defaultString = "Log4j") String username,
                                                   @PluginAttribute("webhookUrl") URL webhookUrl,
                                                   @PluginAttribute(value = "fieldProviderClass", defaultString = "the.java.slack4j.DefaultFieldProvider") String fieldProviderClassName,
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
            FieldProvider fieldProvider = newInstanceOfFieldProvider(fieldProviderClassName);
            slackAppender = new SlackAppender(name, channel, username, webhookUrl, fieldProvider, layout, filter);
        }
        catch (Slack4jConfigurationException e)
        {
            LOGGER.error(e.getMessage(), e);
        }

        return slackAppender;
    }

    private static FieldProvider newInstanceOfFieldProvider(String fieldProviderClassName)
    throws Slack4jConfigurationException
    {
        FieldProvider fieldProvider = null;
        try
        {
            fieldProvider = (FieldProvider) SlackAppender.class.getClassLoader().loadClass(fieldProviderClassName).newInstance();
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException e)
        {
            throw new Slack4jConfigurationException("FieldProvider instance has not been create from specified class{"+fieldProviderClassName+"}. SlackAppender will not work anymore.", e);
        }
        return fieldProvider;
    }

    public void append(LogEvent logEvent)
    {
        SlackMessage message = SlackMessage.wrap(logEvent, fieldProvider.getFields());

        try(SlackApi api = SlackApi.to(webhookUrl))
        {
            api.connect().send(message).close();
        }
        catch (SlackConnectionException e)
        {
            LOGGER.error("The message specified below has not been sent to Slack.\n"+message.toJson(), e);
        }
        catch (Exception e)
        {
            LOGGER.error("Unknown exception has been thrown.", e);
        }
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

    public FieldProvider getFieldProvider()
    {
        return fieldProvider;
    }
}
