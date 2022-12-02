package src.util;

/**
* Class Verify holds basic methods for verifying that the end user gives proper input
* to be held in the contactsbook.
*
*
*/
public class Verify {
    /**
    * The method verifyId() verifies if the given input was in the correct Finlands personal id format.
    *
    * @param inputid Input id is the String that the method then proceeds to verify.
    * @return return a boolean value true if the given input was in the correct Finlands personal id format
    * and false if it wasn't.
    */
    public static boolean verifyId(String inputid) {
        
        // checks if the given inputid Strings length is exactly 11
        if (inputid.length() != 11) {
            return false;
        }

        // checks if the first 6 indexes of the inputid String are numbers
        for (int i=0; i < 6; i++) {
            if (!("" + inputid.charAt(i)).matches("[0-9]+")) {
                return false;
            }
        }

        // validating through every possible error in the date month and year
        if ((Character.getNumericValue(inputid.charAt(0))) < 0 || (Character.getNumericValue(inputid.charAt(0))) > 3) {
            return false;
        } else if (Character.getNumericValue(inputid.charAt(0)) == 3 && Character.getNumericValue(inputid.charAt(1)) > 2) {
            return false;
        } else if (Character.getNumericValue(inputid.charAt(1)) < 0 || Character.getNumericValue(inputid.charAt(1)) > 9) {
            return false;
        } else if (Character.getNumericValue(inputid.charAt(2)) < 0 || Character.getNumericValue(inputid.charAt(2)) > 1) {
            return false;
        } else if (Character.getNumericValue(inputid.charAt(2)) == 1 && Character.getNumericValue(inputid.charAt(3)) > 2) {
            return false;
        } else if (Character.getNumericValue(inputid.charAt(3)) < 0 || Character.getNumericValue(inputid.charAt(3)) > 9) {
            return false;
        } else if (Character.getNumericValue(inputid.charAt(4)) < 0 || Character.getNumericValue(inputid.charAt(4)) > 9) {
            return false;
        } else if (Character.getNumericValue(inputid.charAt(5)) < 0 || Character.getNumericValue(inputid.charAt(5)) > 9) {
            return false;
        }

        // possiple marks for the century sign
        String centurysign = "+-UVWXYABCDEF";
        if (!centurysign.contains("" + inputid.charAt(6))) {
            return false;
        }

        // validates that the individual number ZZZ contains only numbers
        if (!("" + inputid.charAt(7)).matches("[0-9]+") || !("" + inputid.charAt(8)).matches("[0-9]+") || !("" + inputid.charAt(9)).matches("[0-9]+")) {
            return false;
        }

        // possible marks for the controlcharacter sign
        String controlcha = "0123456789ABCDEFHJKLMNPRSTUVWXY";
        if (!controlcha.contains("" + inputid.charAt(10))) {
            return false;
        }


        return true;
    }
    public static boolean verifyFname(String inputfname) {
        if (inputfname.length() > 0 && inputfname.length() < 40 ) {
            return true;
        }
        
        return false;
    }
    public static boolean verifyLname(String inputlname) {
        if (inputlname.length() > 0 && inputlname.length() < 40 ) {
            return true;
        }

        return false;
    }
    public static boolean verifyPhoneNo(String inputphone) {
        if (inputphone.length() >= 3 && inputphone.length() < 13) {
            boolean stringvalid = inputphone.matches("[0-9]+");
            if (stringvalid == true) {
                return true;
            }
        }

        return false;
    }
    public static boolean verifyAddress(String inputaddress) {
        if (inputaddress.length() > 0 && inputaddress.length() < 40) {
            return true;
        }

        return false;
    }
    public static boolean verifyEmail(String inputemail) {
        boolean verify = false;

        int atcount = 0;
        int dotcount = 0;
        for (int i=0; i<inputemail.length(); i++) {
            if (inputemail.charAt(i) == '@') {
                atcount++;
            } else if (inputemail.charAt(i) == '.') {
                dotcount++;
            }
        }

        if (inputemail.charAt(0) != '@' &&
        inputemail.charAt(0) != '.' &&
        inputemail.charAt(inputemail.length()-1) != '@' &&
        inputemail.charAt(inputemail.length()-1) != '.' &&
        atcount == 1 && dotcount > 0) {
            verify = true;
        } else {
            verify = false;
        }

        return verify;

    }
    public static boolean verifyYesNo(String input) {

        if (input.equals("1") || input.equals("2")) {
            return true;
        }
        return false;
    }
}