package the.java.slack4j;

import java.rmi.server.ExportException;

/**
 * Created by taner on 19.12.2016.
 */
public class Slack4jConfigurationException extends Exception
{
    public Slack4jConfigurationException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
