import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class GameEngine {
    //table for game buttons where we set turn (X or O)
    private static final char[][] table = new char[3][3];
    //current mark
    private static char playerMark;
    //game status indicator
    static boolean gameStarted = false;
    //game buttons (9 cells to play Tic-Tac-Toe)
    static Component[] fieldComponents;
    //reset button, player1 and player2 select button
    static Component[] toolBarComponents;
    //status bar which turn is on etc.
    static Component statusComponent;

    public GameEngine(Component[] fieldButtons, Component[] toolBarComponents, Component statusComponent) {
        GameEngine.fieldComponents = fieldButtons;
        GameEngine.toolBarComponents = toolBarComponents;
        GameEngine.statusComponent = statusComponent;
    }


    //getter of the button for action listener
    public static void getButton(int i, int j, GameButton gameButton) {
        playerMark = (getPlayerMark()) ? 'O' : 'X';
        table[i][j] = playerMark;
        gameButton.setText(String.valueOf(playerMark));
    }

    //reset the game to default parameters
    public static void resetGame() {
        gameStarted = false;

        for (char[] chars : table) {
            Arrays.fill(chars, ' ');
        }

        for (Component button : fieldComponents) {
            ((GameButton) button).setText(" ");
        }

        ((JLabel) statusComponent).setText("Game is not started");
    }

    //this method called each turn to check whether the game is over, if over updates game status bar
    //return false if the game is still on
    public static boolean gameNotFinished() {
        //in case current turn finishes game
        if (win(playerMark)) {
            System.out.println(playerMark + " wins");
            ((JLabel) statusComponent).setText(playerMark == 'X' ?
                    String.format("The %s Player (%s) wins", ((JButton) toolBarComponents[0]).getText(), playerMark) :
                    String.format("The %s Player (%s) wins", ((JButton) toolBarComponents[2]).getText(), playerMark));
            return false;
        }
        //in case all game buttons busy and the winner is not present (draw)
        if (draw()) {
            ((JLabel) statusComponent).setText("Draw");
            return false;
        }

        return true;
    }

    //makeMove method calls robotMove if we call it while robot move
    private static void makeMove(String parameter) {
        if (Objects.equals(parameter, "Robot")) robotMove();
    }
    //robotMove method called when we need to immigrate real player move
    private static void robotMove() {
        Random random = new Random();
        //in this implementation in infinite cycle we generate two random numbers from 0 to 2
        while (true) {
            int firstCoordinate = random.nextInt(3);
            int secondCoordinate = random.nextInt(3);
            //when table with generated indexes !contains player mark, we assign this button to "robot" mark
            if (table[firstCoordinate][secondCoordinate] == ' ') {
                for (Component button : fieldComponents) {
                    switch (button.getName()) {
                        case "ButtonA3":
                            if (firstCoordinate == 0 && secondCoordinate == 0)
                                GameEngine.getButton(0, 0, (GameButton) button);
                            break;
                        case "ButtonA2":
                            if (firstCoordinate == 1 && secondCoordinate == 0)
                                GameEngine.getButton(1, 0, (GameButton) button);
                            break;
                        case "ButtonA1":
                            if (firstCoordinate == 2 && secondCoordinate == 0)
                                GameEngine.getButton(2, 0, (GameButton) button);
                            break;
                        case "ButtonB3":
                            if (firstCoordinate == 0 && secondCoordinate == 1)
                                GameEngine.getButton(0, 1, (GameButton) button);
                            break;
                        case "ButtonB2":
                            if (firstCoordinate == 1 && secondCoordinate == 1)
                                GameEngine.getButton(1, 1, (GameButton) button);
                            break;
                        case "ButtonB1":
                            if (firstCoordinate == 2 && secondCoordinate == 1)
                                GameEngine.getButton(2, 1, (GameButton) button);
                            break;
                        case "ButtonC3":
                            if (firstCoordinate == 0 && secondCoordinate == 2)
                                GameEngine.getButton(0, 2, (GameButton) button);
                            break;
                        case "ButtonC2":
                            if (firstCoordinate == 1 && secondCoordinate == 2)
                                GameEngine.getButton(1, 2, (GameButton) button);
                            break;
                        case "ButtonC1":
                            if (firstCoordinate == 2 && secondCoordinate == 2)
                                GameEngine.getButton(2, 2, (GameButton) button);
                            break;
                    }
                }
                //calling the gameLoop method to update the status bar with fresh turn
                gameLoop();
                break;
            }
        }
    }
    //method win called with last answer and check the condition of the
    private static boolean win(char mark) {
        boolean win = false;

        for (int i = 0; i < GameEngine.table.length; i++) {
            win = win || Arrays.equals(GameEngine.table[i], new char[]{mark, mark, mark}) || (GameEngine.table[0][i] == mark && GameEngine.table[1][i] == mark && GameEngine.table[2][i] == mark);
        }

        return win || (GameEngine.table[0][0] == mark && GameEngine.table[1][1] == mark && GameEngine.table[2][2] == mark) || (GameEngine.table[0][2] == mark && GameEngine.table[1][1] == mark && GameEngine.table[2][0] == mark);
    }

    //count cells with ' ' value as draw
    private static boolean draw() {
        int emptyPlaces = 0;

        for (char[] row : GameEngine.table) {
            for (char mark : row) {
                emptyPlaces += (mark == ' ') ? 1 : 0;
            }
        }

        return emptyPlaces == 0;
    }
    //getToolbarButton method manages all buttons
    public static String getToolbarButton(GameButton button) {

        switch (button.getName()) {
            case "ButtonPlayer1", "ButtonPlayer2" -> {
                return Objects.equals(button.getText(), "Robot") ? "Human" : "Robot";
            }
            case "ButtonStartReset" -> {
                if (Objects.equals(button.getText(), "Start")) {
                    lockButtons(toolBarComponents);
                    unlockButtons(fieldComponents);
                    gameLoop();
                    return "Reset";
                } else {
                    resetGame();
                    lockButtons(fieldComponents);
                    unlockButtons(toolBarComponents);
                    return "Start";
                }
            }
            default -> {
                return "";
            }
        }
    }
    //setGameFromMenu method "duplicates" player1 and player2 buttons and game Start
    //it provides similar
    public static void setGameFromMenu(String player1, String player2) {
        resetGame();

        for (Component button : toolBarComponents) {
            switch (button.getName()) {
                case "ButtonPlayer1" -> ((JButton) button).setText(player1);
                case "ButtonPlayer2" -> ((JButton) button).setText(player2);
                case "ButtonStartReset" -> {
                    lockButtons(toolBarComponents);
                    unlockButtons(fieldComponents);
                    gameLoop();
                    ((JButton) button).setText("Reset");
                }
            }
        }
    }
    //updating status bar (which turn is now)
    static void gameLoop() {
        gameStarted = true;

        if (gameNotFinished()) {
            playerMark = (getPlayerMark()) ? 'O' : 'X';

            if (playerMark == 'X') {
                String butPlayer1Text = ((JButton) toolBarComponents[0]).getText();
                ((JLabel) statusComponent).setText(String.format("The turn of %s Player (%s)",butPlayer1Text,playerMark));
                makeMove(((JButton) toolBarComponents[0]).getText());
            }
            if (playerMark == 'O') {
                String butPlayer2Text = ((JButton) toolBarComponents[2]).getText();
                ((JLabel) statusComponent).setText(String.format("The turn of %s Player (%s)",butPlayer2Text,playerMark));
                makeMove(((JButton) toolBarComponents[2]).getText());
            }
        }
    }
    //unlock all game filed buttons except StartReset button
    private static void unlockButtons(Component[] buttons) {
        for (Component button : buttons) {
            if (!Objects.equals(button.getName(), "ButtonStartReset")) {
                button.setEnabled(true);
            }
        }
    }
    //opposite to unlockButtons method
    private static void lockButtons(Component[] buttons) {
        for (Component button : buttons) {
            if (!Objects.equals(button.getName(), "ButtonStartReset")) {
                button.setEnabled(false);
            }
        }
    }
    //setTable method is called in Board class to update representation of players turns
    public void setTable() {
        //StringBuilder is preferable over string in this case
        StringBuilder fieldButtonsValues = new StringBuilder();
        for (Component button : fieldComponents) {
            fieldButtonsValues.append(((GameButton) button).getText());
            button.setEnabled(false);
        }

        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                table[i][j] = fieldButtonsValues.charAt(count);
                count++;
            }
        }
    }

    //when player makes a move set X or O count to +1
    private static boolean getPlayerMark() {
        int x = 0;
        int o = 0;

        for (char[] chars : table) {
            for (char aChar : chars) {
                if (aChar == 'X') x++;
                if (aChar == 'O') o++;
            }
        }

        return x > o;
    }
}
