public class Arena {
    //Attributes
    private Gamer g1;
    private Gamer g2;
    private static boolean isGameOver;

    public void addGamers(Gamer g1 , Gamer g2)
    {
        this.g1 = g1;
        this.g2 = g2;
    }

    public void startGame()
    {
        while (!isGameOver)
        {
            g1.move();
            g2.move();
            StatMaker.proccesMoves(g1,g2);
            StatMaker.displayScores(g1,g2);
        }
    }

    public Gamer getG1() {
        return g1;
    }

    public Gamer getG2() {
        return g2;
    }

    public static void setIsGameOver(boolean isGameOver) {
        Arena.isGameOver = isGameOver;
    }

    public static boolean getIsGameOver()
    {
        return isGameOver;
    }
}
