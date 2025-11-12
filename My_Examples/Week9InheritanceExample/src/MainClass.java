public class MainClass {
    public static void main(String[] args)
    {
        SavingAccount SAccount1 = new SavingAccount("1","Enes" , 1000);
        CheckingAccount CAccount1 = new CheckingAccount("2" , "Omer" , 500, 200);

        SAccount1.displayAccountInfo();
        CAccount1.displayAccountInfo();

        SAccount1.deposit(500);
        SAccount1.addInterest();
        SAccount1.withdraw(200);

        SAccount1.displayAccountInfo();

        CAccount1.withdraw(600);
        CAccount1.withdraw(150);

        CAccount1.displayAccountInfo();

    }
}
