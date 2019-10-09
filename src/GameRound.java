import javafx.beans.property.StringProperty;
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
        Player currPlayer = playerlist[turn];
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
        int[] pdamage = executeRound(turn);
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


  private boolean int_is_in(int x, int[] arr){
    for (int i = 0; i < arr.length; i++){
      if (x == arr[i]){
        return true;
      }
    }
    return false;
  }

  private boolean card_is_in(Card ca, Pile pile){
    Card[] cntnts = pile.getContents();
    for (int i = 0; i < 52; i++){
      if (cntnts[i].isEqual(ca)){
        return true;
      }
    }
    return false;
  }

	private static int randRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

  private Card randCardFromPile(Pile pi){;
    boolean success = false;
    int cno;
    Card tester = new Card(13,4);
    while (success == false) {
      cno = randRange(0,51)+4;
      int s = cno % 4;
      int n = (cno-s)/4;
      tester = new Card(n,s);
      if (card_is_in(tester, pi) == false){
        return tester;
      }
    }
    return tester;
  }

  private int[] randCards(int n){
    //Generates n random numbers

    /*int[] cnos = new int[n];
    int c;
    for (int i = 0; i < n; i++){
      boolean success = false;
      while (success == false) {
        c = randRange(1, 51);
        if (int_is_in(c, cnos) == false){
          cnos[i] = c;
          success = true;
        }
      }
    }
    return cnos;*/
    int[] ints = new int[n];
    for (int i=0; i<n; i++) {
      int c=randRange(1,51);
      ints[i]=c;
    }
    return ints;
  }

  // ambiguous karthik, can u work on constructor, that is second challenging part of class okay
    //Initialize Players


    //Initialize decks

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

  private int getPlayerNo(Player p){
    if (p.role == "Boris"){
      return 1;
    }
    return 2;
  }

  private void printHand(Card[] shand){
    for (int i = 0; i < 4; i++){
      shand[i].disp();
    }
  }

  // returns role code, damage taken of person of role code
  int[] executeRound(int turn){
    //System.out.println(playerlist[turn]);
    Player currPlayer = playerlist[turn];
    Player otherPlayer = playerlist[(turn+1)%2];
    int gun = getGun(currPlayer);
    boolean cont = true;
    Card[] hand1 = playerlist[turn].get_hand();;
    Card[] hand2 = playerlist[(turn+1)%2].get_hand(); //other player hand
    int[] toReturn = new int[2];

    //kalashnikov and golden kalashnikov
    if (gun == 1 && cont == true){
      update_message("You have Kalashnikov, would you like to fire?(y/n) ");
      //get the input into string c IMPORTANT
      if (c == 'y'){
        update_message("Which card index do you pick?(int from 0-3) ");
        //int indx = scan.nextInt();
        int damage = damageDealt(indx, hand2, false);
        toReturn[0] = otherPlayer.get_player_id(); toReturn[1] = damage;
        return toReturn;
      }
    }
    if (gun == 2 && cont == true){
      update_message("You have Golden Kalashnikov, would you like to fire?(y/n) ");
      //char c = scan.next().charAt(0);
      if (c == 'y'){
          toReturn[0] = otherPlayer.get_player_id(); toReturn[1] = 8;
          System.out.println(8);
          return toReturn;
      }
    }
    //player1 draws card
    Card drawnCard;
    printHand(hand1);

    update_message("Which card would you like to replace?(int from 0 to 3) "); //int rcno = scan.nextInt();
    update_message("Which pile should the card go to?(d)iscard/(s)helf "); //String tpile = scan.next();
    update_message("Which pile do you want to draw from?(m)ilitary/(s)helf "); //String opile = scan.next();
    boolean m;
    int[] blankjaja = {1,2};
    Pile[] newPvalues;
    if (opile == "military" || opile == "m"){
      drawnCard = randCardFromPile(this.militaryGarbage); m = true;
    }else{
      drawnCard = randCardFromPile(this.shelf); m = false;
    }
    if (tpile == "discard" || tpile == "d"){
      if (m == true){
        newPvalues = currPlayer.receive_card(this.militaryGarbage, this.discard, drawnCard, rcno);
        this.militaryGarbage = newPvalues[0]; this.discard = newPvalues[1];
      }else{
        newPvalues = currPlayer.receive_card(this.shelf, this.discard, drawnCard, rcno);
        this.shelf = newPvalues[0]; this.discard = newPvalues[1];}
    }else{
      if (m == true){
        newPvalues = currPlayer.receive_card(this.militaryGarbage, this.shelf, drawnCard, rcno);
        this.militaryGarbage = newPvalues[0]; this.shelf = newPvalues[1];
      }else{
        newPvalues = currPlayer.receive_card(this.shelf, this.shelf, drawnCard, rcno);
        int n = hand1[rcno].get_number(); int s = hand1[rcno].get_suit();
        Card rcard = new Card(n,s);
        this.shelf = newPvalues[0]; this.shelf.setCard(n*4+s, rcard);}
    }
    for (int i = 0; i < 1000; i++){
      System.out.println("\n");
    }

    return blankjaja;
  }
}

