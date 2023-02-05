import java.util.Random;

public class Weapon extends  Equipment implements Damager{

    int attDmg;

    int attPerSec;

    @Override
    public int getDamage() {
        return attDmg;
    }

    @Override
    public int getAttPerSec() {
        return attPerSec;
    }

    final Random RANDOM = new Random();

    public Weapon(String title, int attDmg, int cost, int reqLvl){
        super(title);
        this.attDmg = attDmg;
        this.cost = cost;
        this.reqLvl = reqLvl;
        attPerSec = RANDOM.nextInt(1, 3);
    }
    public Weapon(String title){
            super(title);
            attDmg = RANDOM.nextInt(10);
            cost = RANDOM.nextInt(100);
            reqLvl = RANDOM.nextInt(10);
            attPerSec = RANDOM.nextInt(1,5);
    }

}
