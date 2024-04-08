import java.util.ArrayList;
import java.util.Scanner;

public class bank {
    public static void main(String[] args) {
        System.out.println("Enter your role:\n 1.Admin\n 2.User\n3.Banks");
        Scanner scanner = new Scanner(System.in);
        Admin admin1 = new Admin();
        Banks banks1 = new Banks();
        AtmMachine atm = new AtmMachine(9876543, "user1", "1234", 1000.0);
        Customer customer1 = new Customer(atm);
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                admin1.adminRole();
                break;
            case 2:
                customer1.login();
                break;
            case 3:
                banks1.bankRole();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }
}

class AtmMachine {
    int accountNumber;
    String name;
    String pin;
    double balance;

    public AtmMachine(int accountNumber, String accname, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.name = accname;
        this.pin = pin;
        this.balance = balance;
    }

    int getAccountNumber() {
        return accountNumber;
    }

    boolean verifyPin(String pin) {
        return this.pin.equals(pin);
    }

    double getBalance() {
        return balance;
    }

    void deposit(double amt) {
        balance += amt;
    }

    boolean withdraw(double amt) {
        if (balance >= amt) {
            balance -= amt;
            return true;
        }
        return false;
    }
}

class Customer {
    Scanner scanner = new Scanner(System.in);
    AtmMachine acc;
    int pin;

    public Customer(AtmMachine acc) {
        this.acc = acc;
    }

    void login() {
        System.out.println("Enter your name: ");
        String name = scanner.next();
        System.out.println("Please enter your 4-digit PIN: ");
        String pass = scanner.next();
        if (acc.verifyPin(pass)) {
            System.out.println("Login Successful");
            userFunctions();
        } else {
            System.out.println("Invalid PIN");
        }
    }

    void userFunctions() {
        while (true) {
            System.out.println(
                    "1.View Bank Balance\n2.Deposit\n3.Withdraw");
            int op = scanner.nextInt();
            switch (op) {
                case 1:
                    viewBalance();
                    break;
                case 2:
                    depositAmt();
                    break;
                case 3:
                    withdrawAmt();
                    break;
                default:
                    System.out.println("Please enter a valid input :)");
            }
            System.out.println("Do you want to continue? Y/N");
            String ch = scanner.next();
            if (ch.equals("y") || ch.equals("Y")) {
                userFunctions();
            } else if (ch.equals("n") || ch.equals("N")) {
                System.out.println("Thank you! Have a good day");
                break;
            }
        }
    }

    void viewBalance() {
        System.out.println("Your balance is: " + acc.getBalance());
    }

    void depositAmt() {
        System.out.println("Enter amount to be deposited: ");
        double depositAmount = scanner.nextDouble();
        acc.deposit(depositAmount);
        System.out.println("Amount deposited\t Your Balance is: Rs." + acc.getBalance());
    }

    void withdrawAmt() {
        System.out.println("Enter Amount to be withdrawn: ");
        double withdrawAmount = scanner.nextDouble();
        if (acc.withdraw(withdrawAmount)) {
            System.out.println("Amount withdrawn\t Your Balance is: Rs." + acc.getBalance());
        } else {
            System.out.println("Insufficient Balance");
        }
    }
}

class Banks {
    ArrayList<Atm> bank = new ArrayList<>();
    ArrayList<ATM> atm = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    void bankRole() {
        System.out.println(
                "1.Check Balance\n2.Refill");
        System.out.print("Enter your choice: ");
        int ch = scanner.nextInt();
        switch (ch) {
            case 1:
                checkBalance();
                break;
            case 2:
                refillAmount();
                break;
            default:
                System.out.println("Please enter a valid choice");
        }
        System.out.println("Do you want to proceed further? Y/N");
        String c = scanner.next();
        if (c.equalsIgnoreCase("y")) {
            while (ch != 4) {
                bankRole();
            }
        } else if (c.equalsIgnoreCase("n")) {
            System.out.println("Thank you for checking ATM details");
        }
    }

