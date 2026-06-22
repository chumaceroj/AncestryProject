package org.example;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a family unit parsed from a GEDCOM file.
 * Links two spouses and their children using individual IDs.
 */
public class Family {
    String id;
    String spouse1Id; // GEDCOM uses HUSB/WIFE but we store gender-neutrally
    String spouse2Id;
    String marriageDate;
    String marriagePlace;
    String divorceDate;
    String divorcePlace;
    List<String> childIds;

    public Family(String id) {
        this.id = id;
        this.childIds = new ArrayList<>();
    }
}
