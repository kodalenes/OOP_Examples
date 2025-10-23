import java.util.Scanner;

public class Gamer {

    private String name;
    private HandSign sign;
    private int numOfWin;
    private static int numOfDraw;
    private static int numOfTrials;//Deneme sayisi

    Gamer(String name)
    {
        this.name = name;
    }

    void move(Scanner sc)
    {
        boolean isInputInvalid = true;
        System.out.println(this.name + " Enter p:paper , s:scissor , r:rock or q:quit");
        do {
            char input = sc.next().toLowerCase().charAt(0);

            switch (input)
            {
                case 'q':
                    Arena.setIsGameOver(true);
                    isInputInvalid = false;
                    System.exit(0);
                    break;
                case 'p':
                    sign = HandSign.PAPER;
                    isInputInvalid = false;
                    break;
                case 's':
                    sign = HandSign.SCISSOR;
                    isInputInvalid = false;
                    break;
                case 'r':
                    sign = HandSign.ROCK;
                    isInputInvalid = false;
                    break;
                default:
                    System.out.println("Invalid input ! Please enter valid value!");
                    break;
            }

        }while(isInputInvalid);
    }

    public void incrementNumOfWin()
    {
        this.numOfWin++;
    }

    //Getter Setter
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSign(HandSign sign) {
        this.sign = sign;
    }

    public HandSign getSign() {
        return sign;
    }

    public static void setNumOfDraw(int numOfDraw) {
        Gamer.numOfDraw = numOfDraw;
    }

    public static int getNumOfDraw() {
        return numOfDraw;
    }

    public static void setNumOfTrials(int numOfTrials) {
        Gamer.numOfTrials = numOfTrials;
    }

    public static int getNumOfTrials() {
        return numOfTrials;
    }

    public void setNumOfWin(int numOfWin) {
        this.numOfWin = numOfWin;
    }

    public int getNumOfWin() {
        return numOfWin;
    }


}
