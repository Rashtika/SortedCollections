import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Basket {
    private final String name;
    private final Map<StockItem, Integer> list;

    public Basket(String name) {
        this.name = name;
        this.list = new TreeMap<>();
    }

    public int addToBasket(StockItem item, int quantity) {
        if ((item != null) && (quantity > 0) && (quantity <= item.quantityInStock())) {
            int inBasket = list.getOrDefault(item, 0);
            list.put(item, inBasket + quantity);
            item.reserveItem(quantity);
            return inBasket;
        }
        return 0;
    }

    public int removeFromBasket(StockItem item, int quantity) {
        if ((item != null) && (list.get(item) >= quantity)) {
            int inBasket = list.getOrDefault(item, 0);
            list.put(item, inBasket - quantity);
            item.returnItem(quantity);
            return inBasket;
        }
        return 0;
    }

    public void checkOut () {
        for (Map.Entry<StockItem, Integer> item : list.entrySet()) {
            item.getKey().setReserved(item.getKey().getReserved() - item.getValue());
        }
    }

    public Map<StockItem, Integer> Items() {
        return Collections.unmodifiableMap(list);
    }

    @Override
    public String toString() {
        String s = "\nShopping basket " + name + " contains " + list.size() + ((list.size() == 1 )? " item" : " items") + "\n";
        double totalCost = 0.0;
        for (Map.Entry<StockItem, Integer> item : list.entrySet()) {
            s = s + item.getValue() + " " + item.getKey() + ". " + "\n";
            totalCost += item.getKey().getPrice() * item.getValue();
        }
        return s + "Total cost " + totalCost;
    }
}
