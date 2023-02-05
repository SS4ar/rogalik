import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PlayerController implements Runnable{

    Player tempPlayer;

    Map tempMap = new Map();

    Mob tempMob;

    final Random RANDOM = new Random();

    public void setTempMap(Map tempMap) {
        this.tempMap = tempMap;
    }


    @Override
    public void run() {
        int randomX = (RANDOM.nextInt(10));
        int randomY = (RANDOM.nextInt(10));
        tempMap.findCell(randomX, randomY).put(tempPlayer);
        tempPlayer.position.setPosition(randomX, randomY);
        while (tempPlayer.hp > 0){
            if (tempMap.findCell(tempPlayer.position.getX(), tempPlayer.position.getY()).getObjectInCell().size() == 1){
                try {
                    walk();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (tempMap.findCell(tempPlayer.position.getX(), tempPlayer.position.getY()).getObjectInCell().size() == 2){
                for (int i = 0; i < 2; i++) {
                    if (BaseMobs.listZombies.contains(tempMap.findCell(tempPlayer.position.getX(), tempPlayer.position.getY())
                            .getObjectInCell().get(i).getObjectIdentifier()) || BaseMobs.listOrks.contains(tempMap
                            .findCell(tempPlayer.position.getX(), tempPlayer.position.getY())
                            .getObjectInCell().get(i).getObjectIdentifier()) || BaseMobs.listTrolls.contains(tempMap
                            .findCell(tempPlayer.position.getX(), tempPlayer.position.getY())
                            .getObjectInCell().get(i).getObjectIdentifier())) {
                        tempMob = (Mob) tempMap.findCell(tempPlayer.position.getX(), tempPlayer.position.getY()).getObjectInCell().get(i);
                    }
                }
                try {
                        fightMob(tempMob);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (tempMap.findCell(tempPlayer.position.getX(), tempPlayer.position.getY()).getObjectInCell().size() > 2){
                ArrayList<Mob> mobsInCell = new ArrayList<Mob>();
                for (int i = 0; i < tempMap.findCell(tempPlayer.position.getX(), tempPlayer.position.getY()).getObjectInCell().size(); i++) {
                    if (BaseMobs.listZombies.contains(tempMap.findCell(tempPlayer.position.getX(), tempPlayer.position.getY())
                            .getObjectInCell().get(i).getObjectIdentifier()) || BaseMobs.listOrks.contains(tempMap
                            .findCell(tempPlayer.position.getX(), tempPlayer.position.getY())
                            .getObjectInCell().get(i).getObjectIdentifier()) || BaseMobs.listTrolls.contains(tempMap
                            .findCell(tempPlayer.position.getX(), tempPlayer.position.getY())
                            .getObjectInCell().get(i).getObjectIdentifier())) {
                        Mob localMob = (Mob) tempMap.findCell(tempPlayer.position.getX(), tempPlayer.position.getY()).getObjectInCell()
                                .get(i);
                        mobsInCell.add(localMob);
                    }
                }
                int maxDps = 0;
                for (Mob mob: mobsInCell) {
                    if (mob.dps() > maxDps) {
                        maxDps = mob.dps();
                        tempMob = mob;
                    }
                }
                try {
                    fightMob(tempMob);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }

    }
    public PlayerController(Player tempPlayer, Map tempMap) {
        this.tempPlayer = tempPlayer;
        this.tempMap = tempMap;
    }

    void fightMob(Mob mob) throws InterruptedException{
        while (tempMob.hp > 0 && tempPlayer.hp > 0) {
            TimeUnit.MILLISECONDS.sleep(1000);
            GameLogic.fight(tempPlayer, mob);
            System.out.println("[PLAYER][" + tempPlayer.getObjectName() + "] наносит урон " + tempPlayer.dps() + " "
                    +  tempMob.getObjectName());

        }
        if (tempMob.hp < 0 && tempMap.findCell(tempPlayer.position.getX(), tempPlayer.position.getY())
                .getObjectInCell().contains(tempMob)){
            System.out.println("[MOB][" + tempMob.getObjectName() + "] погиб");
            if (tempMob.eqWpSlot.tempWp().getDamage() > tempPlayer.eqWpSlot.tempWp().getDamage()
            || tempMob.eqArmorSlot.tempArmor().getDefLvl() > tempPlayer.eqArmorSlot.tempArmor().getDefLvl()) {
                tempPlayer.takeLoot(tempMob);
                for (int i = 0; i < tempPlayer.inventory.getItems().size(); i++) {
                    try {
                        Weapon tempWp = (Weapon) tempPlayer.inventory.getItems().get(i);
                        tempPlayer.eqWpSlot.put(tempWp);
                    }
                    catch (TypeNotPresentException exception){
                        Armor tempArmor = (Armor) tempPlayer.inventory.getItems().get(i);
                        tempPlayer.eqArmorSlot.put(tempArmor);
                    }
                }
            }
            tempMap.findCell(tempPlayer.position.getX(),tempPlayer.position.getY()).delete(tempMob);

        }
        if (tempPlayer.hp < 0){
            System.out.println("[PLAYER][" + tempPlayer.getObjectName() + "] погиб");
            tempMap.findCell(tempPlayer.position.getX(),tempPlayer.position.getY()).delete(tempPlayer);
        }
    }

    public void walk() throws InterruptedException {
            TimeUnit.MILLISECONDS.sleep(1000);
            int n = RANDOM.nextInt(0,8);
            int lastX = tempPlayer.position.getX();
            int lastY = tempPlayer.position.getY();
            switch (n) {
                case 0:
                    tempMap.findCell(tempPlayer.position.getX(),tempPlayer.position.getY()).delete(tempPlayer);
                    tempPlayer.position.setPosition(tempPlayer.position.getX(), tempPlayer.position.getY());
                    System.out.println("[PLAYER]" + "[" + tempPlayer.getObjectName() + "]: перемещается с (" +
                            lastX + ";" + lastY + ") -> (" + tempPlayer.position.getX() + ";"
                            + tempPlayer.position.getY() + ")");
                    tempMap.findCell(tempPlayer.position.getX(),tempPlayer.position.getY()).put(tempPlayer);
                    break;
                case 1:
                    tempMap.findCell(tempPlayer.position.getX(),tempPlayer.position.getY()).delete(tempPlayer);
                    tempPlayer.position.setPosition(tempPlayer.position.getX(), tempPlayer.position.getY() + 1);
                    System.out.println("[PLAYER]" + "[" + tempPlayer.getObjectName() + "]: перемещается с (" +
                            lastX + ";" + lastY + ") -> (" + tempPlayer.position.getX() + ";"
                            + tempPlayer.position.getY() + ")");
                    tempMap.findCell(tempPlayer.position.getX(),tempPlayer.position.getY()).put(tempPlayer);
                    break;
                case 2:
                    tempMap.findCell(tempPlayer.position.getX(),tempPlayer.position.getY()).delete(tempPlayer);
                    tempPlayer.position.setPosition(tempPlayer.position.getX() - 1, tempPlayer.position.getY());
                    System.out.println("[PLAYER]" + "[" + tempPlayer.getObjectName() + "]: перемещается с (" +
                            lastX + ";" + lastY + ") -> (" + tempPlayer.position.getX() + ";"
                            + tempPlayer.position.getY() + ")");
                    tempMap.findCell(tempPlayer.position.getX(),tempPlayer.position.getY()).put(tempPlayer);
                    break;
                case 3:
                    tempMap.findCell(tempPlayer.position.getX(),tempPlayer.position.getY()).delete(tempPlayer);
                    tempPlayer.position.setPosition(tempPlayer.position.getX(), tempPlayer.position.getY() - 1);
                    System.out.println("[PLAYER]" + "[" + tempPlayer.getObjectName() + "]: перемещается с (" +
                            lastX + ";" + lastY + ") -> (" + tempPlayer.position.getX() + ";"
                            + tempPlayer.position.getY() + ")");
                    tempMap.findCell(tempPlayer.position.getX(),tempPlayer.position.getY()).put(tempPlayer);
                    break;
                case 4:
                    tempMap.findCell(tempPlayer.position.getX(),tempPlayer.position.getY()).delete(tempPlayer);
                    tempPlayer.position.setPosition(tempPlayer.position.getX() - 1, tempPlayer.position.getY() - 1);
                    System.out.println("[PLAYER]" + "[" + tempPlayer.getObjectName() + "]: перемещается с (" +
                            lastX + ";" + lastY + ") -> (" + tempPlayer.position.getX() + ";"
                            + tempPlayer.position.getY() + ")");
                    tempMap.findCell(tempPlayer.position.getX(),tempPlayer.position.getY()).put(tempPlayer);
                    break;
                case 5:
                    tempMap.findCell(tempPlayer.position.getX(),tempPlayer.position.getY()).delete(tempPlayer);
                    tempPlayer.position.setPosition(tempPlayer.position.getX() + 1, tempPlayer.position.getY() + 1);
                    System.out.println("[PLAYER]" + "[" + tempPlayer.getObjectName() + "]: перемещается с (" +
                            lastX + ";" + lastY + ") -> (" + tempPlayer.position.getX() + ";"
                            + tempPlayer.position.getY() + ")");
                    tempMap.findCell(tempPlayer.position.getX(),tempPlayer.position.getY()).put(tempPlayer);
                    break;
                case 6:
                    tempMap.findCell(tempPlayer.position.getX(),tempPlayer.position.getY()).delete(tempPlayer);
                    tempPlayer.position.setPosition(tempPlayer.position.getX() + 1, tempPlayer.position.getY());
                    System.out.println("[PLAYER]" + "[" + tempPlayer.getObjectName() + "]: перемещается с (" +
                            lastX + ";" + lastY + ") -> (" + tempPlayer.position.getX() + ";"
                            + tempPlayer.position.getY() + ")");
                    tempMap.findCell(tempPlayer.position.getX(),tempPlayer.position.getY()).put(tempPlayer);
                    break;
                case 7:
                    tempMap.findCell(tempPlayer.position.getX(),tempPlayer.position.getY()).delete(tempPlayer);
                    tempPlayer.position.setPosition(tempPlayer.position.getX() + 1, tempPlayer.position.getY() - 1);
                    System.out.println("[PLAYER]" + "[" + tempPlayer.getObjectName() + "]: перемещается с (" +
                            lastX + ";" + lastY + ") -> (" + tempPlayer.position.getX() + ";"
                            + tempPlayer.position.getY() + ")");
                    tempMap.findCell(tempPlayer.position.getX(),tempPlayer.position.getY()).put(tempPlayer);
                    break;
                case 8:
                    tempMap.findCell(tempPlayer.position.getX(),tempPlayer.position.getY()).delete(tempPlayer);
                    tempPlayer.position.setPosition(tempPlayer.position.getX() - 1, tempPlayer.position.getY() + 1);
                    System.out.println("[PLAYER]" + "[" + tempPlayer.getObjectName() + "]: перемещается с (" +
                            lastX + ";" + lastY + ") -> (" + tempPlayer.position.getX() + ";"
                            + tempPlayer.position.getY() + ")");
                    tempMap.findCell(tempPlayer.position.getX(),tempPlayer.position.getY()).put(tempPlayer);
                    break;
            }
        }

}

