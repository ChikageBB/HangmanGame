package academy.model;

import java.util.Arrays;



public class HangmanGame {

    private final String guessedWord;
    private final int maxAttempts;
    private int mistakes;
    private final char[] guessedState;
    private final GameDifficult gameDifficult;
    private final String hint;


    public HangmanGame(String guessedWord, String hint, GameDifficult gameDifficult) {
        if (guessedWord == null || guessedWord.isEmpty()) {
            throw new IllegalArgumentException("Слово не может быть пустым");
        }

        this.guessedWord = guessedWord;
        this.hint = hint;
        this.gameDifficult = gameDifficult;
        this.maxAttempts = gameDifficult.getMaxAttempts();
        this.mistakes = 0;
        this.guessedState = new char[guessedWord.length()];
        Arrays.fill(guessedState, '*');
    }

    public boolean guess(char letter) {
        boolean guessed = false;

        letter = Character.toLowerCase(letter);
        for (int i = 0; i < guessedWord.length(); i++) {
            if (guessedWord.charAt(i) == letter) {
                guessedState[i] = letter;
                guessed = true;
            }
        }
        if (!guessed) {
            mistakes++;
        }
        return guessed;
    }

    public String getState() {
        return new String(guessedState);
    }

    public boolean isGameOver() {
        return mistakes >= maxAttempts || isWordGuessed();
    }

    public boolean isWordGuessed() {
        for (char c : guessedState) {
            if (c == '*') {
                return false;
            }
        }
        return true;
    }

    public String getGuessedWord() {
        return guessedWord;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public int getMistakes() {
        return mistakes;
    }

    public char[] getGuessedState() {
        return guessedState;
    }

    public String getHint() {
        return hint;
    }

    public GameDifficult getDifficult() {
        return gameDifficult;
    }
}
