package flashcards;

public class FlashCard {
    private String term;
    private String definition;

    public FlashCard(String term, String definition) {
        this.term = term;
        this.definition = definition;
    }

    public FlashCard() {
    }

    public boolean validateAnswer(String answer){
        return definition.equals(answer);
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }

    @Override
    public String toString() {
        return "Card:\n" + term + '\n' +
                "Definition:\n" + definition + '\n';
    }
}
