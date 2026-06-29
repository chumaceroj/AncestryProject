package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


/**
 * Provides search and analysis methods for parsed GEDCOM data.
 */
public class FamilyTreeAnalyzer {
    HashMap<String, Person> people;
    HashMap<String, Family> families;

    public FamilyTreeAnalyzer(HashMap<String, Person> people, HashMap<String, Family> families) {
        this.people = people;
        this.families = families;
    }

    /** Searches for people whose full name contains the query. */
    public List<Person> searchByName(String query) {
        List<Person> results = new ArrayList<>();
        for (Person p : people.values()) {
            String fullName = (p.firstName != null ? p.firstName : "") + " " +
                    (p.lastName != null ? p.lastName : "");
            if (fullName.contains(query)) {
                results.add(p);
            }
        }
        return results;
    }

    /** Searches by first name only. */
    public List<Person> searchByFirstName(String query) {
        List<Person> results = new ArrayList<>();
        for (Person p : people.values()) {
            if (p.firstName != null && p.firstName.contains(query)) {
                results.add(p);
            }
        }
        return results;
    }

    /** Searches by last name only. */
    public List<Person> searchByLastName(String query) {
        List<Person> results = new ArrayList<>();
        for (Person p : people.values()) {
            if (p.lastName != null && p.lastName.contains(query)) {
                results.add(p);
            }
        }
        return results;
    }

    /** Searches for people whose birthplace contains the query. */
    public List<Person> searchByBirthPlace(String query) {
        List<Person> results = new ArrayList<>();
        for (Person p : people.values()) {
            if (p.birthPlace != null && p.birthPlace.contains(query)) {
                results.add(p);
            }
        }
        return results;
    }

    /** Searches for people whose birthdate contains the query. */
    public List<Person> searchByBirthDate(String query) {
        List<Person> results = new ArrayList<>();
        for (Person p : people.values()) {
            if (p.birthDate != null && p.birthDate.contains(query)) {
                results.add(p);
            }
        }
        return results;
    }

    /** Searches for people whose death place contains the query. */
    public List<Person> searchByDeathPlace(String query) {
        List<Person> results = new ArrayList<>();
        for (Person p : people.values()) {
            if (p.deathPlace != null && p.deathPlace.contains(query)) {
                results.add(p);
            }
        }
        return results;
    }

    /** Searches for people whose deathdate contains the query. */
    public List<Person> searchByDeathDate(String query) {
        List<Person> results = new ArrayList<>();
        for (Person p : people.values()) {
            if (p.deathDate != null && p.deathDate.contains(query)) {
                results.add(p);
            }
        }
        return results;
    }

    /** Searches for people whose marriage date contains the query. */
    public List<Family> searchByMarriageDate(String query) {
        List<Family> results = new ArrayList<>();
        for (Family f : families.values()) {
            if (f.marriageDate != null && f.marriageDate.contains(query)) {
                results.add(f);
            }
        }
        return results;
    }

    /** Searches for people whose divorce date contains the query. */
    public List<Family> searchByDivorceDate(String query) {
        List<Family> results = new ArrayList<>();
        for (Family f : families.values()) {
            if (f.divorceDate != null && f.divorceDate.contains(query)) {
                results.add(f);
            }
        }
        return results;
    }
}
