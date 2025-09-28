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
                ___||___
        """,

        """
                __________________
                  \\||/           |
                   ||           (_)
                   ||
                   ||
                   ||
                   ||
                ___||___
        """,

        """
                __________________
                  \\||/           |
                   ||           (_)
                   ||            |
                   ||
                   ||
                   ||
                ___||___
        """,

        """
                __________________
                  \\||/           |
                   ||           (_)
                   ||           \\|
                   ||
                   ||
                   ||
                ___||___
        """,

        """
                __________________
                  \\||/           |
                   ||           (_)
                   ||           \\|/
                   ||
                   ||
                   ||
                ___||___
        """,

        """
                __________________
                  \\||/           |
                   ||           (_)
                   ||           \\|/
                   ||            |
                   ||           /
                   ||
                ___||___
        """,

        """
                __________________
                  \\||/           |
                   ||           (_)
                   ||           \\|/
                   ||            |
                   ||           / \\
                   ||
                ___||___
        """,
    };


    public static String getStage(GameDifficult difficult, int mistakes) {
        int maxAttempts = difficult.getMaxAttempts();
        int index = (int) Math.round(((double) mistakes / maxAttempts) * (FULL_STAGES.length - 1));
        return FULL_STAGES[Math.min(index, FULL_STAGES.length - 1)];
    }
}
