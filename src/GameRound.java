import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Random;


public class GameRound {
    private Player boris;
    private Player vadim;
    private Pile militaryGarbage;
    private Pile shelf;
    private Pile discard;
    private Pile setupAssist;
    public Player[] playerlist;
    private int turn=0; //tells you whose turn it is
    private int currStage=0; //tells you which stage program is at
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
    private Label messageBox;
    @FXML
    private Button deal_button;
    @FXML
    private Button end_turn;

    private Label[] cards;

    private String inputtext;

    private Card toDiscard;

    private void update_health(int new_health) {
        health.setText(String.valueOf(new_health)); //displays updated health
    }


    public void update_message(String message) {
        messageBox.setText(message);
    }

    @FXML
    public void initialize() {
        //Initialize Players
        boris = new Player("Boris", 20);
        vadim = new Player("Vadim", 20);
        //Initialize decks
        this.militaryGarbage = new Pile(true);
        this.shelf = new Pile(false);
        this.discard = new Pile(false);
        this.setupAssist = new Pile(false);
        int[] idxs = randCards(8);
        for (int i = 0; i < 8; i++){
            int s = idxs[i] % 4;
            int n = (idxs[i] - s)/4;
            Card acqCard = new Card(n, s);
            Pile[] buffer;
            if (i < 4){
                buffer = boris.receive_card(this.militaryGarbage, this.setupAssist, acqCard, i);
            }else{
                buffer = vadim.receive_card(this.militaryGarbage, this.setupAssist, acqCard, i-4);
            }
            this.militaryGarbage = buffer[0]; this.setupAssist = buffer[1];
        }
        cards = new Label[]{card1, card2, card3, card4}; //make array so you can access them by index
        playerlist = new Player[]{boris, vadim};
        executeRound();
    }

    /*@FXML
    protected void deal_hand(ActionEvent event) {
        update_hand();
        int[] pdamage = executeRound(turn);
        deal_damage(pdamage);
        turn=(turn+1)%2; //inverts turn, if 0 becomes 1, 1 to 0, changes turn
    }*/
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
        if (end_turn.getText().equals("Start turn")) {
            Card[] currHand = playerlist[turn].get_hand();
            for (int i=0; i<cards.length; i++) {
                cards[i].setText(currHand[i].toString());
            }
            end_turn.setText("End turn");
            executeRound();
        }
        else {
            for (int i = 0; i < cards.length; i++) {
                cards[i].setText("");
            }
            turn = (turn + 1) % 2;
            currStage=0;
            end_turn.setText("Start turn");
            update_message("Waiting for Player" + turn);
        }
    }


    private boolean int_is_in(int x, int[] arr){
        for (int i = 0; i < arr.length; i++){
            if (x == arr[i]){
                return true;
            }
        }
        return false;
    }

