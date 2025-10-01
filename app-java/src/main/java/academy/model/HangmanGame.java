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
    private GameDifficult gameDifficult;
    private String hint;
    private WordCategory wordCategory;
    private final AppConfig config;

    public HangmanGame(AppConfig config) {

        this.config = config;
        if (config.words().length == 0) {
            throw new IllegalArgumentException("Словарь пуст!");
        }
    }

    public void init(GameDifficult difficult, WordCategory wordCategory) {
        this.gameDifficult = difficult;
        this.wordCategory = wordCategory;


        WordCategorizer wordCategorizer = new WordCategorizer(config.words());
        List<String> dictionary = wordCategorizer.getByCategory(wordCategory);

        if (dictionary.isEmpty()) {
            throw new IllegalArgumentException("Нет слов в выбранной категории: " + wordCategory);
        }

        this.guessedWord = dictionary.get(new Random().nextInt(dictionary.size()));
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
        this.gameDifficult = GameDifficult.EASY; // или другой по умолчанию
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

    public WordCategory getCategory() {
        return wordCategory;
    }

    public void setCategory(WordCategory category) {
        this.wordCategory = category;
    }

    public GameDifficult getDifficult() {
        return gameDifficult;
    }
}
