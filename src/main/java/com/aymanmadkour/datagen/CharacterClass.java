package com.aymanmadkour.datagen;

public enum CharacterClass {
    LOWER_CASE('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'),
    UPPER_CASE('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'),
    DIGITS('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'),
    SPECIAL_CHARACTERS('\'', '"', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '`', '~', '[', ']', '{', '}', '<', '>', ',', '.', ';', ':', '?', '/', '\\', '-', '_', '=', '+', '|'),
    SPACE(' '),
    ;
    private final char[] characters;

    CharacterClass(char ... characters) {
        this.characters = characters;
    }

    public final int getCount() {
        return this.characters.length;
    }

    public final char getCharacter(int index) {
        return this.characters[index];
    }
}
