import com.smalko.scoreboard.match.model.entity.Matches;
import com.smalko.scoreboard.player.model.entity.Players;
import com.smalko.scoreboard.util.HibernateUtil;
import org.junit.jupiter.api.Test;

public class HibernateTest {

    @Test
    void createPlayers() {
        try (var sessionFactory = HibernateUtil.getSession();
             var session = sessionFactory.openSession()) {
            session.getTransaction();
            var players = Players.builder()
                    .name("Venia")
                    .build();
            session.merge(players);

            session.beginTransaction().commit();
        }
    }

    @Test
    void createMatches() {
        try (var sessionFactory = HibernateUtil.getSession();
             var session = sessionFactory.openSession()) {

            session.beginTransaction();

            var playerOne = session.get(Players.class, 1);
            var playerTwo = session.get(Players.class, 2);

            var matches = Matches.builder()
                    .playersOneId(playerOne)
                    .playersTwoId(playerTwo)
                    .winner(playerOne)
                    .build();

            session.merge(matches);

            session.getTransaction().getStatus();
        }
    }


    @Test
    void get() {
        try (var sessionFactory = HibernateUtil.getSession();
             var session = sessionFactory.openSession()) {
            session.getTransaction();

            var matches = session.get(Matches.class, 1);

            System.out.println(matches);

            session.beginTransaction().commit();
        }
    }
}
