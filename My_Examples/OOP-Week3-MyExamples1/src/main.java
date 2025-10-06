public static void main(String[] args)
{

    Scanner scanner = new Scanner(System.in);
    OnlineStoreAccount user = new OnlineStoreAccount("Enes" , 100.0);

    System.out.println("Lutfen giris yapiniz.");
    user.setPassword("1234");
    user.login();

    while (true)
    {
        if (user.isOperationBlocked()) return;

        System.out.println("\n---- Online Store Menu ----");
        System.out.println("1 - Para yatir");
        System.out.println("2 - Sepete ekle");
        System.out.println("3 - Odeme yap");
        System.out.println("0 - Cikis");
        System.out.println("Seciminiz :");

        int choice = scanner.nextInt();

        switch (choice)
        {
            case 1:
            {
                System.out.println("Ne kadar para yatirmak istersiniz?");
                double amount = scanner.nextDouble();
                user.deposit(amount);
                break;
            }
            case 2: user.addToCart(); break;
            case 3: user.checkout(); break;
            case 0:
            {
                System.out.println("Cikis yapiliyor...");
                return;
            }
            default:
                System.out.println("Gecersiz secim");
                break;

        }
    }
}

