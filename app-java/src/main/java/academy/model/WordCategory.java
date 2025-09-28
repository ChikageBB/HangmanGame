package academy.model;

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

}
