package Utils;

public class PasswordCheck {

    String password;
    private static final int PASSWORD_LENGTH = 6;

    public static boolean isInvalidPassword(String password)
    {
        if (password == null || password.isEmpty()) {
            System.out.println("Password cannot be empty");
            return true;
        }

        if (!password.matches("[0-9]+"))
        {
            System.out.println("Password must be contains only integer numbers");
            return true;
        }

        if (password.length() !=  PASSWORD_LENGTH)
        {
            System.out.println("Password must be 6 digit");
            return true;
        }

        return false;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public int getPASSWORD_LENGHT() {
        return PASSWORD_LENGTH;
    }
}
