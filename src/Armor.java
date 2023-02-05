public class Armor extends Equipment{
    private int defLvl;

    public Armor(String objectName, int defLvl) {
        super(objectName);
        this.defLvl = defLvl;
    }

    public int getDefLvl() {
        return defLvl;
    }
}
