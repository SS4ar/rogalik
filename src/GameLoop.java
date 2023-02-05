import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLoop{
    public static void start(){
        String[] dictEns = {
                "zombie",
                "ork",
                "troll"
        };

        String[] dictWeapons = {
                "awp",
                "m4a1",
                "ak74",
                "hk417",
                "g36"
        };

        final Random RANDOM = new Random();

        Map map  = new Map();
        map.setMapSize(1000);

        List<Mob> enemies = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            enemies.add(new Mob(dictEns[(RANDOM.nextInt(dictEns.length))], new Weapon(dictWeapons[(RANDOM.nextInt(dictWeapons.length))])));
            Thread mob = new Thread(new CreatureController(enemies.get(i), map));
            mob.start();
        }

        Player player = new Player("hero", 50, 7,  100);
        Thread playerThread = new Thread(new PlayerController(player, map));
        playerThread.start();
    }
}
