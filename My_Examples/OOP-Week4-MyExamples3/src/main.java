void main()
{
    Scanner scanner = new Scanner(System.in);
    Time time = new Time(scanner);

    time.setTime();
    System.out.println(time.getTime());
}
