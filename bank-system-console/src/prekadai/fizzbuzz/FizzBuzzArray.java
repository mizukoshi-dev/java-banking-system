package prekadai.fizzbuzz;

/**
 * プレ課題（FizzBuzzArray）
 * 
 * @author 
 * @version 1.0
 *
 */
public class FizzBuzzArray {

	/**
	 * (余裕あればメソッド化やクラスのインスタンス化してください)
	 * 1から100までの数を順番に表示し、結果を配列に格納するプログラムを作成してください。<br>
	 * ただし、以下の条件を満たす場合には特定の文字列を配列に格納します。<br>
	 * 3で割り切れる場合： “Fizz”<br>
	 * 5で割り切れる場合： “Buzz”<br>
	 * 3と5の両方で割り切れる場合： “FizzBuzz”<br>
	 * 例）1から15までの数を表示すると以下のようになります：<br>
	 * 1<br>
	 * 2<br>
	 * Fizz<br>
	 * 4<br>
	 * Buzz<br>
	 * Fizz<br>
	 * 7<br>
	 * 8<br>
	 * Fizz<br>
	 * Buzz<br>
	 * 11<br>
	 * Fizz<br>
	 * 13<br>
	 * 14<br>
	 * FizzBuzz<br>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
        FizzBuzzArray app = new FizzBuzzArray();

        String[] result = app.createFizzBuzzArray();

        for (String s : result) {
            System.out.println(s);
        }
    }

    public String[] createFizzBuzzArray() {

        String[] array = new String[100];

        for (int i = 1; i <= 100; i++) {

            if (i % 15 == 0) {
                array[i - 1] = "FizzBuzz";
            } else if (i % 3 == 0) {
                array[i - 1] = "Fizz";
            } else if (i % 5 == 0) {
                array[i - 1] = "Buzz";
            } else {
                array[i - 1] = String.valueOf(i);
            }
        }

        return array;
    }
}