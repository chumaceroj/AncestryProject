package org.example;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an individual person parsed from a GEDCOM file.
 * Stores personal details and references to family records.
 */
public class Person {
    String id;
    String firstName; // includes both first and middle name
    String lastName;
    String sex; // sex of the family member. The GEDCOM file does not allow for clarification of gender identity.
    String birthDate;
    String birthPlace;
    String deathDate;
    String deathPlace;
    String famcId; // family where this person is a child
    List<String> famsIds; //  families where this person is a spouse/parent

    public Person(String id) {
        this.id = id;
        this.famsIds = new ArrayList<>();
    }

}
