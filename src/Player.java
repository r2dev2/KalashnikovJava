public class Player{
    private int health;
    private Card[] hand = new Card[4];
    // Role can either be Boris or Vadim.
    String role;

    public Player(String role, int health){
        this.role = role;
        this.health = health;
    }

    // getters
    int get_health(){
        return this.health;
    }

    Card[] get_hand(){
        return this.hand;
    }

    String get_role(){
        return this.role;
    }

    // 0 if by Boris, 1 if by Vadim Blyat
    int get_player_id(){
        if (this.role == "Boris"){
            return 0;
        }else{
            return 1;
        }
    }

    // setters
    // returns ogpile, then targetpile
    Pile[] receive_card(Pile pileOrigin, Pile pileTarget, Card acquiredCard, int location){
        int idx = acquiredCard.numSuitToNum();
        //Card blankCard = new Card(13,4);
        pileOrigin.setCard(idx, null);
        if (this.hand[location]!=null){
            pileTarget.setCard(idx, this.hand[location]);
        }
        this.hand[location] = acquiredCard;
        Pile[] returnPiles = new Pile[2];
        returnPiles[0] = pileOrigin; returnPiles[1] = pileTarget;
        return returnPiles;
    }

    void takeDamage(int amountDamage){
        this.health -= amountDamage;
    }
}
