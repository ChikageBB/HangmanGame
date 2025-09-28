package academy.service;

import academy.model.WordCategory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordCategorizer {
    private final Map<String, List<String>> categorizedWords = new HashMap();


    public WordCategorizer(String[] rawWords) {
        if (rawWords == null || rawWords.length == 0) {
            return;
        }

        for (String raw : rawWords) {
            if (raw == null) continue;


            String category = "default";
            String word = raw;

            if (raw.contains(":")) {
                String[] parts = raw.split(":");
                category = parts[0];
                word = parts[1];
            }

            categorizedWords.computeIfAbsent(category, w -> new ArrayList<>()).add(word);
        }
    }

    public Map<String, List<String>> asMap() {
        return categorizedWords;
    }

    public List<String> getByCategory(WordCategory category) {
        List<String> res = categorizedWords.get(category.getDescription());
        return res != null ? res : new ArrayList<>();
    }

    public Set<String> categories() {
        return categorizedWords.keySet();
    }
}
