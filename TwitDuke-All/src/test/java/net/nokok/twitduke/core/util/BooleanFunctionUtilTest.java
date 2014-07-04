package net.nokok.twitduke.core.util;

import org.junit.Test;

/**
 *
 * @author noko
 */
public class BooleanFunctionUtilTest {

    @Test(expected = Exception.class)
    public void testThrowWhenTrueCondition_boolean_Supplier() throws Exception {
        BooleanFunctionalUtil.throwWhenTrueCondition(true, Exception::new);
    }

    @Test(expected = Exception.class)
    public void testThrowWhenTrueCondition_Supplier_Supplier() throws Exception {
        BooleanFunctionalUtil.throwWhenTrueCondition(() -> true, Exception::new);
    }

    @Test(expected = Exception.class)
    public void testThrowWhenFalseCondition_boolean_Supplier() throws Exception {
        BooleanFunctionalUtil.throwWhenFalseCondition(false, Exception::new);
    }

    @Test(expected = Exception.class)
    public void testThrowWhenFalseCondition_Supplier_Supplier() throws Exception {
        BooleanFunctionalUtil.throwWhenFalseCondition(() -> false, Exception::new);
    }

    @Test
    public void testShouldNotThrownAnyException() throws Exception {
        BooleanFunctionalUtil.throwWhenFalseCondition(true, Exception::new);
        BooleanFunctionalUtil.throwWhenFalseCondition(() -> true, Exception::new);
        BooleanFunctionalUtil.throwWhenTrueCondition(false, Exception::new);
        BooleanFunctionalUtil.throwWhenTrueCondition(() -> false, Exception::new);
    }

}
