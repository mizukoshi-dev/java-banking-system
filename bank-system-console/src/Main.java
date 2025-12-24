/**
 * CSVファイルに記載された処理を実行するためのエントリポイントクラス。
 * 処理完了後、システムを終了する。
 */
public class Main {

    public static void main(String[] args) {

        System.out.println(Constants.MSG_SYSTEM_START);

        // BankCsvOperation で、口座の読み込み、取引の実行、および出力を行う
        BankCsvOperation csvOp = new BankCsvOperation();
        csvOp.loadData();

        // 処理完了後、システムを終了する
        System.out.println(Constants.MSG_EXIT_CSV);
    }
}