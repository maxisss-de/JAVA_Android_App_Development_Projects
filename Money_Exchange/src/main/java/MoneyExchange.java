//------------------------------------------------------------------------
/* 
Задача:
Название программы: "Money Exchange"
Программа используеться для размена монет с помощью ОДНОГО ЦИКЛА
*/
//------------------------------------------------------------------------

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Укажите на какую сумму вам насыпать монеток?  ");
        int summa = scanner.nextInt();
        System.out.println("Насыпаем монеток на сумму равную " + summa + " рублей");
        razmen(summa);

    }
    static void razmen(int summa){  // Метод рассчитывающий сколько монет насыпать

        while (summa >= 1) {
            if (summa/10>=1) {
                System.out.println("Примите десять - 10 рублей");   summa-=10;
            }
            else if (summa/5>=1) {
                System.out.println("Примите пять - 5 рублей");    summa-=5;
            }
            else if (summa/2>=1) {
                System.out.println("Примите два - 2 рублей");    summa-=2;
            }
            else if (summa/1>=1) {
                System.out.println("Примите один - 1 рублей");    summa-=1;
            }
        }

        System.out.println("Копейки оставим себе...");
    }
}

//------------------------------- КОНЕЦ РАЗМЕНА-------------------------------
