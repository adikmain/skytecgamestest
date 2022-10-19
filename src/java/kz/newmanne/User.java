package kz.newmanne;

public class User {
    public long id;
    public volatile int gold;

    public String username;

    public User(long id, int gold, String username) {
        this.id = id;
        this.gold = gold;
        this.username = username;
    }
}
