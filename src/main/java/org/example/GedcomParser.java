package org.example;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Parses GEDCOM (.ged) genealogy files and stores the data
 * as Person and Family objects in HashMaps for O(1) lookup by ID.
 */
public class GedcomParser {
    HashMap<String, Person> people;
    HashMap<String, Family> families;

    public GedcomParser() {
        this.people = new HashMap<>();
        this.families = new HashMap<>();
    }

    /**
     * Reads a GEDCOM file line by line and adds to the people and families HashMaps.
     * Uses level 0 lines to identify record types (INDI/FAM) and tracks the current
     * record being built so subsequent lines can fill in its fields.
     *
     * @param filename path to the .ged file
     */
    public void parse(String filename) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine();
        Person currentPerson = null;
        Family currentFamily = null;
        String currentEvent = null; // tracks BIRT/DEAT/MARR/DIV so DATE and PLAC go to the right field

        while (line != null) {

            // level 0 lines mark the start of a new record and create the object and reset state
            if (line.startsWith("0") && line.contains("INDI")) {
                String[] parts = line.split(" ");
                String id = parts[1];
                currentPerson = new Person(id);
                currentFamily = null;
                currentEvent= null;
                people.put(id, currentPerson);

            } else if (line.startsWith("0") && line.contains("FAM")) {
                String[] parts = line.split(" ");
                String famId = parts[1];
                currentFamily = new Family(famId);
                currentPerson = null;
                currentEvent = null;
                families.put(famId, currentFamily);

                // level 1-2 lines belong to the current person record
            } else if (currentPerson != null) {
                if (line.contains("GIVN")) {
                    currentPerson.firstName =  line.substring(7); // skips "2 GIVN "
                } else if (line.contains("SURN")) {
                    currentPerson.lastName = line.substring(7);
                } else if (line.contains("SEX")) {
                    currentPerson.sex = line.substring(6);
                } else if (line.contains("BIRT")) {
                    currentEvent = "BIRT";
                } else if (line.contains("DEAT")) {
                    currentEvent = "DEAT";
                } else if (line.contains("DATE") && currentEvent != null) {
                    if (currentEvent.equals("BIRT")) {
                        currentPerson.birthDate = line.substring(7);
                    } else if (currentEvent.equals("DEAT")){
                        currentPerson.deathDate = line.substring(7);
                    }
                } else if (line.contains("PLAC") && currentEvent != null) {
                    if (currentEvent.equals("BIRT")) {
                        currentPerson.birthPlace = line.substring(7);
                    } else if(currentEvent.equals("DEAT")) {
                        currentPerson.deathPlace = line.substring(7);
                    }
                } else if (line.contains("FAMC")) {
                    currentPerson.famcId = line.substring(7);
                } else if (line.contains("FAMS")) {
                    currentPerson.famsIds.add(line.substring(7));
                }

                // level 1-2 lines belong to the current family record
            } else if (currentFamily != null) {
                if (line.contains("HUSB")) {
                    currentFamily.spouse1Id = line.substring(7);
                } else if (line.contains("WIFE")) {
                    currentFamily.spouse2Id = line.substring(7);
                } else if (line.contains("CHIL")) {
                    currentFamily.childIds.add(line.substring(7));
                } else if (line.contains("MARR")) {
                    currentEvent = "MARR";
                } else if (line.contains("DIV")) {
                    currentEvent = "DIV";
                } else if (line.contains("DATE") && currentEvent != null) {
                    if (currentEvent.equals("MARR")) {
                        currentFamily.marriageDate = line.substring(7);
                    } else if (currentEvent.equals("DIV")) {
                        currentFamily.divorceDate = line.substring(7);
                    }
                } else if (line.contains("PLAC") && currentEvent != null) {
                    if (currentEvent.equals("MARR")) {
                        currentFamily.marriagePlace = line.substring(7);
                    } else if (currentEvent.equals("DIV")) {
                        currentFamily.divorcePlace = line.substring(7);
                    }
                }
            }

            line = reader.readLine();
        }
        reader.close();
    }
}
