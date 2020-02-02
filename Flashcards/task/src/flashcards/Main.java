package flashcards;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    private void createCard(Integer i) {
        System.out.println("The card #" + i);
        String term = askForTerm();
        System.out.println("The definition of the card #" + i);
        String def = askForDefinition();
        cardBox.createAndStoreCard(term, def);
    }

    private String askForTerm() {
        String term = scanner.nextLine().trim();
        if (!cardBox.termDoesNotExist(term)) {
            System.out.println("The card \"" + term + "\" already exists. Try again:");
            term = askForTerm();
        }
        return term;
    }

    private String askForDefinition() {
        String def = scanner.nextLine().trim();
        if (!cardBox.definitionDoesNotExist(def)) {
            System.out.println("The definition \"" + def + "\" already exists");
            def = askForDefinition();
        }
        return def;
    }

    private void askDefinitions() {
        for (FlashCard card : cardBox.getCards()) {
            askDefinition(card);
        }
    }

    private void askDefinition(FlashCard card) {
        System.out.println("Print the definition of \"" + card.getTerm() + "\":");
        String answer = scanner.nextLine().trim();
        if (card.validateAnswer(answer)) {
            System.out.println("Correct answer");
        } else {
            String s = "`Wrong answer`, `The correct one is \"" + card.getDefinition() + "\"`,";
            List<FlashCard> collect = cardBox.getCards().stream().filter(c -> c.validateAnswer(answer) && !c.getTerm().equals(card.getTerm())).collect(Collectors.toList());
            if (collect.size() > 0) {
                s += " you've just written the definition of \"" + collect.get(0).getTerm() + "\"";
            }
            System.out.println(s);
        }

    }
}
