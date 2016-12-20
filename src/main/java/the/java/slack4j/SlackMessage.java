package the.java.slack4j;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;

import java.util.List;

/**
 * Created by taner on 05.12.2016.
 */
public class SlackMessage
{
    private final String checksum;
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
        this.checksum = Checksum.of(text);
    }

    public static SlackMessage wrap(LogEvent logEvent, List<SlackField> fields)
    {
        return wrap(logEvent, fields != null ? fields.toArray(new SlackField[]{}) : null);
    }

    public static SlackMessage wrap(LogEvent logEvent, SlackField... fields)
    {
        String title = logEvent.getMessage().getFormattedMessage();
        String text = LogUtil.stackTraceToString(logEvent.getThrown());

        String color = arrangeColor(logEvent);

        return new SlackMessage(title, text, color, fields);
    }

    public String getChecksum()
    {
        return checksum;
    }

    private static String arrangeColor(LogEvent logEvent)
    {
        String color = "good";
        if (logEvent.getLevel().intLevel() == Level.FATAL.intLevel()
                || logEvent.getLevel().intLevel() == Level.ERROR
                .intLevel())
        {
            color = "danger";
        }
        else if (logEvent.getLevel().intLevel() == Level.WARN.intLevel())
        {
            color = "warning";
        }
        return color;
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


        json += "            \"fields\": [\n";
        String separator = "";
        if(fields!=null && getFields().length>0)
        {
            for(SlackField field : fields)
            {
                json += separator + getFieldJson(field);
                separator = ",";
            }
        }
        json += separator + getFieldJson(SlackField.by("Checksum", getChecksum()));
        json +=       "            ],\n";

        json +=      "            \"text\": \""+ getText() +"\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
        return json;
    }

    private String getFieldJson(SlackField field)
    {
        return
                "                {\n" +
                "                    \"title\": \""+ field.getName() +"\",\n" +
                "                    \"value\": \""+ field.getValue() +"\",\n" +
                "                    \"short\": true\n" +
                "                }\n";
    }

    public static void main(String[] args){
        throw new IllegalStateException();
    }
}
