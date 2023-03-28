package net.heavenmine.hmezcore.modal;

public class PlayerData {
    private String id;
    private String userName;
    private String UUID;
    private String IPAddress;
    private String firstJoin;
    private String lastJoin;
    private String location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PlayerData(String id, String userName, String UUID, String IPAddress, String firstJoin, String lastJoin, String location) {
        this.id = id;
        this.userName = userName;
        this.UUID = UUID;
        this.IPAddress = IPAddress;
        this.firstJoin = firstJoin;
        this.lastJoin = lastJoin;
        this.location = location;
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
                ", location='" + location + '\'' +
                '}';
    }
}
