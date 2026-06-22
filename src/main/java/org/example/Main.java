package org.example;

public class Main {
    public static void main(String[] args) throws Exception {
        GedcomParser parser = new GedcomParser();
        parser.parse("test-data.ged");

        System.out.println("People found: " + parser.people.size());
        System.out.println("Families found: " + parser.families.size());
    }
}
