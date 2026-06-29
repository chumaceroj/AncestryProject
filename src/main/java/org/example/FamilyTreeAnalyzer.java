package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;


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

    /**
     * Searches for people whose full name contains the query.
     */
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

    /**
     * Searches by first name only.
     */
    public List<Person> searchByFirstName(String query) {
        List<Person> results = new ArrayList<>();
        for (Person p : people.values()) {
            if (p.firstName != null && p.firstName.contains(query)) {
                results.add(p);
            }
        }
        return results;
    }

    /**
     * Searches by last name only.
     */
    public List<Person> searchByLastName(String query) {
        List<Person> results = new ArrayList<>();
        for (Person p : people.values()) {
            if (p.lastName != null && p.lastName.contains(query)) {
                results.add(p);
            }
        }
        return results;
    }

    /**
     * Searches for people whose birthplace contains the query.
     */
    public List<Person> searchByBirthPlace(String query) {
        List<Person> results = new ArrayList<>();
        for (Person p : people.values()) {
            if (p.birthPlace != null && p.birthPlace.contains(query)) {
                results.add(p);
            }
        }
        return results;
    }

    /**
     * Searches for people whose birthdate contains the query.
     */
    public List<Person> searchByBirthDate(String query) {
        List<Person> results = new ArrayList<>();
        for (Person p : people.values()) {
            if (p.birthDate != null && p.birthDate.contains(query)) {
                results.add(p);
            }
        }
        return results;
    }

    /**
     * Searches for people whose death place contains the query.
     */
    public List<Person> searchByDeathPlace(String query) {
        List<Person> results = new ArrayList<>();
        for (Person p : people.values()) {
            if (p.deathPlace != null && p.deathPlace.contains(query)) {
                results.add(p);
            }
        }
        return results;
    }

    /**
     * Searches for people whose deathdate contains the query.
     */
    public List<Person> searchByDeathDate(String query) {
        List<Person> results = new ArrayList<>();
        for (Person p : people.values()) {
            if (p.deathDate != null && p.deathDate.contains(query)) {
                results.add(p);
            }
        }
        return results;
    }

    /**
     * Searches for people whose marriage date contains the query.
     */
    public List<Family> searchByMarriageDate(String query) {
        List<Family> results = new ArrayList<>();
        for (Family f : families.values()) {
            if (f.marriageDate != null && f.marriageDate.contains(query)) {
                results.add(f);
            }
        }
        return results;
    }

    /**
     * Searches for people whose divorce date contains the query.
     */
    public List<Family> searchByDivorceDate(String query) {
        List<Family> results = new ArrayList<>();
        for (Family f : families.values()) {
            if (f.divorceDate != null && f.divorceDate.contains(query)) {
                results.add(f);
            }
        }
        return results;
    }

    /**
     * Finds all ancestors of a person using BFS traversal, organized by generation.
     * Generation 1 = parents, 2 = grandparents, etc.
     *
     * @param id the person's unique GEDCOM ID
     * @return ancestors grouped by generation number
     */
    public HashMap<Integer, List<Person>> findAncestors(String id) {
        HashMap<Integer, List<Person>> ancestors = new HashMap<>();
        Queue<Person> queue = new LinkedList<>();
        Person start = people.get(id);
        queue.add(start);
        int generation = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Person> currentGeneration = new ArrayList<>();

            // process queue level by level, and each round of the for loop is one generation
            for (int i = 0; i < size; i++) {
                Person p = queue.poll();
                if (p != null && p.famcId != null) {
                    Family f = families.get(p.famcId);
                    Person parent1 = people.get(f.spouse1Id);
                    Person parent2 = people.get(f.spouse2Id);
                    if (parent1 != null) {
                        currentGeneration.add(parent1);
                        queue.add(parent1);
                    }
                    if (parent2 != null) {
                        currentGeneration.add(parent2);
                        queue.add(parent2);
                    }
                }
            }

            if (!currentGeneration.isEmpty()) {
                generation++;
                ancestors.put(generation, currentGeneration);
            }

        }

        return ancestors;
    }

    /**
     * Finds all descendants of a person using BFS traversal, organized by generation.
     * Generation 1 = children, 2 = grandchildren, etc.
     * @param id the person's unique GEDCOM ID
     * @return descendants grouped by generation number
     */
    public HashMap<Integer, List<Person>> findDescendants(String id) {
        HashMap<Integer, List<Person>> descendants = new HashMap<>();
        Queue<Person> queue = new LinkedList<>();
        Person start = people.get(id);
        queue.add(start);
        int generation = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Person> currentGeneration = new ArrayList<>();

            // process queue level by level, and each round of the for loop is one generation
            for (int i = 0; i < size; i++) {
                Person p = queue.poll();
                if (p != null && p.famsIds != null) {
                    // loop through each family the person is a parent in
                    for (String family : p.famsIds) {
                        Family f = families.get(family);
                        for (String children : f.childIds) {
                            Person child = people.get(children);
                            if (child != null) {
                                currentGeneration.add(child);
                                queue.add(child);
                            }
                        }
                    }

                }
            }

            if (!currentGeneration.isEmpty()) {
                generation++;
                descendants.put(generation, currentGeneration);
            }

        }

        return descendants;
    }
}
