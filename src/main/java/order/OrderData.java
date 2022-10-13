package order;

import java.util.ArrayList;
import java.util.List;

public class OrderData {

    private List<String> ingredients = new ArrayList<>();

    public OrderData(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public static OrderData getOrderCorrect() {
        return new OrderData(List.of("61c0c5a71d1f82001bdaaa6d","61c0c5a71d1f82001bdaaa74","61c0c5a71d1f82001bdaaa7a"));
    }

    public static OrderData getOrderInvalidHash() {
        return new OrderData(List.of("1","2","3"));
    }

    public static OrderData getOrderEmpty() {
        return new OrderData(List.of());
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
