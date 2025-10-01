package academy.service;

import academy.config.AppConfig;
import academy.model.*;

import java.util.Random;
import java.util.Scanner;
import academy.model.WordCategory;
import academy.model.GameDifficult;

public class GameSession {


    private final HangmanGame game;

    public GameSession(HangmanGame game) {
        this.game = game;
    }

    public void startInteractive() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Добро пожаловать в игру Виселица!");
        while (true) {
            System.out.println("""
                1. Начать игру
                2. Выйти
                """);

            if (scanner.nextInt() == 1) {
                clearConsole();
                System.out.println("""
                    Выберете уровень сложности:
                    1. Легкий
                    2. Нормальный
                    3. Сложный
                    """);

                GameDifficult difficult = getDifficult(scanner);
                clearConsole();
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
                    """);
                WordCategory category = getCategory(scanner);
                clearConsole();
                System.out.println("Выбрана категория: " + category.getDescription());

                game.init(difficult, category);
                playInteractive();
            } else {
                break;
            }
        }
    }

    public void playInteractive() {
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
                clearConsole();
                System.out.println("Введите хотя бы одну букву");
                continue;
            }

            if (input.equalsIgnoreCase("!hint")) {
                clearConsole();
                System.out.println(game.getHint());
                continue;
            }

            char letter = input.charAt(0);
            GuessResult result = game.guess(letter);

            switch (result) {
                case INCORRECT -> System.out.println("Неверно!");
                case CORRECT -> System.out.println("Верно!");
            }
            clearConsole();
        }
        System.out.println(HangmanArt.getStage(game.getDifficult(), game.getMistakes()));
        if (game.isWordGuessed()) {
            System.out.println("Поздравляем!!! Вы угадали слово: " + game.getGuessedWord());
        } else {
            System.out.println("Вы проиграли!!! Загаданное слово: " + game.getGuessedWord());
        }
    }

    private static WordCategory getCategory(Scanner scanner) {
        return switch (scanner.nextInt()) {
            case 1 -> WordCategory.ANIMALS;
            case 2 -> WordCategory.TRANSPORT;
            case 3 -> WordCategory.PEOPLE;
            case 4 -> WordCategory.PLACES;
            case 5 -> WordCategory.ARTS;
            case 6 -> WordCategory.ITEMS;
            case 7 -> WordCategory.FOODS;
            default -> {
                System.out.println("Такой категории не существует. Выбрана случайная категория");
                WordCategory[] values = WordCategory.values();
                yield values[new Random().nextInt(values.length)];
            }
        };
    }

    private static GameDifficult getDifficult(Scanner scanner) {
        return switch (scanner.nextInt()) {
            case 1 -> GameDifficult.EASY;
            case 2 -> GameDifficult.NORMAL;
            case 3 -> GameDifficult.HARD;
            default -> {
                System.out.println("Такой категории не существует. Выбрана случайная сложность");
                GameDifficult[] values = GameDifficult.values();
                yield values[new Random().nextInt(values.length)];
            }
        };
    }

    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public String playTest(String word, String userInput) {
        HangmanGame testGame = new HangmanGame(new AppConfig(12, new String[]{word}));
        testGame.initForTest(word, 12);
        for (char c : userInput.toCharArray()) {
            testGame.guess(c);
        }
        return testGame.getState() + (testGame.isWordGuessed() ? ";POS" : ";NEG");
    }
}
