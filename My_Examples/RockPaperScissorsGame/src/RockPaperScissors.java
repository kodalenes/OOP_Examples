public class RockPaperScissors{

    public static void main()
    {
        Gamer gamer1 = new Gamer("Enes");
        Gamer gamer2 = new Gamer("Omer");

        Arena arena = new Arena();

        arena.addGamers(gamer1 ,gamer2);
        arena.startGame();

    }
}

