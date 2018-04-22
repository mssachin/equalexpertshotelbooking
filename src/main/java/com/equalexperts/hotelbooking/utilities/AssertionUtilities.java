package com.equalexperts.hotelbooking.utilities;

import org.junit.Assert;


public class AssertionUtilities {


    public static void assertTrue(boolean condition, String failMessage){
            Assert.assertTrue(failMessage,condition);
    }


}
