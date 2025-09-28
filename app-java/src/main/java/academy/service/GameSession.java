package academy.service;

import academy.config.AppConfig;
import academy.model.*;

import java.util.Random;
import java.util.Scanner;
import static academy.model.WordCategory.ANIMALS;
import static academy.model.WordCategory.ARTS;
import static academy.model.WordCategory.FOODS;
import static academy.model.WordCategory.ITEMS;
import static academy.model.WordCategory.PEOPLE;
import static academy.model.WordCategory.PLACES;
import static academy.model.WordCategory.TRANSPORT;
import static academy.model.GameDifficult.EASY;

public class GameSession {


    private final HangmanGame game;

    public GameSession(HangmanGame game) {
        this.game = game;
    }

    public void startInteractive() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Добро пожаловать в игру Виселица!");
        System.out.println("Выберете уровень сложности: EASY / NORMAL / HARD");

        GameDifficult difficult = switch (scanner.nextLine().toUpperCase()) {
            case "EASY" -> EASY;
            case "NORMAL" -> GameDifficult.NORMAL;
            case "HARD" -> GameDifficult.HARD;
            default -> {
                System.out.println("Такой категории не существует. Выбрана случайная сложность");
                GameDifficult[] values = GameDifficult.values();
                yield values[new Random().nextInt(values.length)];
            }
        };
        System.out.println("Выбрана сложность: " + difficult.name());
        clearConsole();

        System.out.println("Выберете категорию слова: ANIMALS / TRANSPORT / PEOPLE / PLACES / ARTS / ITEMS / FOODS ");
        WordCategory category =  switch (scanner.nextLine().toUpperCase()) {
            case "ANIMALS" -> ANIMALS;
            case "TRANSPORT" -> TRANSPORT;
            case "PEOPLE" -> PEOPLE;
            case "PLACES" -> PLACES;
            case "ARTS" -> ARTS;
            case "ITEMS" -> ITEMS;
            case "FOODS" -> FOODS;
            default -> {
                System.out.println("Такой категории не существует. Выбрана случайная категория");
                WordCategory[] values = WordCategory.values();
                yield values[new Random().nextInt(values.length)];
            }
        };
        System.out.println("Выбрана категория: " +  category.name());
        clearConsole();

        game.init(difficult, category);
        playInteractive();
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
                System.out.println("Введите хотя бы одну букву");
                continue;
            }

            if (input.equalsIgnoreCase("!hint")) {
                System.out.println(game.getHint());
                continue;
            }

            char letter = input.charAt(0);
            GuessResult res = game.guess(letter);

            switch (res) {
                case INCORRECT -> System.out.println("Неверно!");
                case CORRECT -> System.out.println("Верно!");
            }
            clearConsole();
        }
        if (game.isWordGuessed()) {
            System.out.println(HangmanArt.getStage(game.getDifficult(), game.getMistakes()));
            System.out.println("Поздравляем!!! Вы угадали слово: " + game.getGuessedWord());
        } else {
            System.out.println(HangmanArt.getStage(game.getDifficult(), game.getMistakes()));
            System.out.println("Вы проиграли!!! Загаданное слово: " + game.getGuessedWord());
        }
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
