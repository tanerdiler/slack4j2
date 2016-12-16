package the.java.slack4j;

/**
 * Created by taner on 13.12.2016.
 */
public class SlackField
{
    private String name;
    private String value;

    private SlackField(String name, String value)
    {
        this.name = name;

        this.value = value;
    }

    public static SlackField by(String name, String value){
        return new SlackField(name, value);
    }

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return value;
    }
}
