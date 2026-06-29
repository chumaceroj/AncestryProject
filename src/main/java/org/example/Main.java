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

        List<Person> results = analyzer.searchByName("Garrison");
        for (Person p : results) {
            System.out.println(p.firstName + " " + p.lastName + " - Born: " + p.birthDate);
        }
        System.out.println("People with name 'House': " + results.size());
    }
}
