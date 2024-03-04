package ppoo.project.library.enums;

import java.util.List;

public enum Collection {
    FANTEZIE("Fantezie"),
    DISTOPIE("Distopie"),
    REALISM_MAGIC("Realism Magic"),
    STIINTIFICO_FANTASTIC("Stiintifico-Fantastic"),
    MISTER("Mister"),
    ROMANTIC("Romantic"),
    CLASICE("Clasice"),
    NON_FICTIUNE("Non-Fictiune"),
    INFORMATICA("Informatica"),
    MARKETING("Marketing"),
    MANAGEMENT("Management");

    private final String displayName;

    Collection(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }



    public static Collection getCollectionByName(String displayName) {
        for (Collection collection : Collection.values()) {
            if (collection.getDisplayName().equalsIgnoreCase(displayName) ||
                    collection.name().equalsIgnoreCase(displayName)) {
                return collection;
            }
        }
        return null;
    }

}

