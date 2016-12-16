package the.java.slack4j;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;

/**
 * Created by taner on 05.12.2016.
 */
public class SlackMessage
{
    private SlackField[] fields;
    private String color;
    private String title;
    private String text;

    public SlackMessage(String title, String text, String color, SlackField... fields)
    {
        this.title = title;
        this.text = text;
        this.color = color;
        this.fields = fields;
    }

    public static SlackMessage wrap(LogEvent logEvent, SlackField... fields)
    {
        String title = logEvent.getMessage().getFormattedMessage();
        String text = LogUtil.stackTraceToString(logEvent.getThrown());
        String color = "good";
        if (logEvent.getLevel().intLevel() == Level.FATAL.intLevel() || logEvent.getLevel().intLevel() == Level.ERROR
                .intLevel())
        {
            color = "danger";
        }
        else if (logEvent.getLevel().intLevel() == Level.WARN.intLevel())
        {
            color = "warning";
        }
        return new SlackMessage(title, text, color, fields);
    }

    public SlackField[] getFields()
    {
        return fields;
    }

    public String getColor()
    {
        return this.color;
    }

    public String getTitle()
    {
        return title;
    }

    public String getText()
    {
        return text;
    }

    public String toJson()
    {
        String json = "{\n" +
                                    "    \"attachments\": [\n" +
                                    "        {\n" +
                                    "            \"color\": \""+ getColor() +"\",\n" +
                                    "            \"title\": \""+ getTitle() +"\",\n";

        if(fields!=null && getFields().length>0)
        {
            json += "            \"fields\": [\n";
            String separator = "";
            for(SlackField field : fields)
            {
                json += separator +
                        "                {\n" +
                        "                    \"title\": \""+ field.getName() +"\",\n" +
                        "                    \"value\": \""+ field.getValue() +"\",\n" +
                        "                    \"short\": true\n" +
                        "                }\n";

                separator = ",";
            }
            json +=       "            ],\n";
        }

        json +=      "            \"text\": \""+ getText() +"\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
        return json;
    }
}
