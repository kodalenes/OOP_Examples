import java.util.InputMismatchException;
import java.util.Scanner;

public class Book {
    //Attributes
    private String title;
    private String author;
    private int pageCount;
    private boolean isPublished;

    //Constructors
    public Book()
    {
        this.title = "Untitled";
        this.author = "Unknown";
        this.pageCount = 0;
        this.isPublished = true;
    }

    public Book(String title, String author, int pageCount)
    {
        this.title = title;
        this.author = author;
        this.pageCount = pageCount;
        if (pageCount < 5 || pageCount > 5000)
        {
            throw new InputMismatchException("Enter valid pageCount(5 to 5000");
        }
        isPublished = true;
    }

    //Getter & Setter Methods


    public void setAuthor(String author)
    {
        if (author.trim().matches("\\d+"))
        {
            throw new IllegalArgumentException("Author name cannot consist only of digits");
        }

        this.author = author;
    }

    public void setTitle(String title) {
        if (title.trim().matches("\\d+"))
        {
            throw new IllegalArgumentException("Author name cannot consist only of digits");
        }
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        if (pageCount < 5 || pageCount > 5000)
        {
            throw new IllegalArgumentException("Page count must be between 5 and 5000");
        }
        this.pageCount = pageCount;
    }

    public void updatePageCount(int newPageCount)
    {
        setPageCount(newPageCount);
    }

    //Methods

    public void printBookInfo()
    {
        System.out.println("Book Information");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("PageCount: " + pageCount);
        System.out.println("Published: " + (isPublished ? "Yes" : "No"));
        System.out.println("----------------------");
    }
}
