import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class utils {

    public static String generateCardId(ArrayList<User> users) {
        Random r = new Random();
        StringBuilder cardId = new StringBuilder();
        do {
            for (int i = 0; i < 16; i++) {
                if (i > 0 && i % 4 == 0) {
                    cardId.append('-');
                }
                cardId.append(r.nextInt(10));
            }
            if (users.isEmpty()) {
                break;
            }
            boolean isUnique = true;
            for (User user : users) {
                if (cardId.toString().equals(user.getCardId())) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) {
                break;
            }
        } while (true);

        return cardId.toString();
    }

    public static User varifyUser(String cardId_or_username, ArrayList<User> users) {
        for (User user : users) {
            if (cardId_or_username.equals(user.getUserName()) ||
                    cardId_or_username.equals(user.getCardId())) {
                return user;
            }//if
        }//for
        return null;
    }

    public static void showUserCommand(Scanner sc, User user, ArrayList<User> users) {
        while (true) {
            System.out.println();
            System.out.println("=========User Command=============");
            System.out.println("1.Check Account");
            System.out.println("2.Deposit");
            System.out.println("3.Withdraw");
            System.out.println("4.Transfer Balance");
            System.out.println("5.Change Password");
            System.out.println("6.Cancel Account");
            System.out.println("0.Log out");
            System.out.println("Enter selection ");
            int command = sc.nextInt();
            switch (command) {
                case 0:
                    System.out.println("Log Out Successfully");
                    return;//back to main page

                case 1:
                    showAccount(user);
                    break;
                case 2:
                    depositBalance(user, sc);
                    break;
                case 3:
                    withdrawBalance(user, sc);
                    break;
                case 4:
                    transferBalance(user, sc, users);
                    break;
                case 5:
                    updatePassword(user, sc);
                    return;
                case 6:
                    if (deleteAccount(user, sc, users)) {
                        //if deleted then back to main page
                        return;
                    } else {
                        //if not delete then back to menu page
                        break;
                    }

                default:
                    System.out.println("Invalid Selection");
                    break;
            }
        }//while


    }

    public static void showAccount(User user) {
        System.out.println("===========User Account Info================");
        System.out.println("Card Id : " + user.getCardId());
        System.out.println("Username : " + user.getUserName());
        System.out.println("Balance : " + user.getBalance());
        System.out.println("Limited Withdraw Balance per once : " + user.getWithdrawLimit());
        System.out.println("=============================================");
        System.out.println();

    }


    public static void depositBalance(User user, Scanner sc) {
        System.out.println("=============Deposit Balance================");
        double balance;
        do {
            try {
                System.out.println("Enter Amount deposit : ");
                balance = sc.nextDouble();
                if (balance < 0) {
                    System.out.println("Error: Invalid input. Please enter a valid number.");
                    sc.nextLine(); // Clear the input buffer
                } else
                    break;
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a valid number.");
                sc.nextLine(); // Clear the input buffer
            }//catch
        } while (true);


        //update user balance
        user.setBalance(user.getBalance() + balance);
        System.out.println("Balance successfully Deposit");
        showAccount(user);
    }

    public static void withdrawBalance(User user, Scanner sc) {
        System.out.println("================Withdraw Balance================");
        //check still have balance
        if (user.getBalance() == 0) {
            System.out.println("You have balance in your account");
            return;
        }

        double balance = 0;
        while (true) {
            try {
                System.out.println("Enter amount withdraw : ");
                balance = sc.nextDouble();
                if (balance < 0) {
                    System.out.println("Error: Invalid input. Please enter a valid number.");
                    sc.nextLine(); // Clear the input buffer
                } else
                    break;
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a valid number.");
                sc.nextLine(); // Clear the input buffer
            }//catch


            //check balance enough to be withdrawn
            if (balance > user.getWithdrawLimit()) {
                System.out.println("Amount Withdraw Once cannot exceed " + user.getWithdrawLimit());
            } else if (balance > user.getBalance()) {
                System.out.println("Balance not enough Withdraw Failed");
                System.out.println("Your current balance : " + user.getBalance());
            } else {
                //Success withdraw
                System.out.println("Withdraw Amount " + balance + " successful");
                //update user balance
                user.setBalance(user.getBalance() - balance);
                showAccount(user);
                return;
            }
        }//while
    }

    public static void transferBalance(User user, Scanner sc, ArrayList<User> users) {
        System.out.println("============Transfer Balance==============");
        //check system have 2 users exist
        if (users.size() < 2) {
            System.out.println("Current system dont have enough 2 user");
            return;
        }

        //>=2 Account user
        //check current user have balance
        if (user.getBalance() == 0) {
            System.out.println("Balance not enough Cannot Transfer");
            return;
        }

        while (true) {
            //Transfer balance
            System.out.println("Enter Card ID you wanted transfer to : ");
            String cardId = sc.next();

            //cannot be user card id
            if (cardId.equals(user.getCardId())) {
                System.out.println("Cannot transfer balance to yourself");
                continue; //back to next loop
            }
            //search card id
            User receiverUser = varifyUser(cardId, users);
            //check user exist or not
            if (receiverUser == null) {
                System.out.println("Cant find Card id");
            }

            if (receiverUser != null) {
                //user exist
                String userName = user.getUserName();
                String tip = "*" + userName.substring(1);
                System.out.println("Enter user [" + tip + "] full name");
                String preName = sc.next();

                if (!userName.startsWith(preName)) {
                    double balance = 0;
                    while (true) {
                        //transfer balance
                        System.out.println("Enter transfer Amount : ");
                        try {
                            balance = sc.nextDouble();
                        } catch (InputMismatchException e) {
                            System.out.println("Error: Invalid input. Please enter a valid number.");
                            sc.nextLine(); // Clear the input buffer
                        }//catch

                        //check enough balance 
                        if (balance > user.getBalance()) {
                            System.out.println("Balance not enough, Your balance is : " + user.getBalance());

                        } else {
                            //transfer balance success
                            user.setBalance(user.getBalance() - balance);
                            receiverUser.setBalance(receiverUser.getBalance() + balance);
                            System.out.println("Transfer Balance Successful");
                            System.out.println("Balance now : " + user.getBalance());
                            return;
                        }
                    }//while
                } else {
                    System.out.println("User name error");
                }

            }
        }//while
    }

    public static void updatePassword(User user, Scanner sc) {
        System.out.println("===============Change password================");
        while (true) {
            System.out.println("Enter current password : ");
            String password = sc.next();
            if (user.getPassword().equals(password)) {
                //password correct
                System.out.println("Enter new password : ");
                String newPassword = sc.next();

                System.out.println("Re-enter new password : ");
                String reNewPassword = sc.next();

                if (newPassword.equals(reNewPassword)) {
                    //successfully change password
                    user.setPassword(newPassword);
                    System.out.println("Password have changed");
                    return;
                } else {
                    System.out.println("Password not same");
                }
            } else {
                System.out.println("Invalid Password");
            }
        }//while
    }

    public static boolean deleteAccount(User user, Scanner sc, ArrayList<User> users) {
        System.out.println("===========Delete Account=================");
        System.out.println("Are you sure you wanna delete Account? [Y]/[N] : ");
        String rs = sc.next();
        if (rs.equals("Y")) {
            if (user.getBalance() > 0) {
                System.out.println("Still have balance cannot delete Account");
            } else {
                users.remove(user);
                System.out.println("Account have been deleted");
                return true;
            }
        }else
            System.out.println("Account not changed");
        
        return false;
    }
}//utils
