package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
        GedcomParser parser = new GedcomParser();
        parser.parse("family.ged");

        System.out.println("People found: " + parser.people.size());
        System.out.println("Families found: " + parser.families.size());

        FamilyTreeAnalyzer analyzer = new FamilyTreeAnalyzer(parser.people, parser.families);

        List<Person> results = analyzer.searchByBirthDate("19 Jan");
        for (Person p : results) {
            System.out.println(p.firstName + " " + p.lastName + " - Born: " + p.birthDate);
        }
        System.out.println("People with the birthday January 19: " + results.size());

        // HashMap<Integer, List<Person>> ancestors = analyzer.findAncestors("@I292021289862@");
        // for (int gen : ancestors.keySet()) {
            // System.out.println("Generation " + gen + ":");
            // for (Person p : ancestors.get(gen)) {
                // System.out.println("  " + p.firstName + " " + p.lastName + " (b. " + p.birthDate + ")");
            // }
        // }
        // System.out.println("\n--- Descendants of _ ---");
        // HashMap<Integer, List<Person>> descendants = analyzer.findDescendants("@I292021299547@");
        // for (int gen : descendants.keySet()) {
            // System.out.println("Generation " + gen + ":");
            // for (Person p : descendants.get(gen)) {
                // System.out.println("  " + p.firstName + " " + p.lastName + " (b. " + p.birthDate + ")");
            // }
        // }

        // int depth = analyzer.findGenerationDepth("@I292021289862@");
        // System.out.println("Generations deep: " + depth);

        // List<Person> result = analyzer.findRelationshipPath("@I292021289862@", "@I292208842573@");
        // if (result == null) {
            // System.out.println("No connection found.");
        // } else {
            // for (Person p : result) {
                // System.out.print(p.firstName + " " + p.lastName + " --> ");
            // }
        //}
    }
}
