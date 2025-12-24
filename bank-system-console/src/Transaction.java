import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 1 件分の取引情報（入金・出金）を保持するクラス。
 */
public class Transaction {

    /** 取引日時 */
    private LocalDate dateTime;

    /** 取引種別 */
    private String type;

    /** 金額 */
    private int amount;
    
    /** 取引後の残高 */
    private int balanceSnapshot;

    /**
     * コンストラクタ：取引種別と金額を受け取り、日時は現在時刻を設定。
     * @param type 取引種別 (入金/出金)
     * @param amount 取引金額
     * @param balanceSnapshot 取引後の残高を記録したもの
     */
    public Transaction(String type, int amount, int balanceSnapshot) {
        this.dateTime = LocalDate.now();
        this.type = type;
        this.amount = amount;
        this.balanceSnapshot = balanceSnapshot;
    }

    /**
     * 取引内容を整形して「日時 - 種別：金額」形式の文字列で返す。
     * @return 整形された取引詳細文字列
     */
    public String getDetail() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        return "日付: " + fmt.format(dateTime) 
             + " 取引種類: " + type 
             + " 金額: " + amount + "円"
             + " 残高: " + balanceSnapshot + "円";
    }
}