package academy;

public enum GameDifficult {
    EASY(10),
    NORMAL(7),
    HARD(5);

    private int maxAttempts;

    GameDifficult(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }
}
