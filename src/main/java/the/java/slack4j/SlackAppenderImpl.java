package the.java.slack4j;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;
import java.net.URL;

/**
 * Created by taner on 30.11.2016.
 */

@Plugin(name = "SlackAppender", category = "Core", elementType = "appender", printObject = true)
public class SlackAppenderImpl extends AbstractAppender
{

    protected SlackAppenderImpl(String name, Filter filter, Layout<? extends Serializable> layout)
    {
        super(name, filter, layout, true);
    }

    public static SlackAppenderImpl createAppender(@PluginAttribute("name") String name,
                                                   @PluginAttribute("channel") String channel,
                                                   @PluginAttribute(value = "username", defaultString = "Log4j") String username,
                                                   @PluginAttribute("webhookUrl") URL webhookUrl,
                                                   @PluginElement("Layout") Layout<? extends Serializable> layout,
                                                   @PluginElement("Filter") final Filter filter,
                                                   @PluginAttribute(value = "meltdownProtection", defaultBoolean = true) boolean meltdownProtection,
                                                   @PluginAttribute(value = "similarMessageSize", defaultInt = 50) int similarMessageSize,
                                                   @PluginAttribute(value = "timeBetweenSimilarLogsMs", defaultInt = 60000) int timeBetweenSimilarLogsMs,
                                                   @PluginAttribute(value = "packagesToMute", defaultString = "") String packagesToMute)
    {
        if (layout == null)
        {
            layout = PatternLayout.createDefaultLayout();
        }
        SlackAppenderImpl slackAppender = new SlackAppenderImpl(name, channel, username, webhookUrl, layout, filter,
                                                        meltdownProtection, similarMessageSize, timeBetweenSimilarLogsMs);
        slackAppender.setPackagesToMute(packagesToMute);
        return slackAppender;
    }
        	public void append(LogEvent logEvent)
    {

    }
}
