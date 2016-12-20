import the.java.slack4j.FieldProvider;
import the.java.slack4j.SlackField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taner on 12/18/2016.
 */
public class TestFieldProvider implements FieldProvider
{
    @Override
    public List<SlackField> getFields()
    {
        return new ArrayList<>();
    }
}
