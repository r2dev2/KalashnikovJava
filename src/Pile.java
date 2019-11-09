public class Pile{
    // Instance vars
    Card[] contents = new Card[52];
    boolean toInit;

    // Generic


    boolean is_in(Card c){
        for (int i = 0; i < 52; i++){
            if (this.contents[i].isEqual(c)) {
                return true;
            }
        }
        return false;
    }

    // Initializes pile to contain standard deck/empty deck
    private void init(boolean toInit){
        for (int i = 0; i < 13; i++){
            for (int j = 0; j < 4; j++){
                //this.contents[i*4+j] = new Card(13,4);

                //initialize if true
                //otherwise do nothing
                if (toInit == true){
                    this.contents[i*4+j] = new Card(i+1,j);
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
    boolean checkCard(Card cardtoCheck){
        int idx = cardtoCheck.numSuitToNum();
        if (this.contents[idx].get_number() == 0){
            return false;
        }
        return true;
    }

    Card[] getContents(){
        return this.contents;
    }

    int length() {return this.contents.length; }

    Card pop(int idx) {
        //removes and returns the card at that index
        return this.contents[idx];
    }

    //Setters
    void setCard(int index, Card cardtoset){
        this.contents[index] = cardtoset;
    }

/*    void sendToPile(int cno, Pile otherPile){
        otherPile.setCard(cno, this.contents[cno]);
        this.contents[cno] = new Card(14,4);
    }*/



}
