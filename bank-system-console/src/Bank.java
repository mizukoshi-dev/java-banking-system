import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * 口座管理と入出金処理を担当するクラス。
 * このクラスに業務ロジックをまとめることで整合性を確保する。
 */
public class Bank {

    /**
     * このアプリ実行中に払い出された口座番号を保持する集合。
     * 再起動するとクリアされるため永続化は行わない。
     */
    private static final Set<Integer> usedNumbers = new HashSet<>();

    /** この銀行インスタンスが保持する口座データ */
    private Account account;

    /** この口座の取引履歴 */
    private List<Transaction> history;

    /**
     * 名義と初期残高を受け取り、重複しない口座番号を自動生成して口座を作成する。
     */
    public Bank(String holderName, int initialBalance) {
        int number = generateUniqueNumber();
        this.account = new Account(holderName, number, initialBalance);
        this.history = new ArrayList<>();
        history.add(new Transaction(Constants.TRANSACTION_INITIAL, initialBalance, initialBalance));
    }
    
    /**
     * CSVファイルからの復元用コンストラクタ。
     * 既存の口座番号を引き継いでインスタンスを生成する。
     */
    public Bank(String holderName, int initialBalance, int accountNumber) {
        // 読み込んだ番号を使用済みリストに登録し、重複を防ぐ
        usedNumbers.add(accountNumber);
        this.account = new Account(holderName, accountNumber, initialBalance);
        this.history = new ArrayList<>();
        history.add(new Transaction(Constants.TRANSACTION_INITIAL, initialBalance, initialBalance));
    }

    public Account getAccount() {
        return account;
    }

    public List<Transaction> getHistory() {
        return history;
    }

    /**
     * ランダムな口座番号を生成し、重複しない番号を返す。
     */
    private int generateUniqueNumber() {
        Random rand = new Random();
        int num;

        do {
            num = rand.nextInt(Constants.ACCOUNT_RANGE) + Constants.ACCOUNT_MIN;
        } while (usedNumbers.contains(num));

        usedNumbers.add(num);
        return num;
    }

    // ---- 入出金処理 ----

    public boolean deposit(int amount) {
        // 金額が業務上有効かチェック (1以上)
        if (amount <= 0) {
            return false;
        }

        account.addBalance(amount);
        history.add(new Transaction(Constants.TRANSACTION_DEPOSIT, amount, account.getBalance()));
        return true;
    }

    /**
     * 出金処理。残高不足の場合は false を返す。
     */
    public boolean withdraw(int amount) {
        // 金額が業務上有効かチェック (1以上)
        if (amount <= 0) {
            return false;
        }
        
        // 2. 残高不足のチェック
        if (account.getBalance() < amount) {
            return false;
        }
        
        account.subtractBalance(amount);
        history.add(new Transaction(Constants.TRANSACTION_WITHDRAW, amount, account.getBalance()));
        return true;
    }

    /** 取引履歴を表示する */
    public void printHistory() {
        System.out.println(Constants.MSG_HISTORY_HEADER);

        if (history.isEmpty()) {
            System.out.println(Constants.MSG_NO_HISTORY);
            return;
        }

        for (Transaction t : history) {
            System.out.println(t.getDetail());
        }
    }
}
