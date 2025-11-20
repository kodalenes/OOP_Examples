package Utils;

public class Validator {

    public static boolean isValidAccountHolder(String name)
    {
        if (name == null || name.trim().isEmpty())
            return false;

        for (char ch : name.toCharArray())
        {
            if (!Character.isLetter(ch) && ch != ' ')
            {
                return false;
            }
        }
        return true;
    }
}
