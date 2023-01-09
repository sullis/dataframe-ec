package io.github.vmzakharov.ecdataframe.util;

import org.junit.Assert;
import org.junit.Test;

import static io.github.vmzakharov.ecdataframe.util.FormatWithPlaceholders.*;

public class FormatWithPlaceholderTest
{
    @Test
    public void noPlaceholders()
    {
        Assert.assertEquals("Hello. How are you?", format("Hello. How are you?").toString());
    }

    @Test
    public void onePlaceholder()
    {
        Assert.assertEquals("Hello, Alice. How are you?",
                format("Hello, ${name}. How are you?").with("name", "Alice").toString());
    }

    @Test
    public void manyPlaceholders()
    {
        Assert.assertEquals("Hello, Alice. How are you today?",
                format("Hello, ${name}. How are you ${time}?")
                        .with("name", "Alice").with("time", "today").toString());
    }

    @Test
    public void repeatingPlaceholders()
    {
        Assert.assertEquals("Hello, Alice. How are you today, Alice?",
                format("Hello, ${name}. How are you ${time}, ${name}?").with("name", "Alice").with("time", "today").toString());
    }

    @Test
    public void missingPlaceholder()
    {
        Assert.assertEquals("Hello, (name is unknown). How are you?",
                format("Hello, ${name}. How are you?").toString());

        Assert.assertEquals("Hello, (name is unknown). How are you today, (name is unknown)?",
                format("Hello, ${name}. How are you ${time}, ${name}?").with("time", "today").toStringSupplier().get());
    }
}