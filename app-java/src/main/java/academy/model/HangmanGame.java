package academy.model;

import academy.config.AppConfig;
import academy.service.HintDictionary;
import academy.service.WordCategorizer;
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
    private WordCategory category;
    private final AppConfig config;

    public HangmanGame(AppConfig config) {

        this.config = config;
        if (config.words().length == 0) {
            throw new IllegalArgumentException("Словарь пуст!");
        }
    }

    public void init(GameDifficult difficult, WordCategory category) {
        this.difficult = difficult;
        this.category = category;


        WordCategorizer wordCategorizer = new WordCategorizer(config.words());
        List<String> dict = wordCategorizer.getByCategory(category);

        if (dict.isEmpty()) {
            throw new IllegalArgumentException("Нет слов в выбранной категории: " + category);
        }

        this.guessedWord = dict.get(new Random().nextInt(dict.size()));
        this.guessedState = new char[guessedWord.length()];
        this.mistakes = 0;
        this.maxAttempts = difficult.getMaxAttempts();
        this.hint = HintDictionary.getHint(guessedWord);
        Arrays.fill(guessedState, '*');
    }

    public void initForTest(String word, int maxAttempts) {
        this.guessedWord = word;
        this.maxAttempts = maxAttempts;
        this.mistakes = 0;
        this.guessedState = new char[word.length()];
        Arrays.fill(guessedState, '*');
        this.hint = HintDictionary.getHint(word);
        this.difficult = GameDifficult.EASY; // или другой по умолчанию
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

    public WordCategory getCategory() {
        return category;
    }

    public void setCategory(WordCategory category) {
        this.category = category;
    }

    public GameDifficult getDifficult() {
        return difficult;
    }
}
