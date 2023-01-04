import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class TicTacToe extends JFrame {

    List<JMenuItem> jMenuItems = new ArrayList<>();
    public TicTacToe() {
        //jframe init
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setResizable(false);
        setSize(500, 500);

        //setting up a menu with items(select mode buttons)
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem humanHuman = new JMenuItem("Human vs Human");
        jMenuItems.add(humanHuman);
        JMenuItem humanRobot = new JMenuItem("Human vs Robot");
        jMenuItems.add(humanRobot);
        JMenuItem robotHuman = new JMenuItem("Robot vs Human");
        jMenuItems.add(robotHuman);
        JMenuItem robotRobot = new JMenuItem("Robot vs Robot");
        jMenuItems.add(robotRobot);
        JMenuItem exit = new JMenuItem("Exit");
        jMenuItems.add(exit);

        //adding items to menuBar
        gameMenu.add(humanHuman);
        gameMenu.add(humanRobot);
        gameMenu.add(robotHuman);
        gameMenu.add(robotRobot);
        gameMenu.addSeparator(); //separator for exit button
        gameMenu.add(exit);

        //adding menuBar to frame
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);

        //for testing in jetbrains academy
        gameMenu.setName("MenuGame");
        humanHuman.setName("MenuHumanHuman");
        humanRobot.setName("MenuHumanRobot");
        robotHuman.setName("MenuRobotHuman");
        robotRobot.setName("MenuRobotRobot");
        exit.setName("MenuExit");



        //action listeners for each JMenuItem
        jMenuItems.forEach(jMenuItem -> jMenuItem.addActionListener(e -> {
            switch (jMenuItem.getText()){
                case "Exit" -> System.exit(0);
                case "Human vs Human" -> {
                    GameEngine.setGameFromMenu("Human", "Human");
                }
                case "Human vs Robot" -> {
                    GameEngine.setGameFromMenu("Human", "Robot");
                }
                case "Robot vs Human" -> {
                    GameEngine.setGameFromMenu("Robot", "Human");
                }
                case "Robot vs Robot" -> {
                    GameEngine.setGameFromMenu("Robot", "Robot");
                }
            }
        }));

        //adding board(buttons, jlabel) to jframe
        Component board = new Board();
        add(board);
        setVisible(true);
    }
}