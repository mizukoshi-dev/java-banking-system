import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * コンソールからのユーザー入力を受け付け、
 * 入出金や残高照会などの操作フローを提供するクラス。
 */
public class BankConsoleOperation {

    /** 入力受付用の Scanner。nextLine() に統一して使用する。 */
    private static final Scanner sc = new Scanner(System.in);

    /**
     * アプリケーションのエントリーポイント。
     * 口座開設からメイン操作ループまでの処理フローを制御する。
     * 
     * @param args コマンドライン引数 (未使用)
     */
    public static void main(String[] args) {

        System.out.println(Constants.MSG_SYSTEM_START);

        // 作成された全ての口座インスタンスを管理するリスト
        List<Bank> accounts = new ArrayList<>();

        // ---- 開設する口座数の入力 ----
        // 1以上の整数入力を保証する readPositiveInt を使用
        int people = readPositiveInt(Constants.MSG_INPUT_PEOPLE);

        // ---- 口座開設処理 ----
        // 指定された人数分だけ繰り返し口座を作成する
        for (int i = 0; i < people; i++) {
            System.out.println((i + 1) + "人目の口座開設");

            // 名前と初期残高の入力（入力チェック付き）
            String name = readName(Constants.MSG_INPUT_NAME);

            // 初期残高の入力に readPositiveInt を使用
            int balance = readPositiveInt(Constants.MSG_INPUT_INITIAL_BALANCE);

            // 口座インスタンスの生成とリストへの登録
            Bank bank = new Bank(name, balance);
            accounts.add(bank);

            System.out.println(Constants.MSG_ACCOUNT_NUMBER + bank.getAccount().getAccountNumber());
        }

        // ---- メイン操作ループ ----
        // ユーザーが「終了」を選択するまで処理を継続する
        while (true) {
            printMenu();

            // メニュー選択の入力
            int select = readInt("");

            // メニュー番号の範囲チェック（不正な値はここで弾く）
            if (select < Constants.MENU_DEPOSIT || select > Constants.MENU_EXIT) {
                System.out.println(Constants.MSG_INVALID_INPUT);
                continue;
            }

            // 終了判定
            if (select == Constants.MENU_EXIT) {
                System.out.println(Constants.MSG_EXIT);
                break;
            }

            // 操作対象となる口座番号の入力
            int accNum = readInt(Constants.MSG_INPUT_ACCOUNT);

            // 入力された番号に該当する口座を検索
            Bank target = findAccount(accounts, accNum);

            // 該当する口座が存在しない場合のエラーハンドリング
            if (target == null) {
                System.out.println(Constants.MSG_NO_ACCOUNT);
                continue;
            }

            // 選択されたメニューに応じて業務処理を分岐
            switch (select) {

                case Constants.MENU_DEPOSIT:
                    // 入金処理
                    int depo = readInt(Constants.MSG_INPUT_DEPOSIT);
                    if (target.deposit(depo)) {
                        System.out.println(Constants.MSG_DEPOSIT_DONE);
                    } else {
                        // deposit が false を返した場合（金額不正の場合）
                    	System.out.println(Constants.MSG_AMOUNT_INVALID); 
                    }
                    break;

                case Constants.MENU_WITHDRAW:
                    // 出金処理
                    int draw = readInt(Constants.MSG_INPUT_WITHDRAW);
                    // 残高確認を行い、出金可否を判定
                    if (target.withdraw(draw)) {
                        System.out.println(Constants.MSG_WITHDRAW_DONE);
                    } else {
                    	if (draw <= 0) {
                            System.out.println(Constants.MSG_AMOUNT_INVALID);
                       } else {
                            System.out.println(Constants.MSG_WITHDRAW_FAIL);
                       }
                   }
                   break;

                case Constants.MENU_BALANCE:
                    // 残高照会・履歴表示
                    showAccountInfo(target);
                    break;

                default:
                    // ここには到達しないはずだが、念のため記述
                    System.out.println(Constants.MSG_INVALID_INPUT);
                    break;
            }
        }

        sc.close();
    }

    // ---- 共通メソッド ----

    /**
     * 整数入力を安全に読み取る。
     * 不正な入力（非数）があった場合、エラーメッセージを表示して再入力を促す。
     * 
     * @param message 入力前に表示するメッセージ
     * @return ユーザーが入力した整数値
     */
    private static int readInt(String message) {
        while (true) {
            if (!message.isEmpty()) {
                System.out.print(message);
            }
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(Constants.MSG_INVALID_INPUT);
            }
        }
    }

    /**
     * 1以上の整数入力を安全に読み取る。
     * 0や負の値、不正な入力があった場合、再入力を促す。
     * 
     * @param message 入力前に表示するメッセージ
     * @return ユーザーが入力した 1以上の整数値
     */
    private static int readPositiveInt(String message) {
        while (true) {
            // readIntを使って、まずは正しい整数形式で取得する
            int value = readInt(message);

            // 業務上のバリデーション: 1以上であることを確認する
            if (value > 0) {
                return value; // 1以上ならその値を返して終了
            }
            // 0以下の場合、エラーメッセージを表示してループを継続
            System.out.println(Constants.MSG_INVALID_INPUT);
        }
    }

    /**
     * 名前の入力を読み取る。
     * 空文字や空白のみの名前は弾き、エラーメッセージを表示して再入力を促す。
     * 
     * @param message 入力前に表示するメッセージ
     * @return 入力された、空白ではない名前の文字列
     */
    private static String readName(String message) {
        while (true) {
            System.out.print(message);
            String name = sc.nextLine().trim();

            if (!name.isEmpty()) {
                return name;
            }
            System.out.println(Constants.MSG_INVALID_NAME);
        }
    }

    /**
     * 指定した口座番号の Bank インスタンスをリストから検索して返す。
     * 
     * @param list   検索対象の Bank リスト
     * @param number 検索する口座番号
     * @return 該当する Bank インスタンス。見つからない場合は null。
     */
    private static Bank findAccount(List<Bank> list, int number) {
        for (Bank b : list) {
            if (b.getAccount().getAccountNumber() == number) {
                return b;
            }
        }
        return null;
    }

    /** メニュー一覧をコンソールに出力する。 */
    private static void printMenu() {
        System.out.println();
        System.out.println(Constants.MSG_MENU);
        System.out.println(Constants.MSG_MENU1);
        System.out.println(Constants.MSG_MENU2);
        System.out.println(Constants.MSG_MENU3);
        System.out.println(Constants.MSG_MENU4);
        System.out.print(Constants.MSG_MENU_SELECT);
    }

    /**
     * 口座の名義・残高・取引履歴をまとめてコンソールに表示する。
     * 
     * @param bank 表示対象の Bank インスタンス
     */
    private static void showAccountInfo(Bank bank) {
        Account acc = bank.getAccount();
        System.out.println(Constants.MSG_ACCOUNT_HOLDER + acc.getHolderName());
        System.out.println(Constants.MSG_BALANCE + acc.getBalance() + "円");
        bank.printHistory();
    }
}