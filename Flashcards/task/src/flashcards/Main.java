package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private Scanner scanner = new Scanner(System.in);
    private CardBox cardBox = new CardBox();
    private List<String> log = new LinkedList<>();
    private String input;
    private String output;

    public static void main(String[] args) {
        new Main(args).runGame();
    }

    public Main(String[] args) {
        if (args.length > 0) {
            processArguments(args);
            if (input != null) {
                try {
                    importCardsFromFile(input);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void processArguments(String[] args) {
        switch (args.length) {
            case 4:
                extractFile(args[2], args[3]);
            case 2:
                extractFile(args[0], args[1]);
        }
    }

    private void extractFile(String argument, String value) {
        if (argument.contains("import")) input = value;
        if (argument.contains("export")) output = value;
    }

    private void runGame() {
        boolean run = true;
        while (run) {
            write("Input the action (add, remove, import, export, ask, log, hardest card, exit):");
            try {
                switch (Actions.valueOf(readLine().trim().replace(" ", "_").toUpperCase())) {
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
                    case LOG:
                        log();
                        break;
                    case HARDEST_CARD:
                        hardestCard();
                        break;
                    case RESET_STATS:
                        cardBox.resetStats();
                        write("Card statistics has been reset");
                        break;
                    case EXIT:
                        write("Bye, bye!");
                        if (output != null) exportCardsToFile(output);
                        run = false;
                        break;
                }
            } catch (FileNotFoundException e) {
                write("File not found.");
            }
        }
    }

    private void createCard() {
        write("The card:");
        String term = askForTerm();
        if (term == null) return;
        write("The definition of the card:");
        String def = askForDefinition();
        if (def == null) return;
        FlashCard card = cardBox.createAndStoreCard(term, def);
        write("The pair " + card.toPair() + " has been added.");
    }

    private void removeCard() {
        write("The card:");
        String term = readLine().trim();
        if (cardBox.removeCard(term)) {
            write("The card has been removed.");
        } else {
            write("Can't remove \"" + term + "\": there is no such card.");
        }

    }

    private String askForTerm() {
        String term = readLine().trim();
        if (!cardBox.termDoesNotExist(term)) {
            write("The card \"" + term + "\" already exists. Try again:");
            return null;
        }
        return term;
    }

    private String askForDefinition() {
        String def = readLine().trim();
        if (!cardBox.definitionDoesNotExist(def)) {
            write("The definition \"" + def + "\" already exists");
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
        write("How many times to ask?");
        int numberOfQuestions = scanner.nextInt();
        readLine();
        for (int i = 0; i < numberOfQuestions; i++) {
            askCard(cardBox.getRandomCard());
        }
    }

    private void askCard(FlashCard card) {
        write("Print the definition of \"" + card.getTerm() + "\":");
        String answer = readLine().trim();
        if (card.validateAnswer(answer)) {
            write("Correct answer");
        } else {
            String s = "`Wrong answer`, `The correct one is \"" + card.getDefinition() + "\"`,";
            List<FlashCard> collect = cardBox.getCards().stream().filter(c -> c.getDefinition().equals(answer) && !c.getTerm().equals(card.getTerm())).collect(Collectors.toList());
            if (collect.size() > 0) {
                s += " you've just written the definition of \"" + collect.get(0).getTerm() + "\"";
            }
            write(s);
        }
    }

    private void importCards() throws FileNotFoundException {
        write("import");
        importCardsFromFile(readLine().trim());
    }

    private void importCardsFromFile(String file) throws FileNotFoundException {
        Scanner reader = new Scanner(new FileReader(file));
        int i = 0;
        while (reader.hasNextLine()) {
            cardBox.updateCards(reader.nextLine().trim(), reader.nextLine().trim());
            i++;
        }
        write("" + i + " cards have been loaded.");

    }

    private void exportCards() throws FileNotFoundException {
        write("File name:");
        exportCardsToFile(readLine().trim());
    }

    private void exportCardsToFile(String file) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(new File(file));
        List<FlashCard> cards = cardBox.getCards();
        cards.forEach(c -> printWriter.println(c.export()));
        write("" + cards.size() + " cards have been saved.");
        printWriter.flush();
        printWriter.close();
    }

    private void hardestCard() {
        List<FlashCard> hardestCard = cardBox.getHardestCard();
        if (hardestCard.size() == 0) {
            write("There are no cards with errors.");
        } else if (hardestCard.size() == 1) {
            FlashCard flashCard = hardestCard.get(0);
            write("The hardest card is \"" + flashCard.getTerm() + "\". You have " + flashCard.getErrors()
                    + " errors answering them.");
        } else {
            FlashCard flashCard = hardestCard.get(0);
            write("The hardest cards are \"" + hardestCard.stream().map(FlashCard::getTerm)
                    .collect(Collectors.joining("\", \"")) + "\". You have " + flashCard.getErrors()
                    + " errors answering them.");
        }
    }

    private void log() throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(new File(readLine().trim()));
        log.forEach(printWriter::println);
        printWriter.flush();
        write("yolo\nLog has been saved.");
        log.clear();
    }


    private String readLine() {
        String s = scanner.nextLine();
        log.add(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE) + ": " + s);
        return s;
    }

    private void write(String s) {
        String format = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + ": " + s;
        log.add(format);
        System.out.println(format);
    }
}
