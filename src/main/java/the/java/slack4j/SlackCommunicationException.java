package the.java.slack4j;

/**
 * Created by taner on 13.12.2016.
 */
public class SlackCommunicationException extends Exception
{
    public SlackCommunicationException(String message, Throwable e)
    {
        super(message, e);
    }
}
