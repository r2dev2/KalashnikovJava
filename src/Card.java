// If card is declared Card(0,0), card is blank
public class Card{
    int number;
    // Clubs, spades, heart, diamond, start from 0
    int suit;
    String[] suitNames = {"Clubs", "Spades", "Hearts", "Diamonds", "Blyat"};
    boolean isblank;

    public Card(int number, int suit){
        this.number = number;
        this.suit = suit;
    }

    // Various
    boolean isEqual(Card card2){
        if (card2.get_number() == this.number && card2.get_suit() == this.suit){
            return true;
        }
        return false;
    }
    @Override //Overrides default string representation
    public String toString() {
        return String.valueOf(this.number)+"of"+this.suit;
    }
    void disp(){
        String[] royalty = {"Jack", "Queen", "King"};
        if (this.number > 0 && this.number <= 9){
            System.out.print(this.number+1);
        }else{if(this.number == 0){
            System.out.print("Ace");
        }else{if(this.number == 13){
            System.out.println("Vadim Blyat");
        }else{
            System.out.print(royalty[this.number-10]);
        }}}
        if(this.number != 0 || this.number != 13){
            System.out.println(" "+suitNames[this.suit]);
        }
    }

    // Getters
    int get_number(){
        return this.number;
    }

    int get_suit(){
        return this.suit;
    }

    String get_name(int cno){
        return suitNames[cno];
    }

    boolean is_blank(){
        return isblank;
    }

    //Setters
    void set_number(int num){
        this.number = num;
    }

    void set_blank(boolean b){
        this.isblank = b;
    }

    void set_suit(int st){
        this.suit = st;
    }
}