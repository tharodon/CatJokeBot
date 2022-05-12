package ru.liga.bot.buttons;

public enum Button {
    CAT("Тык", 0L),
    JOKE("Анекдот", 1L),
    JOKE_FOR_ADULT("Анекдот 18+", 11L),
    TOAST("Тост", 6L);

    private final String name;
    private final Long type;

    Button(String name, Long type) {
        this.name = name;
        this.type = type;
    }

    public Long getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
