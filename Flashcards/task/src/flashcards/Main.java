package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private CardBox cardBox = new CardBox();

    public static void main(String[] args) throws FileNotFoundException {
        new Main().runGame();


    }

    private void runGame() {
        boolean run = true;
        while (run) {
            System.out.println("Input the action (add, remove, import, export, ask, exit):");
            try {
                switch (Actions.valueOf(scanner.nextLine().trim().toUpperCase())) {
                    case ADD:
                        createCard();
                        break;
                    case REMOVE:
                        removeCard();
                        break;
                    case ASK:
                        askCards();
                        break;
                    case IMPORT:
                        importCards();
                        break;
                    case EXPORT:
                        exportCards();
                        break;
                    case EXIT:
                        run = false;
                        System.out.println("Bye, bye!");
                        break;
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            }
        }
    }

    private void createCards() {
        System.out.println("Input the number of cards:");
        int numberOfCards = scanner.nextInt();
        scanner.nextLine();
        for (int i = 1; i <= numberOfCards; i++) {
            createCard();
        }
    }

    private void createCard() {
        System.out.println("The card:");
        String term = askForTerm();
        if (term == null) return;
        System.out.println("The definition of the card:");
        String def = askForDefinition();
        if (def == null) return;
        FlashCard card = cardBox.createAndStoreCard(term, def);
        System.out.println("The pair " + card.toPair() + " has been added.");
    }

    private void removeCard() {
        System.out.println("The card:");
        String term = scanner.nextLine().trim();
        if (cardBox.removeCard(term)) {
            System.out.println("The card has been removed.");
        } else {
            System.out.println("Can't remove \"" + term + "\": there is no such card.");
        }

    }

    private String askForTerm() {
        String term = scanner.nextLine().trim();
        if (!cardBox.termDoesNotExist(term)) {
            System.out.println("The card \"" + term + "\" already exists. Try again:");
            return null;
        }
        return term;
    }

    private String askForDefinition() {
        String def = scanner.nextLine().trim();
        if (!cardBox.definitionDoesNotExist(def)) {
            System.out.println("The definition \"" + def + "\" already exists");
            return null;
        }
        return def;
    }

    private void askDefinitions() {
        for (FlashCard card : cardBox.getCards()) {
            askCard(card);
        }
    }

    private void askCards() {
        System.out.println("How many times to ask?");
        int numberOfQuestions = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < numberOfQuestions; i++) {
            askCard(cardBox.getRandomCard());
        }
    }

    private void askCard(FlashCard card) {
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

    private void importCards() throws FileNotFoundException {
        System.out.println("import");
        Scanner reader = new Scanner(new FileReader(scanner.nextLine().trim()));
        int i = 0;
        while (reader.hasNextLine()) {
            cardBox.updateCards(reader.nextLine().trim(), reader.nextLine().trim());
            i++;
        }
        System.out.println("" + i + " cards have been loaded.");
    }

    private void exportCards() throws FileNotFoundException {
        System.out.println("File name:");
        PrintWriter printWriter = new PrintWriter(new File(scanner.nextLine()));
        List<FlashCard> cards = cardBox.getCards();
        cards.forEach(c -> printWriter.println(c.export()));
        System.out.println("" + cards.size() + " cards have been saved.");
        printWriter.flush();
        printWriter.close();
    }
}
