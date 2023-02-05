import java.io.Serializable;

public class Position implements Serializable {
    private int x;

    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public void setToZero(){
        x = 0;
        y = 0;
    }

    public Position getRelative(int x, int y){
        return new Position(this.x + x, this.y + y);
    }

    public Position(){
        setToZero();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
