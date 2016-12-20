import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.junit.Test;
import the.java.slack4j.FieldProvider;
import the.java.slack4j.SlackAppender;

import static org.junit.Assert.assertEquals;

/**
 * Created by taner on 01.12.2016.
 */
public class SlackAppenderInitializationTest
{
    private static final String APPENDER = "SlackAppender";
    private static final String APPENDER_WITH_DEFAULT_FIELDPROVIDER = "SlackAppenderWithDefaultFieldProvider";
    private SlackAppender getSlackAppender(String appenderName)
    {
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();
        return (SlackAppender) config.getAppender(appenderName);
    }

    @Test
    public void shouldSetChannel()
    {
        SlackAppender slackAppender = getSlackAppender(APPENDER);
        assertEquals("Logs", slackAppender.getChannel());
    }

    @Test
    public void shouldSetUsername()
    {
        SlackAppender slackAppender = getSlackAppender(APPENDER);
        assertEquals("Logger", slackAppender.getUsername());
    }

    @Test
    public void shouldSetWebhookUrl()
    {
        SlackAppender slackAppender = getSlackAppender(APPENDER);
        assertEquals("http://www.tanerdiler.com", slackAppender.getWebhookUrl().toString());
    }

    @Test
    public void shouldSetDefaultFieldProvider()
    {
        SlackAppender slackAppender = getSlackAppender(APPENDER_WITH_DEFAULT_FIELDPROVIDER);
        FieldProvider fieldProvider = slackAppender.getFieldProvider();
        assertEquals(fieldProvider.getClass().getCanonicalName(), "the.java.slack4j.DefaultFieldProvider");
    }
}
