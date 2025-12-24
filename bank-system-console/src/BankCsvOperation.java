import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CSVファイルからのデータ読み込みと、それに基づく銀行操作を担当するクラス。
 * コンソール版の操作ロジックとは完全に分離している。
 */
public class BankCsvOperation {

	/**
     * 口座情報と取引履歴を読み込むメインメソッド。
     * @return 処理後の Bank インスタンスのリスト。
     */
    public List<Bank> loadData() {
        List<Bank> list = loadAccounts();
        loadTransactions(list);
        return list;
    }

    /**
     * account.csv を読み込み、口座インスタンスを生成する。
     * CSV形式エラー（項目数不正、非数など）が発生した場合、
     * 読み込み済みの口座データも含めてすべて破棄し、空のリストを返す（取引の中断）。
     */
    private List<Bank> loadAccounts() {
        List<Bank> list = new ArrayList<>();
        List<String> accountInfoMessages = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(Constants.FILE_ACCOUNT_CSV))) {
            String line;
            
            // CSV内の行順を口座番号として使用
            int currentAccountNumber = 1; 
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                
                // 項目数チェック: 2以外であれば例外をスローし、読み込み中断
                if (data.length != 2) { 
                    throw new IOException(Constants.ERR_MSG_ACCOUNT_ITEM_COUNT + line); 
                }

                String name = data[0].trim();
                
                // 初期残高の数値変換。非数であればNumberFormatExceptionが発生
                int balance = Integer.parseInt(data[1].trim());

                Bank bank = new Bank(name, balance, currentAccountNumber);
                list.add(bank);
                
                accountInfoMessages.add(String.format(
                    Constants.ACCOUNT_INFO_FORMAT, 
                    currentAccountNumber, name, balance
                ));
                currentAccountNumber++;
            }
            
            // 口座情報を全て出力し、開設完了メッセージを表示
            for (String msg : accountInfoMessages) {
                System.out.println(msg);
            }
            System.out.println(Constants.MSG_ACCOUNT_OPENED);

        } catch (IOException e) {
            // 項目数不正など
            list.clear();// 既に読み込んだ正しい口座データも破棄
            System.out.println(Constants.ERR_MSG_DETAIL_HEADER + e.getMessage());
            System.out.println(Constants.MSG_CSV_LOAD_ERROR + Constants.FILE_ACCOUNT_CSV);
            
        } catch (NumberFormatException e) {
            // 残高フィールドが非数だった場合
            list.clear();// 既に読み込んだ正しい口座データも破棄
            System.out.println(Constants.ERR_MSG_DETAIL_HEADER + Constants.ERR_MSG_INITIAL_BALANCE_FORMAT);
            System.out.println(Constants.MSG_CSV_LOAD_ERROR + Constants.FILE_ACCOUNT_CSV);
        }
        return list;
    }

    /**
     * transaction.csv を読み込み、取引を実行する。
     * CSV形式エラー（項目数不正、不正な取引タイプ、非数）が発生した場合、
     * 処理を即座に中断し、それ以降の取引をすべてスキップする。
     */
    private void loadTransactions(List<Bank> accounts) {
        try (BufferedReader br = new BufferedReader(new FileReader(Constants.FILE_TRANSACTION_CSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                
                // 項目数チェック: 3以外であれば例外をスローし、取引中断
                if (data.length != 3) {
                	throw new IOException(Constants.ERR_MSG_TRANSACTION_ITEM_COUNT + line);
                }
                
                // 口座番号と金額の数値変換。非数であればNumberFormatExceptionが発生し中断
                int number = Integer.parseInt(data[0].trim());
                String type = data[1].trim();
                int amount = Integer.parseInt(data[2].trim());
                
                // 対象口座を検索。見つからなければ当該取引をスキップ
                Bank target = findAccountInList(accounts, number);
                if (target == null) {
                    continue;
                }

                // 取引タイプを判別し、取引を実行
                if (Constants.TRANSACTION_DEPOSIT_CSV.equals(type)) {
                	// 入金処理を実行。depositが成功した場合のみメッセージ出力
                	if (target.deposit(amount)) { 
                		System.out.printf(Constants.MSG_DEPOSIT_FORMAT, number, amount);
                	}
                	
                } else if (Constants.TRANSACTION_WITHDRAW_CSV.equals(type)) {
                	// 出金処理を実行。withdrawが成功した場合のみメッセージ出力
                	if (target.withdraw(amount)) {
                		System.out.printf(Constants.MSG_WITHDRAW_FORMAT, number, amount);
                	}
                	
                } else if (Constants.TRANSACTION_GET_BALANCE_CSV.equals(type)) {
                	// 残高照会を実行
                	showAccountInfo(target);
                	
                }else {
                // 許可されていない不正な取引タイプが検出された場合、例外をスローし取引中断
                throw new IOException(Constants.ERR_MSG_TRANSACTION_TYPE + type + " (行データ: " + line + ")");
                }
            }
        } catch (IOException e) {
        	// 項目数不正や不正な取引タイプなどによる中断
            System.out.println(Constants.ERR_MSG_DETAIL_HEADER + e.getMessage());
            System.out.println(Constants.MSG_CSV_LOAD_ERROR + Constants.FILE_TRANSACTION_CSV);
            
        } catch (NumberFormatException e) {
        	// 口座番号や金額が非数であったことによる中断
            System.out.println(Constants.ERR_MSG_DETAIL_HEADER + Constants.ERR_MSG_AMOUNT_FORMAT);
            System.out.println(Constants.MSG_CSV_LOAD_ERROR + Constants.FILE_TRANSACTION_CSV);
        }
    }

    /**
     * 指定した口座番号の Bank インスタンスをリストから検索して返す。
     * @param list 検索対象の口座リスト
     * @param number 検索する口座番号
     * @return 該当する Bank インスタンス。見つからない場合は null。
     */
    private Bank findAccountInList(List<Bank> list, int number) {
        for (Bank b : list) {
            if (b.getAccount().getAccountNumber() == number) {
                return b;
            }
        }
        return null;
    }
    
    /**
     * 残高照会と取引履歴表示を行う。
     * CSV取引の残高照会コマンド専用の出力形式を実装。
     * @param bank 表示対象の Bank インスタンス
     */
    private void showAccountInfo(Bank bank) {
        Account acc = bank.getAccount();
        
        System.out.printf("残高照会 (%s) : %d円%n", acc.getHolderName(), acc.getBalance());
        
        System.out.printf("取引履歴 (口座番号: " + Constants.ACCOUNT_NUMBER_FORMAT_7DIGIT + ")%n", acc.getAccountNumber());
        
        // 取引履歴の詳細情報を出力
        for (Transaction t : bank.getHistory()) {
            System.out.println(t.getDetail());
        }
    }
}