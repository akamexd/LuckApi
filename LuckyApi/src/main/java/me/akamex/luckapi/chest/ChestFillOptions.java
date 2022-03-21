package me.akamex.luckapi.chest;

public enum ChestFillOptions implements ChestFillOption {

    RANDOMLY_FILL,
    CLEAR_EXISTING,
    CREATE_IF_ABSENT;

    @Override
    public ChestFillOptions getOption() {
        return this;
    }

}
