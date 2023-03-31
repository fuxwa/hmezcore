package net.heavenmine.hmezcore.modal;

public class PlayerData {
    private String id;
    private String userName;
    private String UUID;
    private String IPAddress;
    private String firstJoin;
    private String lastJoin;
    private String world;
    private String x;
    private String y;
    private String z;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getFirstJoin() {
        return firstJoin;
    }

    public void setFirstJoin(String firstJoin) {
        this.firstJoin = firstJoin;
    }

    public String getLastJoin() {
        return lastJoin;
    }

    public void setLastJoin(String lastJoin) {
        this.lastJoin = lastJoin;
    }

    public PlayerData(String id, String userName, String UUID, String IPAddress, String firstJoin, String lastJoin, String world, String x, String y, String z) {
        this.id = id;
        this.userName = userName;
        this.UUID = UUID;
        this.IPAddress = IPAddress;
        this.firstJoin = firstJoin;
        this.lastJoin = lastJoin;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "PlayerData{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", UUID='" + UUID + '\'' +
                ", IPAddress='" + IPAddress + '\'' +
                ", firstJoin='" + firstJoin + '\'' +
                ", lastJoin='" + lastJoin + '\'' +
                ", world='" + world + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", z='" + z + '\'' +
                '}';
    }
}
