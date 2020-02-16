package flashcards;

public class FlashCard {
    private String term;
    private String definition;
    private int errors = 0;

    public FlashCard(String term, String definition) {
        this.term = term;
        this.definition = definition;
    }

    public FlashCard() {
    }

    public boolean validateAnswer(String answer){
        boolean equals = definition.equals(answer);
        if (!equals) errors++;
        return equals;
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getErrors() {
        return errors;
    }

    public void resetErrors() {
        this.errors = 0;
    }

    @Override
    public String toString() {
        return "Card:\n" + term + '\n' +
                "Definition:\n" + definition + '\n';
    }

    public String toPair() {
        return "(\"" + term + "\":\"" + definition + "\")";
    }

    public String export() {
        return term + "\n" + definition;
    }
}
