public class AccountManager {


    public static boolean login(BankAccount account)
    {
        if (account == null) return false;

        String enteredPass;
        // keep asking until correct password is entered or account is suspended
        while (true) {
            long now = System.currentTimeMillis();

            // if there is a suspension value set, decide whether it's still active or expired
            if (account.suspendedUntilMillis != 0) {
                if (account.suspendedUntilMillis > now) {
                    long timeLeft = (account.suspendedUntilMillis - now) / 1000;
                    System.out.printf("Account suspended. Try again in %s min %s second later. %n" , timeLeft / 60 , timeLeft % 60);
                    return false;
                } else {
                    // suspension expired -> clear
                    account.suspendedUntilMillis = 0;
                    account.isSuspended = false;
                    account.passTrialCounter = 0;
                }
            }

            enteredPass = InputUtils.readString("Enter password?[6 digit number]");

            // check if password format is valid
            if (PasswordCheck.isInvalidPassword(enteredPass)) {
                continue; // ask again
            }

            // correct password -> success (use enteredPass.equals to avoid NPE if stored password is null)
            boolean isPassMatched = enteredPass.equals(account.getPassword());
            if (isPassMatched)
            {
                account.passTrialCounter = 0;
                account.isSuspended = false;
                return true;
            }

            // wrong password
            System.out.println("Wrong password!");
            account.passTrialCounter++;

            if (account.passTrialCounter >= 3) {
                account.isSuspended = true;
                account.suspendedUntilMillis = now + (5 * 60 * 1000); // 5 minutes
                System.out.println("Account suspended for 5 minutes due to repeated failed attempts.");
                return false;
            }
        }
    }
}
