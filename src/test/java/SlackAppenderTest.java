import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.junit.Assert;
import org.junit.Test;
import the.java.slack4j.SlackAppender;

import static org.junit.Assert.*;

/**
 * Created by taner on 01.12.2016.
 */
public class SlackAppenderTest
{
    private static final Logger logger = LogManager.getLogger(SlackAppenderTest.class);

    @Test
    public void shouldSetChannel()
    {
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();
        SlackAppender slackAppender = (SlackAppender) config.getAppender("SlackAppender");
        assertEquals("Logs", slackAppender.getChannel());
    }

    @Test
    public void shouldSetUsername()
    {
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();
        SlackAppender slackAppender = (SlackAppender) config.getAppender("SlackAppender");
        assertEquals("Logger", slackAppender.getUsername());
    }

    @Test
    public void shouldSetWebhookUrl()
    {
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();
        SlackAppender slackAppender = (SlackAppender) config.getAppender("SlackAppender");
        assertEquals("http://www.tanerdiler.com", slackAppender.getWebhookUrl().toString());
    }
}
