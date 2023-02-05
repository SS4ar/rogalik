public class Player extends Creature
    implements  Attacker, Damageable {
    int tempLvl;

    double maxWeight;

    Inventory inventory = new Inventory();


    EquipmentCell<Weapon> eqWpSlot = new EquipmentCell<Weapon>();

    EquipmentCell<Armor> eqArmorSlot = new EquipmentCell<Armor>();
    public Player(String name, int hp, int attDmg, double maxWeight){
        super(name);
        this.hp = hp;
        this.attDmg = attDmg;
        this.maxWeight = maxWeight;
        tempLvl = 0;
        eqArmorSlot.put(new Armor("Skin", 0));
        defLvl = eqArmorSlot.tempArmor().getDefLvl();
        eqWpSlot.put(new Weapon("Arm", 5, 0, 0));

    }

    @Override
    public void attack(Damageable target) {
        target.getHit(this);
    }

    @Override
    public int dps() {
        return (attDmg + eqWpSlot.tempWp().getDamage()) * eqWpSlot.tempWp().getAttPerSec();
    }
    @Override
    public void getHit(Attacker attacker) {
        hp -= (attacker.dps() - defLvl);
    }

    public void takeLoot(Mob lootable) {
        inventory.getItems().addAll(lootable.dropLoot());
    }

    public boolean checkWp(Weapon wp){
        return tempLvl >= wp.reqLvl;
    }
}
