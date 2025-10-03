package academy.service;

import academy.util.ConsoleUtils;
import academy.util.InputUtils;
import academy.config.AppConfig;
import academy.model.*;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import academy.model.WordCategory;
import academy.model.GameDifficult;

public class GameSession {
    public void startInteractive(AppConfig appConfig) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Добро пожаловать в игру Виселица!");
        while (true) {
            System.out.println("""
                1. Начать игру
                2. Выйти

                Введите цифру:
                """);
            int menuChoice = InputUtils.safeReadInt(scanner);
            if (menuChoice == 1) {
                ConsoleUtils.clearConsole();
                System.out.println("""
                    Выберете уровень сложности:
                    1. Легкий
                    2. Нормальный
                    3. Сложный

                    Введите цифру:
                    """);

                GameDifficult difficult = GameDifficult.fromNumber(InputUtils.safeReadInt(scanner));
                ConsoleUtils.clearConsole();
                System.out.println("Выбрана сложность: " + difficult.name());

                System.out.println("""
                    Выберете категорию слова:
                    1. Животные
                    2. Транспорт
                    3. Люди
                    4. Места
                    5. Искусство
                    6. Предметы
                    7. Еда

                    Введите цифру:
                    """);
                WordCategory category = WordCategory.fromNumber(InputUtils.safeReadInt(scanner));
                ConsoleUtils.clearConsole();
                System.out.println("Выбрана категория: " + category.getDescription());

                var categoryObj = appConfig.words().stream()
                    .filter(c -> c.category().equalsIgnoreCase(category.getDescription()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Нет слов в выбранной категории: " + category));

                var wordObj = categoryObj.elements().get(new Random().nextInt(categoryObj.elements().size()));
                HangmanGame game = new HangmanGame(wordObj.word(), wordObj.hint(), difficult);
                playInteractive(game);
            } else {
                break;
            }
        }
    }

    public void playInteractive(HangmanGame game) {
        Scanner scanner = new Scanner(System.in, "CP866");

        System.out.println("Угадайте слово. Максимум ошибок: " + game.getMaxAttempts());
        System.out.println("Длина: " + game.getGuessedWord().length());

        while (!game.isGameOver()) {

            System.out.println(HangmanArt.getStage(game.getDifficult(), game.getMistakes()));
            System.out.println("Слово: " + game.getState());
            System.out.println("Ошибки: " + game.getMistakes() + "/" + game.getMaxAttempts());
            System.out.print("Введите букву (или !hint для подсказки): ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                ConsoleUtils.clearConsole();
                System.out.println("Введите хотя бы одну букву");
                continue;
            }

            if (input.equalsIgnoreCase("!hint")) {
                ConsoleUtils.clearConsole();
                System.out.println(game.getHint());
                continue;
            }

            if (input.length() > 1) {
                ConsoleUtils.clearConsole();
                System.out.println("Введите только одну букву или !hint для подсказки");
                continue;
            }

            char letter = input.charAt(0);
            boolean result = game.guess(letter);

            if (result) {
                System.out.println("Верно!");
            } else {
                System.out.println("Неверно");
            }

            ConsoleUtils.clearConsole();
        }
        System.out.println(HangmanArt.getStage(game.getDifficult(), game.getMistakes()));
        if (game.isWordGuessed()) {
            System.out.println("Поздравляем!!! Вы угадали слово: " + game.getGuessedWord());
        } else {
            System.out.println("Вы проиграли!!! Загаданное слово: " + game.getGuessedWord());
        }
    }

    public void startNonInteractive(AppConfig appConfig) {
        if (appConfig.words().size() != 1 || appConfig.words().get(0).elements().size() != 2) {
            System.out.println("Неверный формат тестовых данных");
            return;
        }

        var category = appConfig.words().get(0);
        var wordObj = category.elements().get(0);
        var userInputObj = category.elements().get(1);

        String word = wordObj.word();
        String userInput = userInputObj.word();

        char[] guessedState = new  char[word.length()];
        Arrays.fill(guessedState, '*');

        for (char charUserInput: userInput.toCharArray()) {
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == charUserInput) {
                    guessedState[i] = charUserInput;
                }
            }
        }

        String state = new String(guessedState);
        System.out.println(state + (!state.contains("*") ? ";POS" : ";NEG"));
    }
}
