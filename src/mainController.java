import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class mainController {
    private GameRound round;
    private Player boris;
    private Player vadim;
    private int turn=0; //tells you whose turn it is
    @FXML
    private Label health;
    @FXML
    private Label enemyHealth;
    @FXML
    private Label card1;
    @FXML
    private Label card2;
    @FXML
    private Label card3;
    @FXML
    private Label card4;
    @FXML
    private TextField inputBox;
    @FXML
    private static Label messageBox;
    @FXML
    private static Button deal_button;
    @FXML
    private static Button end_turn;

    private Label[] cards;

    private String inputtext;

    private void update_health(int new_health) {
        health.setText(String.valueOf(new_health)); //displays updated health
    }

    public void update_hand() {
        //takes a card, and the position of the card to replace
        Player currPlayer = round.playerlist[turn];
        Card[] hand = currPlayer.get_hand();
        for (int i=0;i<hand.length;i++) {
            cards[i].setText(hand[i].toString());
        }

    }
    public static void update_message(String message) {
        messageBox.setText(message);
    }

    @FXML
    public void initialize() {
        Player boris = new Player("Boris", 20);
        Player vadim = new Player("Vadim", 20);
        round = new GameRound(boris, vadim);
        cards = new Label[]{card1, card2, card3, card4}; //make array so you can access them by index
    }
    @FXML
    //checks if enter key is pressed, if so get stuff from textbox
    protected void enterCheck(KeyEvent ke) {
        if (ke.getCode()==KeyCode.ENTER) {
            inputtext = inputBox.getText();
        }
    }
    @FXML
    protected void deal_hand(ActionEvent event) {
        update_hand();
        int[] pdamage = round.executeRound(turn);
        deal_damage(pdamage);
        turn=(turn+1)%2; //inverts turn, if 0 becomes 1, 1 to 0, changes turn
    }
    private void deal_damage(int[] pdamage) {
        if (pdamage[0] == 0) {
            boris.takeDamage(pdamage[1]);
            health.setText(String.valueOf(boris.get_health()));
        } else {
            vadim.takeDamage(pdamage[1]);
            enemyHealth.setText(String.valueOf(vadim.get_health()));
        }
        if (boris.get_health() <= 0) {
            messageBox.setText("Vadim Blyat has won.");

        }
        if (vadim.get_health() <= 0) {
            messageBox.setText("The slav king has won.");

        }
    }
    @FXML
    protected void endTurn(ActionEvent event) {
        for (int i=0; i<cards.length; i++) {
            cards[i].setText("");
        }

    }
}

