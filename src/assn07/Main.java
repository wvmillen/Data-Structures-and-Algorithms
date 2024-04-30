package assn07;


import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Map<String,String> passwordManager = new PasswordManager<>();

        String input;

        do {
            System.out.println("Enter Master Password");
            input = scanner.nextLine().trim();
        } while(!passwordManager.checkMasterPassword(input));

        while (true) {
            String command = scanner.nextLine().trim();

            if(command.compareTo("New password") == 0) { //new password
                String website = scanner.nextLine().trim();
                String password = scanner.nextLine().trim();

                passwordManager.put(website,password);

                System.out.println("New password added");
            }

            else if(command.compareTo("Get password") == 0) {  //get password
                String website = scanner.nextLine().trim();


                if (passwordManager.get(website) == null) {
                    System.out.println("Account does not exist");
                }

                else{
                    System.out.println(passwordManager.get(website));
                }
            }

            else if(command.compareTo("Delete account") == 0) { //delete account
                String websiteName = scanner.nextLine().trim();
                boolean deleted;
                if(passwordManager.remove(websiteName).isEmpty()){
                    deleted = false;
                }
                    else{
                        deleted = true;
                }
                if (deleted) {
                    System.out.println("Account deleted");
                } else {
                    System.out.println("Account does not exist");
                }
            }

            else if(command.compareTo("Check duplicate password") == 0) { //dupe
                String website = scanner.nextLine().trim();
                List<String> duplicates = passwordManager.checkDuplicate(website);

                if (duplicates.isEmpty()) {
                    System.out.println("No account uses that password");
                }

                else {
                    System.out.println("Websites using that password:");
                    for (String matchingPassword : duplicates) {
                        System.out.println(matchingPassword);
                    }
                }
            }

            else if(command.compareTo("Get accounts") == 0) { //gets accounts
                Set<String> sites = passwordManager.keySet();
                Iterator<String> sitesIterator = sites.iterator();
                System.out.println("Your accounts:");
                while (sitesIterator.hasNext()) {
                    System.out.println(sitesIterator.next());
                }
            }

            else if(command.compareTo("Generate random password") == 0) { //generate random password
                int length = scanner.nextInt();

                System.out.println(passwordManager.generatesafeRandomPassword(length));
            }

            else if(command.compareTo("Exit") == 0) { //exits
                return;
            }

            else {
                System.out.println("Command not found"); //general error
            }
        }
    }
}