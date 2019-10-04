import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
    private static Label card1;
    @FXML
    private static Label card2;
    @FXML
    private static Label card3;
    @FXML
    private static Label messageBox;
    @FXML
    private static Button deal_button;
    @FXML
    private static Button end_turn;

    private static Label[] cards = {card1, card2, card3}; //make array so you can access them by index

    private void update_health(int new_health) {
        health.setText(String.valueOf(new_health)); //displays updated health
    }

    public static void update_card(Card card, int card_position) {
        //takes a card, and the position of the card to replace
        cards[card_position].setText(card.toString());
    }
    public static void update_message(String message) {
        messageBox.setText(message);
    }

    @FXML
    public void initialize() {
        Player boris = new Player("Boris", 20);
        Player vadim = new Player("Vadim", 20);
        GameRound round = new GameRound(boris, vadim);
    }
    @FXML
    protected void deal_hand(ActionEvent event) {
        int[] pdamage = round.executeRound(turn);
        deal_damage(pdamage);
        turn=(turn+1)%2; //inverts turn, if 0 becomes 1, 1 to 0, changes turn


    }
    private void deal_damage(int[] pdamage) {
        if (pdamage[0] == 1) {
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

