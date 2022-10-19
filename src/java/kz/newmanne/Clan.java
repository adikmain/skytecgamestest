package kz.newmanne;

import java.util.concurrent.atomic.LongAdder;

public class Clan {
    public long id;
    public String name;
    public LongAdder gold;

    public Clan(long id, String name, LongAdder gold) {
        this.id = id;
        this.name = name;
        this.gold = gold;
    }
}
