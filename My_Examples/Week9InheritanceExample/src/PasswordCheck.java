public class PasswordCheck {

    String password;
    private final int PASSWORD_LENGTH = 6;

    public boolean isValid(String password)
    {
        if (password == null || password.isEmpty()) {
            System.out.println("Password cannot be empty");
            return false;
        }

        if (!password.matches("[0-9]+"))
        {
            System.out.println("Password must be contains only integer numbers");
            return false;
        }

        if (password.length() < PASSWORD_LENGTH)
        {
            System.out.println("Password must be 6 digit");
            return false;
        }

        return true;
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
