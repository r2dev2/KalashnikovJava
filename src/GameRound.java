import java.util.Scanner;
import java.util.Random;

public class GameRound{
  Player b;
  Player v;
  // Status is no gun, kalashnikov, golden kalashnikov
  Pile militaryGarbage;
  Pile shelf;
  Pile discard;
  Pile setupAssist;

  private boolean int_is_in(int x, int[] arr){
    for (int i = 0; i < arr.length; i++){
      if (x == arr[i]){
        return true;
      }
    }
    return false;
  }

  private boolean card_is_in(Card ca, Pile pi){
    Card[] cntnts = pi.getContents();
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

  private int[] randCards(int l){
    int[] cnos = new int[l];
    int c;
    for (int i = 0; i < l; i++){
      boolean success = false;
      while (success == false) {
        c = randRange(1, 51);
        if (int_is_in(c, cnos) == false){
          cnos[i] = c;
          success = true;
        }
      }
    }
    return cnos;
  }

  // ambiguous karthik, can u work on constructor, that is second challenging part of class okay
  public GameRound(Player b, Player v){
    this.b = b;
    this.v = v;

    this.militaryGarbage = new Pile(true);
    this.shelf = new Pile(false);
    this.discard = new Pile(false);
    this.setupAssist = new Pile(false);

    //debug discard pile
    /*Card[] cn = this.discard.getContents();
    for (int i = 0; i < 52; i++){
      cn[i].disp();
    }*/

    //initialize slav hands
    int[] idxs = randCards(8);
    for (int i = 0; i < 8; i++){
      int s = idxs[i] % 4;
      int n = (idxs[i] - s)/4;
      Card acqCard = new Card(n, s);
      Pile[] buffer = new Pile[2];
      if (i < 4){
        buffer = this.b.receive_card(this.militaryGarbage, this.setupAssist, acqCard, i);
      }else{
        buffer = this.v.receive_card(this.militaryGarbage, this.setupAssist, acqCard, i-4);
      }
      this.militaryGarbage = buffer[0]; this.setupAssist = buffer[1];
    }
    // test case to see if golden kalashnikov works
    /*Card acqCard = new Card(3,1);
    this.b.receive_card(this.militaryGarbage, this.setupAssist, acqCard, 0);
    acqCard = new Card(0,1);
    this.b.receive_card(this.militaryGarbage, this.setupAssist, acqCard, 1);
    acqCard = new Card(6,1);
    this.b.receive_card(this.militaryGarbage, this.setupAssist, acqCard, 2);
    acqCard = new Card(12,1);
    this.b.receive_card(this.militaryGarbage, this.setupAssist, acqCard, 3);*/
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

  private int damageDealt(int idx, Card[] shand, boolean isKalashnikov){
    int[] snarray = new int[4];
    int damage = 0;
    for (int i = 0; i < 4; i++){
      snarray[i] = shand[i].get_number();
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
  int[] executeRound(Player player1, Player player2){
    int g = getGun(player1);
    boolean cont = true;
    Scanner scan = new Scanner(System.in);
    Card[] hand1 = player1.get_hand();
    Card[] hand2 = player2.get_hand();
    int[] toReturn = new int[2];
    //kalashnikov and golden kalashnikov
    if (g == 1 && cont == true){
      System.out.println("You have Kalashnikov, would you like to fire?(y/n) ");
      char c = scan.next().charAt(0);
      if (c == 'c'){
        System.out.println("Which card index do you pick?(int from 0-3) ");
        int indx = scan.nextInt();
        int damage = damageDealt(indx, hand2, false);
        toReturn[0] = player2.get_role_code(); toReturn[1] = damage;
        return toReturn;
      }
    }
    if (g == 2 && cont == true){
      System.out.println("You have Golden Kalashnikov, would you like to fire?(y/n) ");
      char c = scan.next().charAt(0);
      if (c == 'y'){
          toReturn[0] = player2.get_role_code(); toReturn[1] = 8;
          System.out.println(8);
          return toReturn;
      }
    }
    //player1 draws card
    Card drawnCard;
    printHand(hand1);
    //GUI stuff
    for (int i=0; i<hand1.length; i++) {
        mainController.update_card(hand1[i], i);
    }


    System.out.println("Which card would you like to replace?(int from 0 to 3) "); int rcno = scan.nextInt();
    System.out.println("Which pile should the card go to?(d)iscard/(s)helf "); String tpile = scan.next();
    System.out.println("Which pile do you want to draw from?(m)ilitary/(s)helf "); String opile = scan.next();
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
        newPvalues = player1.receive_card(this.militaryGarbage, this.discard, drawnCard, rcno);
        this.militaryGarbage = newPvalues[0]; this.discard = newPvalues[1];
      }else{
        newPvalues = player1.receive_card(this.shelf, this.discard, drawnCard, rcno);
        this.shelf = newPvalues[0]; this.discard = newPvalues[1];}
    }else{
      if (m == true){
        newPvalues = player1.receive_card(this.militaryGarbage, this.shelf, drawnCard, rcno);
        this.militaryGarbage = newPvalues[0]; this.shelf = newPvalues[1];
      }else{
        newPvalues = player1.receive_card(this.shelf, this.shelf, drawnCard, rcno);
        int n = hand1[rcno].get_number(); int s = hand1[rcno].get_suit();
        Card rcard = new Card(n,s);
        this.shelf = newPvalues[0]; this.shelf.setCard(n*4+s, rcard);}
    }
    for (int i = 0; i < 1000; i++){
      System.out.println("\n");
    }
    executeRound(player2, player1);
    return blankjaja;
  }


}
