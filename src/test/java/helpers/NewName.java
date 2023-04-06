package helpers;

import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;

public class NewName {

    public static @NotNull String newName(){
    return RandomStringUtils.random(8, true, true);
    }
}
