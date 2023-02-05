import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mob extends Creature
        implements Lootable, Attacker, Damageable {
    String title;

    Inventory inventory = new Inventory();

    EquipmentCell<Weapon> eqWpSlot = new EquipmentCell<Weapon>();

    EquipmentCell<Armor> eqArmorSlot = new EquipmentCell<Armor>();
    final Random RANDOM = new Random();

    public Mob(String title, int hp, int attDmg, int defLvl){
        super(title);
        this.hp = hp;
        this.attDmg = attDmg;
        this.defLvl = defLvl;
    }

    public Mob(String title, Weapon weapon){
        super(title);
        hp = RANDOM.nextInt(100);
        attDmg = RANDOM.nextInt(10);
        eqArmorSlot.put(new Armor("Skin", 0));
        defLvl = eqArmorSlot.tempArmor().getDefLvl();
        eqWpSlot.put(weapon);
        if (title == "troll"){
            BaseMobs.listTrolls.add(this.getObjectIdentifier());
        }
        if (title == "ork"){
            BaseMobs.listOrks.add(this.getObjectIdentifier());
        }
        if (title == "zombie"){
            BaseMobs.listZombies.add(this.getObjectIdentifier());
        }
    }

    @Override
    public List<Item> dropLoot() {
        return inventory.getItems();
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

}
