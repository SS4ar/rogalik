import javax.swing.plaf.TableHeaderUI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CreatureController implements Runnable{


    Mob tempMob;

    Player tempPlayer;

    Map tempMap;

    Thread creatureThread = new Thread();

    final Random RANDOM = new Random();

    @Override
    public void run() {
        int randomX = (RANDOM.nextInt(10));
        int randomY = (RANDOM.nextInt(10));
        tempMap.findCell(randomX, randomY).put(tempMob);
        tempMob.position.setPosition(randomX, randomY);
        while (tempMob.hp > 0){
            if (tempMap.findCell(tempMob.position.getX(), tempMob.position.getY()).getObjectInCell().size() == 1){
                try {
                    walk();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            uniq(tempMap.findCell(tempMob.position.getX(), tempMob.position.getY()).getObjectInCell());
            if (tempMap.findCell(tempMob.position.getX(), tempMob.position.getY()).getObjectInCell().size() > 1){
                ArrayList<Mob> mobsInCell = new ArrayList<Mob>();
                for (int i = 0; i < tempMap.findCell(tempMob.position.getX(), tempMob.position.getY())
                        .getObjectInCell().size(); i++) {
                    if (BaseMobs.listZombies.contains(tempMap.findCell(tempMob.position.getX(), tempMob.position.getY())
                            .getObjectInCell().get(i).getObjectIdentifier()) ||  BaseMobs.listTrolls
                            .contains(tempMap.findCell(tempMob.position.getX(), tempMob.position.getY())
                            .getObjectInCell().get(i).getObjectIdentifier()) || BaseMobs.listOrks
                            .contains(tempMap.findCell(tempMob.position.getX(), tempMob.position.getY())
                            .getObjectInCell().get(i).getObjectIdentifier())){
                        mobsInCell.add((Mob) tempMap.findCell(tempMob.position.getX(), tempMob.position.getY()).getObjectInCell().get(i));
                    }
                }

                uniq(mobsInCell);
                if (mobsInCell.size() > 1){
                    uniq(tempMap.findCell(tempMob.position.getX(), tempMob.position.getY()).getObjectInCell());
                    System.out.println("[CELL][" + tempMob.position.getX() + ";" + tempMob.position.getY()
                    + "] Встретились мобы и их базовый урон увеличился на 1");
                    for (int i = 0; i < mobsInCell.size(); i++) {
                        tempMap.findCell(tempMob.position.getX(), tempMob.position.getY()).getObjectInCell().remove(mobsInCell.get(i));
                        mobsInCell.get(i).attDmg += 1;
                        tempMap.findCell(tempMob.position.getX(), tempMob.position.getY()).getObjectInCell().add(mobsInCell.get(i));
                    }

                    try {
                        walk();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }


                }
                if (mobsInCell.size() == 1){
                    for (int i = 0; i < tempMap.findCell(tempMob.position.getX(), tempMob.position.getY())
                            .getObjectInCell().size(); i++) {
                        if (!(BaseMobs.listZombies.contains(tempMap.findCell(tempMob.position.getX(), tempMob.position.getY())
                                .getObjectInCell().get(i).getObjectIdentifier()) || BaseMobs.listTrolls
                                .contains(tempMap.findCell(tempMob.position.getX(), tempMob.position.getY())
                                        .getObjectInCell().get(i).getObjectIdentifier()) || BaseMobs.listOrks
                                .contains(tempMap.findCell(tempMob.position.getX(), tempMob.position.getY())
                                        .getObjectInCell().get(i).getObjectIdentifier()))) {
                            tempPlayer = (Player) tempMap.findCell(tempMob.position.getX(), tempMob.position.getY())
                                    .getObjectInCell().get(i);

                        }
                    }
                    try {
                        fightsPlayer();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }


                mobsInCell.clear();
            }

        }
    }


    public CreatureController(Mob tempMob, Map tempMap) {
        this.tempMob = tempMob;
        this.tempMap = tempMap;
    }

    private void fightsPlayer() throws InterruptedException{
        TimeUnit.MILLISECONDS.sleep(1000);
        while (tempMob.hp > 0 && tempPlayer.hp > 0) {
            GameLogic.fight(tempMob, tempPlayer);
            System.out.println("[MOB][" + tempMob.getObjectName() + "] наносит урон " + tempMob.dps() + " "
                    +  tempPlayer.getObjectName());

        }

    }
    public void walk() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(1000);
        int n = RANDOM.nextInt(4);
        int lastX = tempMob.position.getX();
        int lastY = tempMob.position.getY();
        switch (n){
            case 0:
                tempMap.findCell(tempMob.position.getX(),tempMob.position.getY()).delete(tempPlayer);
                tempMob.position.setPosition(tempMob.position.getX(), tempMob.position.getY());
                System.out.println("[MOB]" + "[" + tempMob.getObjectName() + "]: перемещается с (" +
                        lastX + ";" + lastY + ") -> (" + tempMob.position.getX() + ";" + tempMob.position.getY() + ")");
                tempMap.findCell(tempMob.position.getX(),tempMob.position.getY()).put(tempMob);
                break;
            case 1:
                tempMap.findCell(tempMob.position.getX(),tempMob.position.getY()).delete(tempPlayer);
                tempMob.position.setPosition(tempMob.position.getX() + 1, tempMob.position.getY());
                System.out.println("[MOB]" + "[" + tempMob.getObjectName() + "]: перемещается с (" +
                        lastX + ";" + lastY + ") -> (" + tempMob.position.getX() + ";" + tempMob.position.getY() + ")");
                tempMap.findCell(tempMob.position.getX(),tempMob.position.getY()).put(tempMob);
                break;
            case 2:
                tempMap.findCell(tempMob.position.getX(),tempMob.position.getY()).delete(tempPlayer);
                tempMob.position.setPosition(tempMob.position.getX(), tempMob.position.getY() + 1);
                System.out.println("[MOB]" + "[" + tempMob.getObjectName() + "]: перемещается с (" +
                        lastX + ";" + lastY + ") -> (" + tempMob.position.getX() + ";" + tempMob.position.getY() + ")");
                tempMap.findCell(tempMob.position.getX(),tempMob.position.getY()).put(tempMob);
                break;
            case 3:
                tempMap.findCell(tempMob.position.getX(),tempMob.position.getY()).delete(tempPlayer);
                tempMob.position.setPosition(tempMob.position.getX() - 1, tempMob.position.getY());
                System.out.println("[MOB]" + "[" + tempMob.getObjectName() + "]: перемещается с (" +
                        lastX + ";" + lastY + ") -> (" + tempMob.position.getX() + ";" + tempMob.position.getY() + ")");
                tempMap.findCell(tempMob.position.getX(),tempMob.position.getY()).put(tempMob);
                break;
            case 4:
                tempMap.findCell(tempMob.position.getX(),tempMob.position.getY()).delete(tempPlayer);
                tempMob.position.setPosition(tempMob.position.getX(), tempMob.position.getY() - 1);
                System.out.println("[MOB]" + "[" + tempMob.getObjectName() + "]: перемещается с (" +
                        lastX + ";" + lastY + ") -> (" + tempMob.position.getX() + ";" + tempMob.position.getY() + ")");
                tempMap.findCell(tempMob.position.getX(),tempMob.position.getY()).put(tempMob);
                break;
        }
    }

    void uniq(ArrayList arrayList){
        Set<GameObject> set= new HashSet<>(arrayList);
        arrayList.clear();
        arrayList.addAll(set);
    }


}
