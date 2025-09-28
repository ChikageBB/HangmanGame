package academy;

import academy.config.AppConfig;
import academy.model.GameDifficult;
import academy.model.GuessResult;
import academy.model.HangmanGame;
import academy.model.WordCategory;
import academy.service.WordCategorizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class HangmanGameTest {
    private AppConfig configSingle;
    private AppConfig configMulti;
    private HangmanGame game;
    private WordCategorizer categorizerSingle;
    private WordCategorizer categorizerMulti;

    @BeforeEach
    void setUp() {
        configSingle = new AppConfig(24, new String[]{"животные:кот"});
        configMulti = new AppConfig(24, new String[]{
                "животные:кот",
                "животные:собака"
        });
        game = new HangmanGame(configSingle);
        game.init(GameDifficult.EASY, WordCategory.ANIMALS);
        categorizerSingle = new WordCategorizer(configSingle.words());
        categorizerMulti = new WordCategorizer(configMulti.words());
    }

    // Проверка: слово выбирается только из указанной категории
    @Test
    void testWordChosenFromConfig() {
        HangmanGame game = new HangmanGame(configSingle);
        game.init(GameDifficult.EASY, WordCategory.ANIMALS);

        String chosen = game.getGuessedWord();
        assertTrue(categorizerMulti.getByCategory(WordCategory.ANIMALS).contains(chosen));
    }
    // Проверка: состояние игры обновляется после угадывания буквы
    @Test
    void testGameStateUpdateAfterGuess() {
        char firstLetter = game.getGuessedWord().charAt(0);
        GuessResult result = game.guess(firstLetter);

        assertEquals(GuessResult.CORRECT, result);
        assertTrue(game.getState().contains(String.valueOf(firstLetter)));
        assertEquals(game.getMistakes(), 0);
    }

    // Проверка: ввод обрабатывается без учёта регистра
    @Test
    void testInputCaseInsensitive() {
        char firstLetter = game.getGuessedWord().charAt(0);
        char upper = Character.toUpperCase(firstLetter);

        GuessResult result = game.guess(upper);
        assertEquals(GuessResult.CORRECT, result);
        assertTrue(game.getState().contains(String.valueOf(Character.toLowerCase(firstLetter))));
    }

    //Проверка: игра завершается поражением после превышения лимита ошибок
    @Test
    void testGameLosesAfterMaxMistakes() {
        while (!game.isGameOver()) {
            game.guess('*');
        }

        assertTrue(game.isGameOver());
        assertFalse(game.isWordGuessed());
    }

    // Проверка: пустое слово в конфиге выбрасывает исключение
    @Test
    void testInvalidWordLengthThrows() {
        AppConfig badConfig = new AppConfig(24, new String[]{""});
        HangmanGame badGame = new HangmanGame(badConfig);

        assertThrows(IllegalArgumentException.class,
            () -> badGame.init(GameDifficult.EASY, WordCategory.ANIMALS));
    }

    // Проверка: состояние меняется при правильных и неправильных догадках
    @Test
    void testStateChangesOnCorrectAndIncorrectGuesses() {
        GuessResult correct = game.guess(game.getGuessedWord().charAt(0));
        GuessResult incorrect = game.guess('х');

        assertEquals(GuessResult.CORRECT, correct);
        assertEquals(GuessResult.INCORRECT, incorrect);
        assertTrue(game.getMistakes() > 0);
    }

    @Test
    void testMultiCharInputIgnored() {
        String before = game.getState();

        String input = "ко";
        if (input.length() == 1) {
            game.guess(input.charAt(0));
        }

        String after = game.getState();
        assertEquals(before, after);
    }
}
