public class Pile{
    // Instance vars
    Card[] contents = new Card[52];
    boolean toInit;

    // Generic
    public int numSuitToNum(int n, int s){
        return n*4+s;//isn't it possible for two different cards to have the same ID?
        // no suit only goes up to 3
    }

    boolean is_in(Card c){
        for (int i = 0; i < 52; i++){
            if (this.contents[i].isEqual(c)) {
                return true;
            }
        }
        return false;
    }

    // Initializes pile to contain standard deck/empty deck
    private void init(boolean notblank){
        for (int i = 0; i < 13; i++){
            for (int j = 0; j < 4; j++){
                this.contents[numSuitToNum(i,j)] = new Card(13,4);
                if (notblank == true){
                    this.contents[numSuitToNum(i,j)] = new Card(i+1,j);
                }
            }
        }
    }

    public Pile(boolean toInit){
        init(toInit);//fills the deck with the default cards lets do gameRound now that's a class right? ya look at
            // the pseudocode going to the doc now
    }
     // lets make the new classes, i'll create the files
    // Getters
    boolean checkCard(int cardnum, int cardsuit){
        int idx = numSuitToNum(cardnum, cardsuit);
        if (this.contents[idx].get_number() == 0){
            return false;
        }
        return true;
    }

    Card[] getContents(){
        return this.contents;
    }

    //Setters
    void setCard(int index, Card cardtoset){
        this.contents[index] = cardtoset;
    }

    void sendToPile(int cno, Pile otherPile){
        otherPile.setCard(cno, this.contents[cno]);
        this.contents[cno] = new Card(14,4);
    }


}
