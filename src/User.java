public class User {
    private String cardId;
    private String userName;
    private String password;
    private double withdrawLimit;
    private double balance;

    public User(String cardId, String userName, String password, double withdrawLimit, double balance) {
        this.cardId = cardId;
        this.userName = userName;
        this.password = password;
        this.withdrawLimit = withdrawLimit;
        this.balance = balance;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getWithdrawLimit() {
        return withdrawLimit;
    }

    public void setWithdrawLimit(double withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
