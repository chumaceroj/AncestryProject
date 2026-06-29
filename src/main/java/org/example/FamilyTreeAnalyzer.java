package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Collections;


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
     * Searches for families whose marriage date contains the query.
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
     * Searches for families whose divorce date contains the query.
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

    /** Returns how many generations of ancestors exist for a given person. */
    public int findGenerationDepth(String id) {
        HashMap<Integer, List<Person>> ancestors = findAncestors(id);
        if (ancestors.isEmpty()) return 0;
        return ancestors.size();
    }

    /**
     * Finds the shortest relationship path between two people using BFS.
     * Traverses parent, child, and spouse connections in all directions.
     * @param id1 starting person's GEDCOM ID
     * @param id2 target person's GEDCOM ID
     * @return list of Person objects forming the path, or null if no connection exists
     */
    public List<Person> findRelationshipPath(String id1, String id2) {
        HashMap<String, String> path = new HashMap<>();
        Queue<Person> queue = new LinkedList<>();
        Person start = people.get(id1);
        queue.add(start);
        path.put(id1, null);

        // checks if start and end of the path are the same
        if (id1.equals(id2)) {
            List<Person> result = new ArrayList<>();
            result.add(people.get(id1));
            return result;
        }
        while(!queue.isEmpty()) {
            Person p = queue.poll();

            // explore parents
            if (p != null && p.famcId != null) {
                Family f = families.get(p.famcId);
                Person parent1 = people.get(f.spouse1Id);
                Person parent2 = people.get(f.spouse2Id);
                if (parent1 != null && !path.containsKey(parent1.id)) {
                    path.put(parent1.id, p.id);
                    queue.add(parent1);
                    if (parent1.id.equals(id2)) {
                        return buildPath(path, id2);
                    }
                }
                if (parent2 != null && !path.containsKey(parent2.id)) {
                    path.put(parent2.id, p.id);
                    queue.add(parent2);
                    if (parent2.id.equals(id2)) {
                        return buildPath(path, id2);
                    }
                }
            }

            // explore children and spouse(s)
            if (p != null && p.famsIds != null) {
                // loop through each family the person is a parent in
                for (String family : p.famsIds) {
                    Family f = families.get(family);
                    for (String children : f.childIds) {
                        Person child = people.get(children);
                        if (child != null && !path.containsKey(child.id)) {
                            path.put(child.id, p.id);
                            queue.add(child);
                            if (child.id.equals(id2)) {
                                return buildPath(path, id2);
                            }
                        }
                    }

                    Person spouse;
                    if (p.id.equals(f.spouse1Id)) {
                        spouse = people.get(f.spouse2Id);
                    } else {
                        spouse = people.get(f.spouse1Id);
                    }

                    if (spouse != null && !path.containsKey(spouse.id)) {
                        path.put(spouse.id, p.id);
                        queue.add(spouse);
                        if (spouse.id.equals(id2)) {
                            return buildPath(path, id2);
                        }
                    }
                }
            }

        }
        return null;
    }

    /** Traces back through the path to build it from start to target. */
    private List<Person> buildPath(HashMap<String, String> path, String id2) {
        List<Person> result = new ArrayList<>();
        String current = id2;
        while (current != null) {
            result.add(people.get(current));
            current = path.get(current);
        }
        Collections.reverse(result);
        return result;
    }


}
