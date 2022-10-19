package kz.newmanne;

import java.util.List;
import java.util.concurrent.atomic.LongAdder;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        AddFoundService addFoundService = new AddFoundService();
        Clan clan = new Clan(1, "1", new LongAdder());
        User user1 = new User(1, 1000, "test1");
        User user2 = new User(2, 1000, "test2");
        User user3 = new User(3, 1000, "test3");
        addFoundService.users.addAll(List.of(user1, user2, user3));
        addFoundService.clans.add(clan);

        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                addFoundService.processUserDonation(user1.id, clan.id, 1);
            }).start();
            new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                addFoundService.processUserDonation(user2.id, clan.id, 1);
            }).start();
            new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                addFoundService.processUserDonation(user3.id, clan.id, 1);
            }).start();
        }

        Thread.sleep(5000);

        System.out.println(user1.gold);
        System.out.println(user2.gold);
        System.out.println(user3.gold);
        System.out.println(clan.gold);
    }
}