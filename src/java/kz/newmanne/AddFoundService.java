package kz.newmanne;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class AddFoundService {
    List<User> users = new ArrayList<>();
    List<Clan> clans = new ArrayList<>();

    void processUserDonation(long userId, long clanId, long amount) {
        User user = users.stream().filter(u -> u.id == userId).findFirst().orElseThrow();
        Clan clan = clans.stream().filter(c -> c.id == clanId).findFirst().orElseThrow();
        Instant now;

        synchronized (user) {
            now = Instant.now();
            if (user.gold < amount) throw new IllegalStateException();
            user.gold -= amount;
        }
        clan.gold.add(amount);
        logUserDonation(user, clan, amount, now);
    }

    private void logUserDonation(User user, Clan clan, long amount, Instant date) {
        String SQL = "insert into PUBLIC.GOLD_FLOW_HISTORY(USERNAME, AMOUNT, CLAN_ID, DONATION_TIME) " +
                "values (?,?,?,?)";
        try (
                Connection conn = getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, user.username);
            preparedStatement.setLong(2, amount);
            preparedStatement.setLong(3, clan.id);
            preparedStatement.setTimestamp(4, Timestamp.from(date));
            preparedStatement.execute();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
    }
}
