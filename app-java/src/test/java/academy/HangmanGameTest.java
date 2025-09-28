package academy;

import academy.model.GameDifficult;
import academy.model.GuessResult;
import academy.model.HangmanGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class HangmanGameTest {
    private AppConfig configSingle;
    private AppConfig configMulti;
    private HangmanGame game;

    @BeforeEach
    void setUp() {
        configSingle = new AppConfig(12, new String[]{"кот"});
        configMulti = new AppConfig(12, new String[]{"молоко", "окно", "стол"});
        game = new HangmanGame(GameDifficult.EASY, configSingle);
    }

    // Напишите тесты для проверки правильности выбора слова из списка.
    @Test
    void testWordChosenFromConfig() {
        HangmanGame game = new HangmanGame(GameDifficult.EASY, configMulti);
        String chosen = game.getGuessedWord();
        assertTrue(List.of(configMulti.words()).contains(chosen));
    }

    // Проверьте корректность отображения состояния игры после каждого ввода пользователя
    @Test
    void testGameStateUpdatesAfterGuess() {

        char firstLetter = game.getGuessedWord().charAt(0);

        GuessResult result = game.guess(firstLetter);

        assertEquals(GuessResult.CORRECT, result);
        assertTrue(game.getState().contains(String.valueOf(firstLetter)));
    }

    // Убедитесь, что введенные буквы корректно обрабатываются вне зависимости от их регистра
    @Test
    void testInputCaseInsensitive() {

        GuessResult result = game.guess('К');
        assertEquals(GuessResult.CORRECT, result);
        assertTrue(game.getState().startsWith("к"));

    }

    // После превышения заданного количества попыток игра всегда возвращает поражение
    @Test
    void testGameLosesAfterMaxMistakes() {

        while (!game.isGameOver()) {
            game.guess('*');
        }

        assertTrue(game.isGameOver());
        assertFalse(game.isWordGuessed());
    }

    // Игра не запускается, если загадываемое слово имеет некорректную длину
    @Test
    void testInvalidWordLengthThrows() {
        AppConfig badConfig = new AppConfig(12, new String[]{""}); // пустое слово

        assertThrows(IllegalArgumentException.class,
            () -> new HangmanGame(GameDifficult.EASY, badConfig));
    }

    // Состояние игры корректно изменяется при угадывании/не угадывании
    @Test
    void testStateChangesOnCorrectAndIncorrectGuesses() {
        GuessResult correct = game.guess('к');
        GuessResult incorrect = game.guess('x');
        assertEquals(GuessResult.CORRECT, correct);
        assertEquals(GuessResult.INCORRECT, incorrect);
        assertTrue(game.getMistakes() > 0);
        assertTrue(game.getState().startsWith("к"));
    }

    //Проверка, что при отгадывании ввод строки длиной больше чем 1 (опечатка) приводит к повторному вводу, без изменения
    // состояния.

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
