package academy.model;

import academy.AppConfig;
import academy.HintDictionary;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HangmanGame {

    private String guessedWord;
    private int maxAttempts;
    private int mistakes;
    private char[] guessedState;
    private GameDifficult difficult;
    private String hint;

    public HangmanGame(GameDifficult difficult, AppConfig config) {
        this.difficult = difficult;

        if (config.words().length == 0) {
            throw new IllegalArgumentException("Словарь пуст!");
        }

        List<String> dict = Arrays.asList(config.words());

        if (dict.stream().anyMatch(word -> word == null || word.trim().isEmpty())) {
            throw new IllegalArgumentException("Некорректное слово в словаре!");
        }

        this.guessedWord = dict.get(new Random().nextInt(dict.size()));
        this.guessedState = new char[guessedWord.length()];
        this.mistakes = 0;
        this.maxAttempts = difficult.getMaxAttempts();
        this.hint = HintDictionary.getHint(guessedWord);
        Arrays.fill(guessedState, '*');
    }

    public GuessResult guess(char letter) {
        boolean flag = false;
        letter = Character.toLowerCase(letter);
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

    public String getHint() {
        return hint;
    }

    public GameDifficult getDifficult() {
        return difficult;
    }
}
