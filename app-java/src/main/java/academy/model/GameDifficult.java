package academy.model;

import java.util.Random;

public enum GameDifficult {
    EASY(7),
    NORMAL(5),
    HARD(3);

    private int maxAttempts;

    GameDifficult(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public static GameDifficult fromNumber(int number) {
        return switch (number) {
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

}
