import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class mainController {
    @FXML
    private static Label health;
    @FXML
    private static Label card1;
    @FXML
    private static Label card2;
    @FXML
    private static Label card3;

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

        while (1 == 1) {
            GameRound round = new GameRound(boris, vadim);
            int[] pdamage = round.executeRound(boris, vadim);
            if (pdamage[0] == 1) {
                boris.takeDamage(pdamage[1]);
            } else {
                vadim.takeDamage(pdamage[1]);
            }
            if (boris.get_health() <= 0) {
                System.out.println("Vadim Blyat has won.");
                break;
            }
            if (vadim.get_health() <= 0) {
                System.out.println("The slav king has won.");
                break;
            }
            System.out.println("Round finished.");
        }
    }

}
