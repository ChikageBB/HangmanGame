package academy.model;

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
}
