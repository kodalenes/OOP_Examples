import java.util.Scanner;

public class MainClass {
    public static void main(String args[])
    {
        Scanner scanner = new Scanner(System.in);

        Book book1 = new Book("Enes" ,"Sensiz Bir Gece" , 120);

        try {
            book1.printBookInfo();
            book1.updatePageCount(InputHelper.getValidIntInput(scanner,5,5000,"Enter new page count"));
            InputHelper.getValidStringInput(scanner,"Enter author's name" ,book1::setAuthor);
            InputHelper.getValidStringInput(scanner,"Enter new title" ,book1::setTitle);
            book1.printBookInfo();
        }catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }catch (Exception e)
        {
            System.out.println("An unexpected error: " + e.getMessage());
        }



    }
}
