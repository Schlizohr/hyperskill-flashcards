package flashcards;

import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    CardBox cardBox = new CardBox();

    public static void main(String[] args) {
        Main main = new Main();
        main.all();
    }

    private void all() {
        String s = "wrong...";
        if (cardBox.createAndStoreCard(scanner.nextLine().trim(),
                scanner.nextLine().trim())
                .validateAnswer(scanner.nextLine().trim())) {
            s = "right!";
        }
        System.out.println("Your answer is " + s);
    }
}
