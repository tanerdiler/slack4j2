import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.junit.Test;
import the.java.slack4j.SlackAppender;

import static org.junit.Assert.assertEquals;

/**
 * Created by taner on 01.12.2016.
 */
public class SlackAppenderInitializationTest
{
    private SlackAppender getSlackAppender()
    {
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();
        return (SlackAppender) config.getAppender("SlackAppender");
    }

    @Test
    public void shouldSetChannel()
    {
        SlackAppender slackAppender = getSlackAppender();
        assertEquals("Logs", slackAppender.getChannel());
    }

    @Test
    public void shouldSetUsername()
    {
        SlackAppender slackAppender = getSlackAppender();
        assertEquals("Logger", slackAppender.getUsername());
    }

    @Test
    public void shouldSetWebhookUrl()
    {
        SlackAppender slackAppender = getSlackAppender();
        assertEquals("http://www.tanerdiler.com", slackAppender.getWebhookUrl().toString());
    }
}
