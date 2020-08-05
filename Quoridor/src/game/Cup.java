package game;
import java.util.*;

public class Cup {
    private ArrayList<Player> players = new ArrayList<Player>();
    private Integer skipper;
    private int turn = 0;

    public Cup(ArrayList<Player> players) {
        this.players = players;
    }

    public void nextLevel() {
        Collections.shuffle(players);
        if (players.size() % 2 != 0) {
            int i = 0;
            for (i = 0; players.get(i).skip_level; ++i);
            players.get(i).skip_level = true;
            skipper = i;
        }
        else
            skipper = null;

        turn = 0;
    }

    public Player[] play() {
        Player[] gamers = new Player[2];

        if (turn == skipper)
            turn++;
        gamers[0] = players.get(turn++);
        gamers[0].setId('U');
        if (turn == skipper)
            turn++;
        gamers[1] = players.get(turn);
        gamers[1].setId('D');

        return new Player[] {gamers[0], gamers[1]};
    }

    public void won(int i) {
        if (i == 1) {
            if (turn - i == skipper)
                i++;
            players.remove(turn - i);
        }
        else if (i == 2)
            players.remove(turn);
        turn++;
    }

    public boolean levelFinished() { return turn == players.size(); }

    public Player finished() { return (players.size() == 1) ? players.get(0) : null; }
}