    void checkBalance() {
        System.out.print("Enter bank id to check remaining amount in ATM: ");
        int id = scanner.nextInt();
        for (ATM a : atm) {
            if (id == a.bankId) {
                System.out.println("Bank Id:" + id);
                System.out.println("Remaining Amount in ATM: " + a.getRemainingAmount());
                return;
            }
        }
        System.out.println("ATM for Bank with ID " + id + " not found.");
    }

    void refillAmount() {
        System.out.println("Enter bank id");
        int id = scanner.nextInt();
        ATM a = new ATM(id);
        for (Atm b : bank) {
            if (id == b.bankId) {
                System.out.println("Bank Id:" + b.bankId);
                System.out.println("Bank Location :" + b.location);
                System.out.println("Minimum Amount in ATM: " + a.refillAmount);
            }
        }
    }
}

class Admin {
    ArrayList<Atm> bank = new ArrayList<>();
    ArrayList<User> user = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    void adminRole() {
        System.out.println(
                "1.Add ATM\n2.Add User\n3.View ATM list\n4.Remove ATM details");
        System.out.print("Enter your choice: ");
        int opt = scanner.nextInt();
        switch (opt) {
            case 1:
                addATMdetails();
                break;
            case 2:
                addUser();
                break;
            case 3:
                viewATMlist();
                break;
            case 4:
                removeATM();
                break;
            default:
                System.out.println("Please enter a correct option to perform an operation");
        }
        System.out.println("Do you want to continue? Y/N");
        String ch = scanner.next();
        if (ch.equals("y") || ch.equals("Y")) {
            while (opt != 6) {
                adminRole();
            }
        } else {
            System.out.println("Thank you Have a nice day");
        }
    }

    void addATMdetails() {
        System.out.print("Enter ATM id: ");
        int bankId = scanner.nextInt();
        System.out.print("Enter Bank IFSC code: ");
        String ifsc = scanner.next();
        System.out.print("Enter ATM'S location: ");
        String location = scanner.next();
        System.out.print("Enter ATM's minimum deposit amount: ");
        Double depositAmount = scanner.nextDouble();
        Atm b1 = new Atm(bankId, ifsc, location, depositAmount);
        bank.add(b1);
        System.out.println("ATM setup completed successfully");
    }

    void addUser() {
        System.out.print("Enter Your name: ");
        String name = scanner.next();
        System.out.print("Enter You Account Number: ");
        Double accountNumber = scanner.nextDouble();
        System.out.print("Enter Your Mobile number: ");
        Double phoneNumber = scanner.nextDouble();
        User u1 = new User(name, accountNumber, phoneNumber);
        user.add(u1);
        System.out.println("Successfully Added a new user");
    }

    void viewATMlist() {
        for (Atm b : bank) {
            System.out.println("ATM ID: " + b.bankId + " Bank IFSC code: " + b.ifsc + " ATM'S location: " + b.location
                    + " ATM's minimum deposit amount: " + b.depositAmount);
        }
    }

    void removeATM() {
        int idx = 0;
        System.out.println("Enter ATM id to delete: ");
        int id = scanner.nextInt();
        for (Atm b : bank) {
            if (b.bankId == id) {
                idx = bank.indexOf(b);
            }
        }
        bank.remove(idx);
        System.out.println("ATM removed from");
    }
}

class Atm {
    int bankId;
    String ifsc;
    String location;
    Double depositAmount;

    public Atm(int bankId, String ifsc, String location, Double depositAmount) {
        this.bankId = bankId;
        this.ifsc = ifsc;
        this.location = location;
        this.depositAmount = depositAmount;
    }
}

class User {
    Double accountNumber;
    String name;
    Double phoneNumber;

    User(String name, Double accountNumber, Double phoneNumber) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}

class ATM {
    double usedAmount;
    double remainingAmount;
    double refillAmount;
    Double depositAmount;
    double totalAmount;
    public int bankId;

    public ATM(double usedAmount) {
        this.usedAmount = usedAmount;
    }

    public double getRemainingAmount() {
        return remainingAmount = depositAmount - usedAmount;
    }

    public double refill() {
        return totalAmount = remainingAmount + refillAmount;
    }
}
