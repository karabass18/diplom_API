package pantryApiTests.models;

import lombok.Data;

@Data
public class ResponseOfGetPantryModel {
    private String name, description;
    private boolean notifications;
    private int percentFull;
    private String[] errors;
    private BasketInstance[] baskets;

    @Data
    public static class BasketInstance {
        private String name;
        private long ttl;
    }
}
