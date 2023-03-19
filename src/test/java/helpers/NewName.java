package helpers;

import org.apache.commons.lang3.RandomStringUtils;

public class NewName {
    public static String newName(){
    return RandomStringUtils.random(8, true, true);
    }
}
