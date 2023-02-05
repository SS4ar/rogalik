public interface Attacker {

    void attack(Damageable target);

    default int dps() {
        return 0;
    }
}
