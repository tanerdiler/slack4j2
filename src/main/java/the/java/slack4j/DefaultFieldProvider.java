package the.java.slack4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taner on 20.12.2016.
 */
public class DefaultFieldProvider implements  FieldProvider
{
    @Override
    public List<SlackField> getFields()
    {
        return new ArrayList<>();
    }
}
