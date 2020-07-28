package load;

import game.Board;
import graphics.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.*;

public class FileManager {
    public static boolean save(Board board) {
        Formatter savior;
        try {
            savior = new Formatter(System.getProperty("user.dir") +
                    "/load/" + Calendar.getInstance().getTime() + ".csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        savior.format("%s,%d\n", board.getPlayer1().getName(), board.getWall().getPlayer1_walls());
        savior.format("%s,%d\n", board.getPlayer2().getName(), board.getWall().getPlayer2_walls());
        int[][] matrix = board.getBoard();
        for (int[] row : matrix) {
            for (int cell : row)
                savior.format("%d,", cell);
            savior.format("\n");
        }

        savior.close();
        return true;
    }

    public static ArrayList <String[]> load() {
        ArrayList<String[]> info = new ArrayList<>();
        FilenameFilter csv_finder = (dir, name) -> name.toLowerCase().endsWith(".csv");

        File[] save_files = new File(System.getProperty("user.dir") + "/load").listFiles(csv_finder);
        for (File save_file : Objects.requireNonNull(save_files)) {
            String player1name = null, player2name = null;
            try {
                Scanner loader = new Scanner(save_file);
                player1name = loader.nextLine().split(",")[0];
                player2name = loader.nextLine().split(",")[0];
                loader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            info.add(new String[] {save_file.getName(), player1name, player2name});
        }

        return info;
    }

    public static boolean load(File game_data, Controller controller) {
        Scanner loader;
        try {
            loader = new Scanner(game_data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        String[] player1info = loader.nextLine().split(",");
        String[] player2info = loader.nextLine().split(",");
        Board board = new Board(player1info, player2info);

        for (int i = 0; i < 17; ++i) {
            String[] cells = loader.nextLine().split(",");
            for (int j = 0; j < 17; j++)
                if (!cells[j].equals("0"))
                    board.setCell(i, j, Integer.parseInt(cells[j]));
        }
        controller.setBoard(board);

        loader.close();
        return true;
    }
}