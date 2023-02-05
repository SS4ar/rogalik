import java.util.List;

public class EquipmentCell <T extends Equipment> {
    private T wpSlot;

    private T armorSlot;
    T tempWp(){
        return wpSlot;
    }

    T tempArmor(){
        return armorSlot;
    }
    void put(T equipment) {
        if (equipment.getClass() == Weapon.class) {
            wpSlot = equipment;
        }
        if (equipment.getClass() == Armor.class) {
            armorSlot = equipment;
        }
    }

    void remove(T equipment){
        if (equipment.getClass() == Weapon.class) {
            wpSlot = null;
        }
        if (equipment.getClass() == Armor.class) {
            armorSlot = null;
        }
    }
}