/*    private boolean card_is_in(Card ca, Pile pile){
        Card[] cntnts = pile.getContents();
        for (int i = 0; i < 52; i++){
            if (cntnts[i].isEqual(ca)){
                return true;
            }
        }
        return false;
    }*/

    private static int randRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private Card randCardFromPile(Pile pile){;
/*        boolean success = false;
        int cno;
        Card tester = new Card(13,4);
        while (success == false) {
            cno = randRange(0,51)+4;
            int s = cno % 4;
            int n = (cno-s)/4;
            tester = new Card(n,s);
            if (card_is_in(tester, pile) == false){
                return tester;
            }
        }
        return tester;*/
        int idx = randRange(0, pile.length());
        return pile.pop(idx);
    }

    private int[] randCards(int n){
        //Generates n random numbers
        int[] ints = new int[n];
        for (int i=0; i<n; i++) {
            int c=randRange(1,51);
            ints[i]=c;
        }
        return ints;
    }






    private boolean isValidCard(int cnum, int[] possibleCards){
        for(int i = 0; i < 4; i++){
            if(cnum == possibleCards[i]){
                return true;
            }
        }
        return false;
    }

    private int[] removeCard(int cnum, int[] carray){
        for(int i = 0; i < 4; i++){
            if(carray[i] == cnum){
                carray[i] = 20;
            }
        }
        return carray;
    }

    private boolean isKalashnikov(Card[] shand){
        int sum = 0;
        for (int i = 0; i < 4; i++){
            sum += shand[i].get_suit();
        }
        if (sum % 4 == 0){
            return true;
        }
        return false;
    }

    // returns 0 if none, 1 if kalashnikov, 2 if golden kalashnikov
    private int getGun(Player slav){
        int[] cardNums = {0, 12, 3, 6};
        Card[] slavHand = new Card[4];
        slavHand = slav.get_hand();
        int gunpoints = 0;
        for (int i = 0; i < 4; i++){
            if (isValidCard(slavHand[i].get_number(), cardNums) == true){
                cardNums = removeCard(slavHand[i].get_number(), cardNums);
                gunpoints++;
            }
        }
        if (gunpoints == 4){
            if (isKalashnikov(slavHand) == true){
                return 2;
            }
            return 1;
        }
        return 0;
    }

    private int damageDealt(int idx, Card[] hand, boolean isKalashnikov){
        int[] snarray = new int[4];
        int damage = 0;
        for (int i = 0; i < 4; i++){
            snarray[i] = hand[i].get_number();
            if (snarray[i] == 0){
                snarray[i] = 20;
            }
        }
        for (int i = 0; i < 4; i++){
            if (snarray[i] <= snarray[idx]){
                damage++;
            }
        }
        if (isKalashnikov == true){
            damage *= 2;
        }
        return damage;
    }

    /*private int getPlayerNo(Player p){
      if (p.role == "Boris"){
        return 1;
      }
      return 2;
    }

    private void printHand(Card[] shand){
      for (int i = 0; i < 4; i++){
        shand[i].disp();
      }
    }*/
    String tpile;
    @FXML
    //checks if enter key is pressed, if so get stuff from textbox
    protected void enterCheck(KeyEvent ke) {
        System.out.println(currStage);
        if (ke.getCode()==KeyCode.ENTER) {
            inputtext = inputBox.getText();
            switch (currStage) { //checks if value of currStage == int value, better than a lot of if else
                case 0:
                    update_message("Which card index do you pick?(0-3)");
                case 1:
                    //Kalashnikov
                    Integer idx = Integer.valueOf(inputtext);
                    executeGunLogic(idx);
                case 2:
                    //Golden Kalashnikov
                    Player otherPlayer = playerlist[(turn+1)%2];
                    if (inputtext=="y") {
                        int[] pdamage = new int[2];
                        pdamage[0] = otherPlayer.get_player_id(); pdamage[1] = 8;
                        deal_damage(pdamage);
                    }
                case 3:
                    Player currPlayer = playerlist[turn];
                    int rcno=Integer.valueOf(inputtext);
                    toDiscard = currPlayer.get_hand().pop(rcno);
                    update_message("Which pile should the card go to?(d)iscard/(s)helf");
                    break;
                case 4:
                    tpile = inputtext;
                    if (this.shelf.contents.length!=0) {
                        update_message("Which pile do you want to draw from(m)ilitary/(s)helf");
                    }
                    break;
                case 5:
                    Card drawnCard;
                    String opile = inputtext;
                    if (opile.equals("military") | opile.equals("m")) {
                        drawnCard = randCardFromPile(this.militaryGarbage);
                        
                    } else {
                        try {
                            drawnCard = randCardFromPile(this.shelf);
                        }
                        catch (NullPointerException e) {
                            update_message("There is nothing in the shelf");
                        }
                    }
            }
            currStage+=1;
        }
    }
    private void executeGunLogic(Integer idx) { //idx param needed to calc damage
        Player currPlayer = playerlist[turn];
        Player otherPlayer = playerlist[(turn+1)%2];
        Card[] hand2 = otherPlayer.get_hand(); //other player hand
        if (idx==null) {
            int gun = getGun(currPlayer);;
            boolean cont = true;
            if (gun == 1 && cont == true) {
                update_message("You have Kalashnikov, would you like to fire?(y/n) ");
            }
            else if (gun == 2 && cont == true) {
                update_message("You have Golden Kalashnikov, would you like to fire?(y/n) ");
            }
            else{
                currStage+=2; //skip Kalashnikov if no gun
            }
        }
        else {
            int[] toReturn = new int[2];
            int damage = damageDealt(idx, hand2, false);
            toReturn[0] = otherPlayer.get_player_id(); toReturn[1] = damage;
            deal_damage(toReturn);
        }
    }
    public void update_hand() {
        //takes a card, and the position of the card to replace
        Player currPlayer = playerlist[turn];
        Card[] hand = currPlayer.get_hand();
        if (currStage==2) {
            update_message("Which card would you like to replace(range 0-3)");
        }
        for (int i=0;i<hand.length;i++) {
            cards[i].setText(hand[i].toString());
        }
        currStage+=1;
    }
    // returns role code, damage taken of person of role code
    void executeRound(){
        Player currPlayer = playerlist[turn];
        Card[] hand1 = currPlayer.get_hand();;



        //kalashnikov and golden kalashnikov
        executeGunLogic(null); //pass in null the first time
        //player1 draws card
        Card drawnCard;
        update_hand();
    }
}

