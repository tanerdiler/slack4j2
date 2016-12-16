package the.java.slack4j;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by taner on 13.12.2016.
 */
public class LogUtil
{
    public static String stackTraceToString(Throwable e)
    {
        String text = "";
        if(e != null)
        {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            text = errors.toString();
        }
        return text;
    }
}
