import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Create an empty list of User objects
        ArrayList<User> users = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("==============ATM SYSTEM===============");
            System.out.println("1.Account Login");
            System.out.println("2.Account Register");
            System.out.println("0.Exit");
            System.out.println();
            System.out.print("Enter Your Selection : ");
            int command = sc.nextInt();

            switch (command) {
                case 0:
                    System.out.println("Thank you and bye bye...");
                    return;
                case 1:
                    login(users, sc);
                    break;
                case 2:
                    register(users, sc);
                    break;
                default:
                    System.out.println("Invalid selection");
            }//switch
        }//while

    }//main

    public static void register(ArrayList<User> users, Scanner sc) {
        System.out.println("===========Register Account=============");
        System.out.println("Please Enter Username : ");
        String username = sc.next();
        String password;
        double withdrawLimit;

        //Username
        if (!users.isEmpty()) {
            for (User user : users) {
                if (username.equals(user.getUserName())) {
                    System.out.println("User already exist!!");
                    return;
                }//if
            }//for
        }//if

        //Password
        do {
            System.out.println("Please Enter Password: ");
            password = sc.next();
            System.out.println("Please Re-enter Password: ");
            String rePassword = sc.next();
            sc.nextLine(); // Clear the input buffer

            if (!password.equals(rePassword)) {
                System.out.println("Passwords do not match. Please try again.");
            } else {
                // Passwords match, exit the loop
                break;
            }
        } while (true);

        //withdraw limit
        do {
            try {
                System.out.println("Maximum Amount Cash every withdraw : ");

                withdrawLimit = sc.nextDouble();
                if (withdrawLimit < 200) {
                    System.out.println("Minimum Account Cash every withdraw should exceed 200!!");
                    System.out.println("Please try again");
                } else
                    break;

            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a valid number.");
                sc.nextLine(); // Clear the input buffer
            }//catch
        } while (true);

        System.out.println("hello");
        //cardId
        String cardId = utils.generateCardId(users);

        //Create user
        User user = new User(cardId, username, password, withdrawLimit, 0);
        users.add(user);

        System.out.println();
        System.out.println("===================================");
        System.out.println("Account Registered ");
        System.out.println("User Name : " + user.getUserName());
        System.out.println("Card Id : " + user.getCardId());


    }//register

    public static void login(ArrayList<User> users, Scanner sc) {
        System.out.println("==================Login====================");
        //check system have account exist
        if (users.isEmpty()) {
            System.out.println("Currently System dont have Account existed");
            return; //end program
        }


        System.out.println("Enter Card ID or UserName : ");
        String cardId_or_username = sc.next();
        User user = utils.varifyUser(cardId_or_username, users);

        if (user == null) {
            System.out.println("User not found!!");
            return;
        }


        boolean login = false;
        int remainingAttempts = 4;

        while (remainingAttempts >= 1) {
            System.out.println("Enter password: ");
            String password = sc.next();

            if (!password.equals(user.getPassword())) {
                remainingAttempts--;
                if (remainingAttempts >= 1) {
                    System.out.printf("Invalid password. You have %d attempts remaining.\n", remainingAttempts);
                }
            } else {
                login = true;
                break;
            }
        }

        if (login) {
            //Successfully login
            System.out.printf("Welcome back,User %s \n", user.getUserName());
            utils.showUserCommand(sc, user, users);
        }


    }//login

}//Main