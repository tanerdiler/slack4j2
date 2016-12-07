package the.java.slack4j;

import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;

/**
 * Created by taner on 05.12.2016.
 */
public class Message
{
    private LogEvent logEvent;

    private Message(LogEvent logEvent)
    {
        this.logEvent = logEvent;
    }

    public static Message wrap(LogEvent logEvent)
    {
        return new Message(logEvent);
    }

    public String toJson()
    {
        return "{\"text\":\"%s\"}";
    }
}
