package ppoo.project.library.validator;

public class Validator {
    public static boolean validateAuth(String email, String password){
        if(email.length() == 0){
            System.out.println("Emailul nu a fost introdus");
            return false;
        }
        else if(!email.contains("@gmail.com")){
            System.out.println("Emailul trebuie sa contina @gmail.com");
            return false;
        }
        else if(password.length() == 0){
            System.out.println("Parola nu a fost introdusa");
            return false;
        }
        else{
            return true;
        }
    }

    public static boolean validateRegister(String name, String email, String password) {
        if (name.length() == 0) {
            System.out.println("Nu ați introdus numele.");
            return false;
        } else if (email.length() == 0) {
            System.out.println("Nu ați introdus emailul.");
            return false;
        } else if (!isValidEmail(email)) {
            System.out.println("Emailul nu are un format valid.");
            return false;
        } else if (password.length() < 6) {
            System.out.println("Parola trebuie să aibă cel puțin 6 caractere.");
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidEmail(String email) {
        String regex = "^(.+)@(.+)$";
        return email.matches(regex);
    }



}
