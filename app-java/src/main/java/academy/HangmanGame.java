package academy;

import java.util.ArrayList;
import java.util.Arrays;

public class HangmanGame {

    private String guessedWord;
    private int maxAttempts;
    private int mistakes;
    private char[] guessedState;
    private GameDifficult difficult;;

    public HangmanGame(GameDifficult difficult, AppConfig config) {
        this.difficult = difficult;
        ArrayList<String> dict = new ArrayList<>(Arrays.asList(config.words()));

        guessedWord = GameUtil.getWord(dict)
                .orElseThrow(() -> new IllegalArgumentException("Словарь пуст! Нельзя начать игру!!"))
                .trim()
                .toLowerCase();

        this.guessedState = new char[guessedWord.length()];
        this.mistakes = 0;
        this.maxAttempts = difficult.getMaxAttempts();
        Arrays.fill(guessedState, '*');
    }

    public GuessResult guess(char letter) {
        boolean flag = false;
        for (int i = 0; i < guessedWord.length(); i++) {
            if (guessedWord.charAt(i) == letter) {
                guessedState[i] = letter;
                flag = true;
            }
        }

        if (flag) {
            return GuessResult.CORRECT;
        } else {
            this.mistakes++;
            return GuessResult.INCORRECT;
        }
    }

    public String getState() {
        return new String(guessedState);
    }

    public boolean isGameOver() {
        return mistakes >= maxAttempts || isWordGuessed();
    }

    public boolean isWordGuessed() {
        for (int i = 0; i < guessedState.length; i++) {
            if (guessedState[i] == '*') {
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

    public GameDifficult getDifficult() {
        return difficult;
    }
}
