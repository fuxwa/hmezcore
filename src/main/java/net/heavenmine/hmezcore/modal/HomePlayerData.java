package net.heavenmine.hmezcore.modal;

public class HomePlayerData {
    private String id;
    private String username;
    private String uuid;
    private String homeName;
    private String world;
    private String x;
    private String y;
    private String z;

    @Override
    public String toString() {
        return "HomePlayerData{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", uuid='" + uuid + '\'' +
                ", homeName='" + homeName + '\'' +
                ", world='" + world + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", z='" + z + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public HomePlayerData(String id, String username, String uuid, String homeName,
                          String world, String x, String y, String z) {
        this.id = id;
        this.username = username;
        this.uuid = uuid;
        this.homeName = homeName;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
