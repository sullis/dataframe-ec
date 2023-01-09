package io.github.vmzakharov.ecdataframe.util;

import org.junit.Assert;
import org.junit.Test;

public class ErrorReporterBuilderTest
{
    @Test
    public void defaultExceptionType()
    {
        try
        {
            ErrorReporter.initialize();
            ErrorReporter.exception("Hello").fire();
            Assert.fail("didn't throw");
        }
        catch (RuntimeException e)
        {
            Assert.assertEquals(RuntimeException.class, e.getClass());
            Assert.assertEquals("Hello", e.getMessage());
        }
    }

    @Test
    public void defaultExceptionTypeWithCauseArg()
    {
        Throwable cause = new RuntimeException("Boom!");

        try
        {
            ErrorReporter.initialize();
            ErrorReporter.exception("Hello").fire(cause);
            Assert.fail("didn't throw");
        }
        catch (RuntimeException e)
        {
            Assert.assertEquals(RuntimeException.class, e.getClass());
            Assert.assertEquals("Hello", e.getMessage());
            Assert.assertEquals(cause, e.getCause());
        }
    }

    @Test
    public void defaultUnsupported()
    {
        try
        {
            ErrorReporter.initialize();
            throw ErrorReporter.exception("Do it!").getUnsupported();
        }
        catch (UnsupportedOperationException e)
        {
            Assert.assertEquals(UnsupportedOperationException.class, e.getClass());
            Assert.assertEquals("Do it!", e.getMessage());
        }
    }

    @Test
    public void overrideExceptionType()
    {
        try
        {
            ErrorReporter.exceptionFactories(VerySpecialException::new, VerySpecialException::new, DontWanna::new);
            ErrorReporter.exception("Hello").fire();
            Assert.fail("didn't throw");
        }
        catch (RuntimeException e)
        {
            Assert.assertEquals(VerySpecialException.class, e.getClass());
            Assert.assertEquals("Hello", e.getMessage());
        }
    }

    @Test
    public void overrideUnsupported()
    {
        try
        {
            ErrorReporter.exceptionFactories(VerySpecialException::new, VerySpecialException::new, DontWanna::new);
            throw ErrorReporter.exception("Do it!").getUnsupported();
        }
        catch (UnsupportedOperationException e)
        {
            Assert.assertEquals(DontWanna.class, e.getClass());
            Assert.assertEquals("Do it!", e.getMessage());
        }
    }

    @Test
    public void overrideExceptionTypeWithCauseArg()
    {
        Throwable cause = new RuntimeException("Boom!");

        try
        {
            ErrorReporter.exceptionFactories(VerySpecialException::new, VerySpecialException::new, DontWanna::new);
            ErrorReporter.exception("Hello").fire(cause);
            Assert.fail("didn't throw");
        }
        catch (RuntimeException e)
        {
            Assert.assertEquals(VerySpecialException.class, e.getClass());
            Assert.assertEquals("Hello", e.getMessage());
            Assert.assertEquals(cause, e.getCause());
        }
    }

    @Test
    public void errorPrinter()
    {
        CollectingPrinter printer = new CollectingPrinter();
        ErrorReporter.setPrintedMessagePrefix("Boo-boo: ");
        ErrorReporter.setErrorPrinter(printer);

        try
        {
            ErrorReporter.exception("ouch").fire();
        }
        catch (RuntimeException e)
        {
            Assert.assertEquals("Boo-boo: ouch\n", printer.toString());
        }

        printer.clear();

        try
        {
            ErrorReporter.reportAndThrow("ow-ow", new RuntimeException("Nothing to see here"));
        }
        catch (RuntimeException e)
        {
            Assert.assertEquals("Boo-boo: ow-ow\n", printer.toString());
        }

        printer.clear();

        try
        {
            ErrorReporter.exception("oh, well").getUnsupported();
        }
        catch (UnsupportedOperationException e)
        {
            Assert.assertEquals("Boo-boo: oh, well\n", printer.toString());
        }

        ErrorReporter.initialize();
    }

    private static class VerySpecialException
    extends RuntimeException
    {
        public VerySpecialException(String message)
        {
            super(message);
        }

        public VerySpecialException(String message, Throwable cause)
        {
            super(message, cause);
        }
    }

    private static class DontWanna
    extends UnsupportedOperationException
    {
        public DontWanna(String message)
        {
            super(message);
        }
    }
}
