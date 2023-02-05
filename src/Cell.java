import java.util.ArrayList;

public class Cell {
    private Position position;

    private ArrayList<GameObject> objectsInCell = new ArrayList<GameObject>();

    void put(GameObject gameObject){
        objectsInCell.add(gameObject);
    }

    void delete(GameObject gameObject){
        objectsInCell.remove(gameObject);
    }

    public ArrayList<GameObject> getObjectInCell() {
        return objectsInCell;
    }

    public Position getPosition() {
        return position;
    }

    public Cell(int x, int y) {
        this.position = new Position();
        this.position.setPosition(x, y);
    }
}
