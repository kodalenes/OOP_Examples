public class StatMaker {

    static void proccesMoves(Gamer g1, Gamer g2)
    {
        if ((g1.getSign() == null) || (g2.getSign() == null) || Arena.getIsGameOver()) return;
        else if (g1.getSign() == g2.getSign())
            Gamer.setNumOfDraw(Gamer.getNumOfDraw() + 1);
        else if ((g1.getSign() == HandSign.PAPER) && (g2.getSign() == HandSign.ROCK))
            g1.incrementNumOfWin();
        else if ((g1.getSign() == HandSign.SCISSOR) && (g2.getSign() == HandSign.PAPER))
            g1.incrementNumOfWin();
        else if ((g1.getSign() == HandSign.ROCK) && (g2.getSign() == HandSign.SCISSOR))
            g1.incrementNumOfWin();
        else
            g2.incrementNumOfWin();

        Gamer.setNumOfTrials(Gamer.getNumOfTrials() + 1);
    }

    static void displayScores(Gamer g1 , Gamer g2)
    {
        System.out.println(g1.getName() + " : Win count " + g1.getNumOfWin() + " : Trials " + (Gamer.getNumOfTrials() == 0 ? 0 : Gamer.getNumOfTrials()));
        System.out.println(g2.getName() + " : Win count " + g2.getNumOfWin() + " : Trials " + (Gamer.getNumOfTrials() == 0 ? 0 : Gamer.getNumOfTrials()));
        System.out.println("Num of draws " +  Gamer.getNumOfDraw());
    }
}
