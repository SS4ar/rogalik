import java.util.ArrayList;
import java.util.List;

public class Inventory<T extends Item>{
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }
    void put(Item item){
        items.add(item);
    }

    void remove(Item item){
        items.remove(item);
    }

    void clear(){
        items.clear();
    }

    public Inventory() {
        items = new ArrayList<>();
    }
}
