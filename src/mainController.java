import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class mainController {
    private GameRound round;
    private Player boris;
    private Player vadim;
    @FXML
    private static Label health;
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

    private static Label[] cards = {card1, card2, card3}; //make array so you can access them by index

    private void update_health(int new_health) {
        health.setText(String.valueOf(new_health)); //displays updated health
    }

    public static void update_card(Card card, int card_position) {
        //takes a card, and the position of the card to replace
        cards[card_position].setText(card.toString());
    }

    @FXML
    public void initialize() {
        Player boris = new Player("Boris", 20);
        Player vadim = new Player("Vadim", 20);
        GameRound round = new GameRound(boris, vadim);
    }
    @FXML
    protected void deal_hand(ActionEvent event) {
        int[] pdamage = round.executeRound();
        if (pdamage[0] == 1) {
            boris.takeDamage(pdamage[1]);
            health.setText(String.valueOf(boris.get_health()));
        } else {
            vadim.takeDamage(pdamage[1]);

        }
        if (boris.get_health() <= 0) {
            messageBox.setText("Vadim Blyat has won.");

        }
        if (vadim.get_health() <= 0) {
            messageBox.setText("The slav king has won.");

        }
        messageBox.setText("Round finished.");
    }
}

