abstract class Account {
    protected int acc_no;
    protected String name;
    protected double balance;

    public Account(int acc_no, String name, double balance) {
        this.acc_no = acc_no;
        this.name = name;
        this.balance = balance;
    }

    // Print account statistics
     public void printStats(String action) {
        System.out.println(action + " performed.");
        System.out.println("Account No: " + acc_no);
        System.out.println("Name: " + name);
        System.out.println("Balance: " + balance);
        System.out.println("-----------------------");
    }
}

abstract class SavingsAcc extends Account {
    public SavingsAcc(int acc_no, String name, double balance) {
        super(acc_no, name, balance);
    }

   public void deposit(double amount) {
        balance += amount;
        balance += balance * 0.05; // 5% increment
        System.out.println("Deposit (Savings): Amount = " + amount);
        printStats("Deposit with 5% increment");
    }

    public void withdraw(double amount) {
        if (amount > 50000) {
            balance -= amount;
            balance -= balance * 0.02; // 2% decrement
            System.out.println("Withdraw (Savings): Amount = " + amount + " > 50000, 2% decrement applied");
            printStats("Withdraw above 50K with 2% decrement");
        } else {
            balance -= amount;
            System.out.println("Withdraw (Savings): Amount = " + amount);
            printStats("Regular withdraw");
        }
    }
}

class CurrentAcc extends Account {
    public CurrentAcc(int acc_no, String name, double balance) {
        super(acc_no, name, balance);
    }

    public void deposit(double amount) {
        balance += amount;
        balance += balance * 0.10; // 10% increment
        System.out.println("Deposit (Current): Amount = " + amount);
        printStats("Deposit with 10% increment");
    }

    public void withdraw(double amount) {
        if (amount > 11000) {
            balance -= amount;
            balance -= balance * 0.02; // 2% decrement
            System.out.println("Withdraw (Current): Amount = " + amount + " > 11000, 2% decrement applied");
            printStats("Withdraw above 11K with 2% decrement");
        } else {
            balance -= amount;
            System.out.println("Withdraw (Current): Amount = " + amount);
            printStats("Regular withdraw");
        }
    }
}
