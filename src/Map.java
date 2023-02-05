import java.util.ArrayList;

public class Map {
    private ArrayList<Cell> gameMap;

    public void setMapSize(int size){
        gameMap = new ArrayList<Cell>();
        for (int i = -1000; i < size; i++) {
            for (int j = -1000; j < size; j++) {
                gameMap.add(new Cell(i, j));
            }
        }
    }

    Cell findCell(int x, int y){
        for (int i = 0; i < gameMap.size(); i++){
            if (gameMap.get(i).getPosition().getX() == x && gameMap.get(i).getPosition().getY() == y){
                return gameMap.get(i);
            }
        }
        return null;
    }
}
