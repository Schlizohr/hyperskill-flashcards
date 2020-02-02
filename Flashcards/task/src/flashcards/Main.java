package flashcards;

import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    CardBox cardBox = new CardBox();

    public static void main(String[] args) {
        Main main = new Main();
        main.createCards();
        main.askDefinitions();
    }

    private void createCards() {
        System.out.println("Input the number of cards:");
        int numberOfCards = scanner.nextInt();
        scanner.nextLine();
        for (int i = 1; i <= numberOfCards; i++) {
            createCard(i);
        }
    }

    private void createCard(int i) {
        System.out.println("The card #" + i);
        String term = scanner.nextLine().trim();
        System.out.println("The definition of the card #" + i);
        String def = scanner.nextLine().trim();
        cardBox.createAndStoreCard(term, def);
    }

    private void askDefinitions() {
        for (FlashCard card : cardBox.getCards()) {
            askDefinition(card);
        }
    }

    private void askDefinition(FlashCard card) {
        System.out.println("Print the definition of \"" + card.getTerm() + "\":");
        if (card.validateAnswer(scanner.nextLine().trim())) {
            System.out.println("Correct answer");
        } else {
            System.out.println("Wrong answer. The correct one is \"" + card.getDefinition() + "\".");
        }
    }
}
