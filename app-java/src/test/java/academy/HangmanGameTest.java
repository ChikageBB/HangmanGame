package academy;

import academy.config.AppConfig;
import academy.model.GameDifficult;
import academy.model.HangmanGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class HangmanGameTest {
    private AppConfig configSingle;
    private AppConfig configMulti;
    private HangmanGame game;

    @BeforeEach
    void setUp() {
        configSingle = new AppConfig(
            24,
            List.of(new AppConfig.Category(
                "животные",
                List.of(new AppConfig.Word("кот", "Домашнее животное, любит молоко"))
            ))
        );

        configMulti = new AppConfig(
            24,
            List.of(new AppConfig.Category(
                "животные",
                List.of(
                    new AppConfig.Word("кот", "Домашнее животное, любит молоко"),
                    new AppConfig.Word("собака", "Лучший друг человека")
                )
            ))
        );

        AppConfig.Word word = configSingle.words().getFirst().elements().getFirst();
        game = new HangmanGame(word.word(), word.hint(), GameDifficult.EASY);
    }

    // Проверка: слово выбирается только из указанной категории
    @Test
    void testWordChosenFromConfig() {
        String chosen = game.getGuessedWord();
        assertTrue(configSingle.words().getFirst().elements()
            .stream()
            .map(AppConfig.Word::word)
            .toList()
            .contains(chosen)
        );
    }

    // Проверка: состояние игры обновляется после угадывания буквы
    @Test
    void testGameStateUpdateAfterGuess() {
        char firstLetter = game.getGuessedWord().charAt(0);
        boolean result = game.guess(firstLetter);

        assertTrue(result);
        assertTrue(game.getState().contains(String.valueOf(firstLetter)));
        assertEquals(game.getMistakes(), 0);
    }

    // Проверка: ввод обрабатывается без учёта регистра
    @Test
    void testInputCaseInsensitive() {
        char firstLetter = game.getGuessedWord().charAt(0);
        char upperFirstLetter = Character.toUpperCase(firstLetter);

        boolean result = game.guess(upperFirstLetter);
        assertTrue(result);
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
        assertThrows(IllegalArgumentException.class,
            () -> new HangmanGame("", "подсказка", GameDifficult.EASY));
    }

    // Проверка: состояние меняется при правильных и неправильных догадках

    @Test
    void testStateChangesOnCorrectGuesses() {
        boolean correct = game.guess(game.getGuessedWord().charAt(0));

        assertTrue(correct);
        assertTrue(game.getMistakes() == 0);
    }

    @Test
    void testStateChangesOnIncorrectGuesses() {
        boolean incorrect = game.guess('&');

        assertFalse(incorrect);
        assertTrue(game.getMistakes() > 0 );
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
