import javax.swing.*;
import java.awt.*;
import java.util.Objects;

//this class is used for creating 2 types of buttons
public class GameButton extends JButton {

    //constructor creates button with required name and assign action listener to it
    public GameButton(String name) {

        setName("Button" + name);
        //depending on the passed obj we define different color and bound
        //player case
        if (name.equals("Player1") || name.equals("Player2")) {
            setText("Human");
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
            setFont(new Font("Arial", Font.BOLD, 12));
        }//start button case
        else if (name.equals("StartReset")) {
            setText("Start");
            setBackground(Color.BLACK);
            setForeground(Color.WHITE);
            setFont(new Font("Arial", Font.BOLD, 12));
        }//play button case
        else {
            setBorder(new RoundedBorder(20));
            setBackground(Color.YELLOW);
            setForeground(Color.BLACK);
            setText(" ");
            setFont(new Font("Arial", Font.BOLD, 40));
        }


        addActionListener(e -> {
            //if we pass 1 of 3 "control buttons" calls the getToolbarButton method to set up the game
            if (name.equals("Player1") || name.equals("Player2") || name.equals("StartReset"))
                setText(GameEngine.getToolbarButton(this));
            //if game not finished calls getButton method to set up the turn(X || O) and adds answer to tj
            if (GameEngine.gameNotFinished()) {
                if (Objects.equals(getText(), " ")) {
                    switch (name) {
                        case "A3" -> GameEngine.getButton(0, 0, this);
                        case "A2" -> GameEngine.getButton(1, 0, this);
                        case "A1" -> GameEngine.getButton(2, 0, this);
                        case "B3" -> GameEngine.getButton(0, 1, this);
                        case "B2" -> GameEngine.getButton(1, 1, this);
                        case "B1" -> GameEngine.getButton(2, 1, this);
                        case "C3" -> GameEngine.getButton(0, 2, this);
                        case "C2" -> GameEngine.getButton(1, 2, this);
                        case "C1" -> GameEngine.getButton(2, 2, this);
                    }
                    GameEngine.gameLoop();
                }
            }
        });
    }
}
