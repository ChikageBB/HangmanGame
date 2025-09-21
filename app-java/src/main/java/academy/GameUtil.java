package academy;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class GameUtil {
    public static Optional<String> getWord(ArrayList<String> words) {
        if (words == null || words.size() == 0) {
            return Optional.empty();
        }

        Random r = new Random();
        return Optional.of(words.get(r.nextInt(words.size())));
    }
}
