public abstract class GameObject {
    private final int objectIdentifier;

    private final String objectName;

    private static int idCounter = 0;

    public GameObject(String objectName) {
        this.objectName = objectName;
        this.objectIdentifier = ++idCounter;
    }

    public int getObjectIdentifier() {
        return objectIdentifier;
    }

    public String getObjectName() {
        return objectName;
    }
}
