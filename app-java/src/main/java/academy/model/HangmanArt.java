package academy.model;

public class HangmanArt {
    private static final String[] FULL_STAGES = {

        """
                __________________
                  \\||/
                   ||
                   ||
                   ||
                   ||
                   ||
                ===||====
        """,

        """
                __________________
                  \\||/           |
                   ||           (_)
                   ||
                   ||
                   ||
                   ||
                ===||====
        """,

        """
                __________________
                  \\||/           |
                   ||           (_)
                   ||            |
                   ||
                   ||
                   ||
                ===||====
        """,

        """
                __________________
                  \\||/           |
                   ||           (_)
                   ||            |
                   ||            |
                   ||
                   ||
                ===||====
        """,

        """
                __________________
                  \\||/           |
                   ||           (_)
                   ||           /|
                   ||            |
                   ||
                   ||
                ===||====
        """,

        """
                __________________
                  \\||/           |
                   ||           (_)
                   ||           /|\\
                   ||            |
                   ||
                   ||
                ===||====
        """,

        """
                __________________
                  \\||/           |
                   ||           (_)
                   ||           /|\\
                   ||            |
                   ||           /
                   ||
                ===||====
        """,

        """
                __________________
                  \\||/           |
                   ||           (_)
                   ||           /|\\
                   ||            |
                   ||           / \\
                   ||
                ===||====
        """,
    };


    private static final String[] EASY_STAGES = {
        FULL_STAGES[0], FULL_STAGES[1], FULL_STAGES[2],
        FULL_STAGES[3], FULL_STAGES[4], FULL_STAGES[5],
        FULL_STAGES[6], FULL_STAGES[7]
    };

    private static final String[] NORMAL_STAGES = {
        FULL_STAGES[0], FULL_STAGES[1], FULL_STAGES[3], FULL_STAGES[4],
        FULL_STAGES[6], FULL_STAGES[7]
    };

    private static final String[] HARD_STAGES = {
        FULL_STAGES[0], FULL_STAGES[2], FULL_STAGES[5], FULL_STAGES[7]
    };
    // 0 1 3 4 6 7
    // 0 2 5 7
    public static String getStage(GameDifficult difficult, int mistakes) {
        int index = Math.min(mistakes, getMaxStages(difficult) - 1);
        return switch (difficult) {
            case EASY -> HangmanArt.EASY_STAGES[index];
            case NORMAL -> HangmanArt.NORMAL_STAGES[index];
            case HARD -> HangmanArt.HARD_STAGES[index];
        };
    }

    public static int getMaxStages(GameDifficult difficult) {
        return switch (difficult) {
            case EASY -> HangmanArt.EASY_STAGES.length;
            case NORMAL -> HangmanArt.NORMAL_STAGES.length;
            case HARD -> HangmanArt.HARD_STAGES.length;
        };
    }
}
