//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public static void main()
{
    Account account1 = new Account("Enes" , 100.0);
    Account account2 = new Account("Omer" , 100.0);

    System.out.println(account1.getBalance());
    System.out.println(account2.getBalance());

    account2.transferTo(account1,100.0);
    System.out.println(account1.getBalance());
    System.out.println(account2.getBalance());
    System.out.println(account1.getTransferredAmount());
    System.out.println(account2.getTransferredAmount());
}
