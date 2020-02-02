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

    public FlashCard createAndStoreCard(String term, String definition) {
        FlashCard card = new FlashCard(term, definition);
        cards.add(card);
        return card;
    }

    public void addCard(FlashCard flashCard) {
        cards.add(flashCard);
    }

    public void setCards(List<FlashCard> cards) {
        this.cards = cards;
    }
}
