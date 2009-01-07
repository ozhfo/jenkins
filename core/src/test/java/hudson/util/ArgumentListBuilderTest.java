package hudson.util;

import org.junit.Assert;
import org.junit.Test;

public class ArgumentListBuilderTest extends Assert {

    public static void assertArrayEquals(String msg, boolean[] expected, boolean[] actual) {
        assertArrayEquals(msg,box(expected),box(actual));
    }

    private static Boolean[] box(boolean[] a) {
        if(a==null)     return null;
        Boolean[] r = new Boolean[a.length];
        for (int i = 0; i < a.length; i++)
            r[i] = a[i];
        return r;
    }


    @Test
    public void assertEmptyMask() {
        ArgumentListBuilder builder = new ArgumentListBuilder();
        builder.add("arg");
        builder.add("other", "arguments");

        assertFalse("There shouldnt be any masked arguments", builder.hasMaskedArguments());
        boolean[] array = builder.toMaskArray();
        assertNotNull("The mask array should not be null", array);
        assertArrayEquals("The mask array was incorrect", new boolean[]{false,false,false}, array);
    }

    @Test
    public void assertLastArgumentIsMasked() {
        ArgumentListBuilder builder = new ArgumentListBuilder();
        builder.add("arg");
        builder.addMasked("ismasked");

        assertTrue("There should be masked arguments", builder.hasMaskedArguments());
        boolean[] array = builder.toMaskArray();
        assertNotNull("The mask array should not be null", array);
        assertArrayEquals("The mask array was incorrect", new boolean[]{false,true}, array);
    }

    @Test
    public void assertSeveralMaskedArguments() {
        ArgumentListBuilder builder = new ArgumentListBuilder();
        builder.add("arg");
        builder.addMasked("ismasked");
        builder.add("non masked arg");
        builder.addMasked("ismasked2");

        assertTrue("There should be masked arguments", builder.hasMaskedArguments());
        boolean[] array = builder.toMaskArray();
        assertNotNull("The mask array should not be null", array);
        assertArrayEquals("The mask array was incorrect", new boolean[]{false,true, false, true}, array);
    }

    @Test
    public void assertPrependAfterAddingMasked() {
        ArgumentListBuilder builder = new ArgumentListBuilder();
        builder.addMasked("ismasked");
        builder.add("arg");
        builder.prepend("first", "second");

        assertTrue("There should be masked arguments", builder.hasMaskedArguments());
        boolean[] array = builder.toMaskArray();
        assertNotNull("The mask array should not be null", array);
        assertArrayEquals("The mask array was incorrect", new boolean[]{false,false,true,false}, array);
    }

    @Test
    public void assertPrependBeforeAddingMasked() {
        ArgumentListBuilder builder = new ArgumentListBuilder();
        builder.prepend("first", "second");
        builder.addMasked("ismasked");
        builder.add("arg");

        assertTrue("There should be masked arguments", builder.hasMaskedArguments());
        boolean[] array = builder.toMaskArray();
        assertNotNull("The mask array should not be null", array);
        assertArrayEquals("The mask array was incorrect", new boolean[]{false,false,true,false}, array);
    }
}
