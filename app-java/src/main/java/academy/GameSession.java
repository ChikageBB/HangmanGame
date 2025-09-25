package academy;

import java.util.Scanner;

public class GameSession {

    private final HangmanGame game;

    public GameSession(HangmanGame game) {
        this.game = game;
    }

    public void playInteractive() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Добро пожаловать в игру Виселица!");
        System.out.println("Угадайте слово. Максимум ошибок: " + game.getMaxAttempts());
        System.out.println("Слово: " + game.getGuessedWord());
        System.out.println("Длина: " + game.getGuessedWord().length());

        while (!game.isGameOver()) {

            System.out.println(HangmanArt.getStage(game.getDifficult(), game.getMistakes()));
            System.out.println("Слово: " + game.getState());
            System.out.println("Ошибки: " + game.getMistakes() + "/" + game.getMaxAttempts());
            System.out.print("Введите букву: ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Введите хотя бы одну букву");
                continue;
            }

            char letter = Character.toLowerCase(input.charAt(0));
            GuessResult res = game.guess(letter);

            switch (res) {
                case INCORRECT -> System.out.println("Неверно!");
                case CORRECT -> System.out.println("Верно!");
            }
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        if (game.isWordGuessed()) {
            System.out.println(HangmanArt.getStage(game.getDifficult(), game.getMistakes()));
            System.out.println("Поздравляем!!! Вы угадали слово: " + game.getGuessedWord());
        } else {
            System.out.println(HangmanArt.getStage(game.getDifficult(), game.getMistakes()));
            System.out.println("Вы проиграли!!! Загаданное слово: " + game.getGuessedWord());
        }
    }

    public String playTest(String word, String userInput) {
        HangmanGame testGame = new HangmanGame(GameDifficult.EASY, new AppConfig(12, new String[]{word}));
        for (char c : userInput.toCharArray()) {
            testGame.guess(c);
        }
        return testGame.getState() + (testGame.isWordGuessed() ? ";POS" : ";NEG");
    }
}
