/**
 * システム全体で利用する定数をまとめたクラス。
 * 文言や数値を 1 箇所に集約することで、変更時の影響範囲を最小化する。
 */
public class Constants {

    // ---- 口座番号関連 ----
    public static final int ACCOUNT_MIN = 1000000;
    public static final int ACCOUNT_RANGE = 9000000;
    
    // ---- ファイル設定 ---- // 
    public static final String FILE_ACCOUNT_CSV = "account.csv";
    public static final String FILE_TRANSACTION_CSV = "transaction.csv";
    
    // ---- 取引種別 (プログラム内部で使用する定数) ---- //
    public static final String TRANSACTION_DEPOSIT_CSV = "deposit";
    public static final String TRANSACTION_WITHDRAW_CSV = "withdraw";
    public static final String TRANSACTION_GET_BALANCE_CSV = "getBalance";

    // ---- 取引種別 (ユーザー出力や履歴表示用)----
    public static final String TRANSACTION_DEPOSIT = "入金";
    public static final String TRANSACTION_WITHDRAW = "出金";
    public static final String TRANSACTION_INITIAL = "初期預金";

    // ---- 日付フォーマット ----
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    // ---- メニュー番号 ----
    public static final int MENU_DEPOSIT = 1;
    public static final int MENU_WITHDRAW = 2;
    public static final int MENU_BALANCE = 3;
    public static final int MENU_EXIT = 4;

    // ---- メッセージ ----
    public static final String MSG_SYSTEM_START = "銀行システム起動";

    public static final String MSG_INPUT_PEOPLE = "口座開設する人数を入力してください：";
    public static final String MSG_INPUT_NAME = "口座名義人を入力してください：";
    public static final String MSG_INPUT_INITIAL_BALANCE = "初期残高を入力してください：";

    public static final String MSG_ACCOUNT_NUMBER = "口座番号：";

    public static final String MSG_MENU = "\n操作を選択してください";
    public static final String MSG_MENU1 = "1：入金";
    public static final String MSG_MENU2 = "2：出金";
    public static final String MSG_MENU3 = "3：残高照会";
    public static final String MSG_MENU4 = "4：終了";
    public static final String MSG_MENU_SELECT = "選択：";

    public static final String MSG_INPUT_ACCOUNT = "口座番号を入力してください：";
    public static final String MSG_INPUT_DEPOSIT = "入金額を入力してください：";
    public static final String MSG_INPUT_WITHDRAW = "出金額を入力してください：";

    public static final String MSG_DEPOSIT_DONE = "入金が完了しました";
    public static final String MSG_WITHDRAW_DONE = "出金が完了しました";
    public static final String MSG_WITHDRAW_FAIL = "残高不足のため出金できません";

    public static final String MSG_NO_ACCOUNT = "該当する口座がありません。";
    public static final String MSG_EXIT = "システムを終了します。";
    public static final String MSG_EXIT_CSV = "銀行システム終了";

    public static final String MSG_BALANCE = "残高：";
    public static final String MSG_ACCOUNT_HOLDER = "口座名義：";

    public static final String MSG_HISTORY_HEADER = "【取引履歴】";
    public static final String MSG_NO_HISTORY = "取引履歴はありません。";

    public static final String MSG_INVALID_AMOUNT = "金額が不正です。";
    public static final String MSG_INVALID_INPUT = "入力値が不正です。もう一度入力してください。";
    public static final String MSG_AMOUNT_INVALID = "金額が不正です。取引は実行されませんでした｡";

    public static final String MSG_INVALID_NAME = "名前が空白です。もう一度入力してください。";
    public static final String MSG_CSV_LOAD_ERROR = "データの読み込みに失敗しました｡取引を中断します｡：";
    public static final String MSG_ACCOUNT_OPENED = "口座開設完了";
    
    // エラー詳細メッセージのヘッダー
    public static final String ERR_MSG_DETAIL_HEADER = "【エラー詳細】: ";

    // 数値形式不正 (NumberFormatException) 時のメッセージ
    public static final String ERR_MSG_AMOUNT_FORMAT = "数値データに記号や文字が混入しています。";
    
    // account.csv 関連
    public static final String ERR_MSG_ACCOUNT_ITEM_COUNT = "不正なデータ形式です。項目数が期待値(2)と異なります: ";
    public static final String ERR_MSG_INITIAL_BALANCE_FORMAT = "初期残高のデータに記号や文字が混入しています。"; 

    // transaction.csv 関連
    public static final String ERR_MSG_TRANSACTION_ITEM_COUNT = "不正なデータ形式です。項目数が期待値(3)と異なります: ";
    public static final String ERR_MSG_TRANSACTION_TYPE = "不正な取引タイプが検出されました: ";

    // 出力フォーマット
    public static final String ACCOUNT_INFO_FORMAT = "口座番号: %07d 口座名義人: %s 初期残高: %d";
    public static final String ACCOUNT_NUMBER_FORMAT_7DIGIT = "%07d";
    public static final String MSG_DEPOSIT_FORMAT = ACCOUNT_NUMBER_FORMAT_7DIGIT + "に入金しました。金額: %d%n";
    public static final String MSG_WITHDRAW_FORMAT = ACCOUNT_NUMBER_FORMAT_7DIGIT + "から出金しました。金額: %d%n";
}