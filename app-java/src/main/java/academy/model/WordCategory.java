package academy.model;

import java.util.Random;

public enum WordCategory {


    ANIMALS("животные"),
    PEOPLE("люди"),
    ITEMS("предметы"),
    TRANSPORT("транспорт"),
    PLACES("места"),
    ARTS("искусство"),
    FOODS("еда");

    private String desription;


    WordCategory(String description) {
        this.desription = description;
    }

    public String getDescription() {
        return desription;
    }

    public static WordCategory fromNumber(int number) {
        return switch (number) {
            case 1 -> WordCategory.ANIMALS;
            case 2 -> WordCategory.TRANSPORT;
            case 3 -> WordCategory.PEOPLE;
            case 4 -> WordCategory.PLACES;
            case 5 -> WordCategory.ARTS;
            case 6 -> WordCategory.ITEMS;
            case 7 -> WordCategory.FOODS;
            default -> {
                System.out.println("Такой категории не существует. Выбрана случайная категория");
                WordCategory[] values = WordCategory.values();
                yield values[new Random().nextInt(values.length)];
            }
        };
    }

}
