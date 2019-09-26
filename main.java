class main{
    public static void main(String args[]) {
        Player boris = new Player("Boris", 20); 
        Player vadim = new Player("Vadim", 20);

        while (1==1) {
            GameRound round = new GameRound(boris, vadim);
            int[] pdamage = round.executeRound(boris, vadim);
            if (pdamage[0] == 1) {
                boris.takeDamage(pdamage[1]);
            }else{
                vadim.takeDamage(pdamage[1]);
            }
            if (boris.get_health() <= 0) {
                System.out.println("Vadim Blyat has won."); break;
            }
            if (vadim.get_health() <= 0) {
                System.out.println("The slav king has won."); break;
            }
            System.out.println("Round finished.");
        }
    }
}