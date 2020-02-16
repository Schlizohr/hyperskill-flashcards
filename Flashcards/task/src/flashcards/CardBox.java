package flashcards;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CardBox {
    private static List<FlashCard> cards = new LinkedList<>();
    ;

    public CardBox() {
    }

    public List<FlashCard> getCards() {
        return cards;
    }

    public FlashCard createAndStoreCard(String term, String definition) {
        FlashCard card = new FlashCard(term, definition);
        if (termDoesNotExist(card.getTerm())) {
            cards.add(card);
        }
        return card;
    }

    public boolean removeCard(String term) {
        return cards.removeAll(cards.stream().filter(c -> c.getTerm().equals(term))
                .collect(Collectors.toList()));
    }

    public boolean termDoesNotExist(String term) {
        return cards.stream().noneMatch(c -> c.getTerm().equals(term));
    }

    public boolean definitionDoesNotExist(String definition) {
        return cards.stream().noneMatch(c -> c.getDefinition().equals(definition));
    }

    public FlashCard getRandomCard() {
        return cards.get(new Random().nextInt(cards.size()));
    }

    public void updateCards(String term, String definition) {
        cards.stream().filter(c -> c.getTerm().equals(term)).findFirst().ifPresentOrElse(
                (c1) -> c1.setDefinition(definition), () -> cards.add(new FlashCard(term, definition)));
    }

    public List<FlashCard> getHardestCard() {
        try {
            return cards.stream().filter(c -> c.getErrors() == cards.stream().max(Comparator.comparingInt(FlashCard::getErrors)).get().getErrors()).filter(c -> c.getErrors() > 0).collect(Collectors.toList());
        } catch (Error e) {
            return new LinkedList<>();
        }
    }

    public void resetStats() {
        cards.forEach(FlashCard::resetErrors);
    }
}
