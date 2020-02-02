package flashcards;

import java.util.LinkedList;
import java.util.List;

public class CardBox {
    private List<FlashCard> cards;

    public CardBox() {
        cards = new LinkedList<>();
    }

    public List<FlashCard> getCards() {
        return cards;
    }

    public void createAndStoreCard(String term, String definition) {
        FlashCard card = new FlashCard(term, definition);
        if (termDoesNotExist(card.getTerm())) {
            cards.add(card);
        }
    }

    public boolean termDoesNotExist(String term) {
        return cards.stream().noneMatch(c -> c.getTerm().equals(term));
    }

    public boolean definitionDoesNotExist(String definition) {
        return cards.stream().noneMatch(c -> c.getDefinition().equals(definition));
    }

    public void addCard(FlashCard flashCard) {
        cards.add(flashCard);
    }

    public void setCards(List<FlashCard> cards) {
        this.cards = cards;
    }
}
