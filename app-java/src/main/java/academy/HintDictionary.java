package academy;

import java.util.Map;


// СЛОВАРЬ ПОДСКАЗОК
public class HintDictionary {
    private static final Map<String, String> HINT = Map.ofEntries(
        Map.entry("кот", "Домашнее животное, любит молоко и мурлыкать"),
        Map.entry("собака", "Лучший друг человека, охраняет дом"),
        Map.entry("тигр", "Большая дикая кошка с полосками"),
        Map.entry("лев", "Царь зверей"),
        Map.entry("олень", "Животное с рогами, живёт в лесу"),
        Map.entry("заяц", "Лесной житель с длинными ушами"),
        Map.entry("медведь", "Большой лесной зверь, любит мёд"),
        Map.entry("лиса", "Хитрое рыжее животное"),
        Map.entry("волк", "Хищник, живёт в стае"),
        Map.entry("енот", "Зверёк с маской на морде, любит мыть еду")
    );

    public static String getHint(String word) {
        return HINT.getOrDefault(word, "Подсказки нет");
    }
}
