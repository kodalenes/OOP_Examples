import java.util.Scanner;

public class RockPaperScissors{

    public static void main()
    {
        Scanner sc = new Scanner(System.in);
        Gamer gamer1 = new Gamer("Enes");
        Gamer gamer2 = new Gamer("Omer");

        Arena arena = new Arena();

        arena.addGamers(gamer1 ,gamer2 , sc);
        arena.startGame();

    }
}

