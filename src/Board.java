import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Board extends JPanel {

    String[] toolbarButtonsNames = {"Player1", "StartReset", "Player2"};
    String[] cellNames = {"A3", "B3", "C3", "A2", "B2", "C2", "A1", "B1", "C1"};
    public Board() {
        setLayout(new BorderLayout());

        //top toolbar with 3 selection buttons
        JPanel toolbarPanel = new JPanel();
        toolbarPanel.setLayout(new GridLayout(1, 3,10,0));
        toolbarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Arrays.stream(toolbarButtonsNames).forEach(name -> toolbarPanel.add(new GameButton(name)));
        add(toolbarPanel, BorderLayout.NORTH);

        //jpanel with 9 game buttons
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new GridLayout(3,3,5,5));

        Arrays.stream(cellNames).forEach(name -> fieldPanel.add(new GameButton(name)));
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        add(fieldPanel, BorderLayout.CENTER);

        //game status display field
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BorderLayout());

        JLabel labelStatus = new JLabel();
        labelStatus.setText("Game is not started");
        labelStatus.setName("LabelStatus");
        labelStatus.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        statusPanel.add(labelStatus, BorderLayout.WEST);

        add(statusPanel, BorderLayout.SOUTH);

        //components in game field passed to the GameEngine class where all the logic exists
        Component[] fieldComponents = fieldPanel.getComponents();
        Component[] toolBarComponents = toolbarPanel.getComponents();
        Component statusComponent = statusPanel.getComponent(0);

        GameEngine gameEngine = new GameEngine(fieldComponents, toolBarComponents, statusComponent);
        gameEngine.setTable();
    }
}