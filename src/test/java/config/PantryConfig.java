package config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:pantry.properties"})

public interface PantryConfig extends Config{

    @Config.Key("myPantryId")
    String getPantryId();
}
