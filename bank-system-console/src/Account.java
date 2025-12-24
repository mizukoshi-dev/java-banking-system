/**
 * 口座の基本情報（名義人・口座番号・残高）を保持するクラス。
 * 残高操作は原則として Bank クラスを経由する想定。
 * （業務ロジックを 1 箇所に集約するための設計方針）
 */
public class Account {

    /** 口座名義 */
    private String holderName;

    /** 口座番号 */
    private int accountNumber;

    /** 現在の残高 */
    private int balance;

    /**
     * コンストラクタ：名義人・口座番号・初期残高を設定する。
     * @param holderName 口座名義人
     * @param accountNumber 口座番号
     * @param balance 初期残高
     */
    public Account(String holderName, int accountNumber, int balance) {
        this.holderName = holderName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // ---- Getter ----

    /**
     * 口座名義人を返す。
     * @return 口座名義
     */
    public String getHolderName() {
        return holderName;
    }

    /**
     * 口座番号を返す。
     * @return 口座番号
     */
    public int getAccountNumber() {
        return accountNumber;
    }

    /**
     * 現在の残高を返す。
     * @return 現在の残高
     */
    public int getBalance() {
        return balance;
    }

    // ---- 残高操作（Bank から呼び出される前提） ----

    /**
     * 残高に金額を加算する。（Bankクラスからのみアクセス可能）
     * @param amount 加算する金額
     */
    void addBalance(int amount) {
        this.balance += amount;
    }

    /**
     * 残高から金額を減算する。（Bankクラスからのみアクセス可能）
     * @param amount 減算する金額
     */
    void subtractBalance(int amount) {
        this.balance -= amount;
    }
}