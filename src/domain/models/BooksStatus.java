package domain.models;

/**
 * AVAILABLE(disponible)
 * BORROWER(emprunte)
 * RESERVED(reserve)
 * MAINTENANCE(en maintenance)
 * LOST(perdu)
 * */

public enum BooksStatus {
    AVAILABLE("disponible"),
    BORROWER("emprunte"),
    RESERVED("reserve"),
    MAINTENANCE("ne maintenance"),
    LOST("perdu");

    private final String displayName;
    BooksStatus(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {return displayName;}
}
