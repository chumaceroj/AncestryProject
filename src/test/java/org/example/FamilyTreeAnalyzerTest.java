package org.example;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class FamilyTreeAnalyzerTest {
    FamilyTreeAnalyzer analyzer;

    @Before
    public void setUp() throws Exception {
        GedcomParser parser = new GedcomParser();
        parser.parse("test-data.ged");
        analyzer = new FamilyTreeAnalyzer(parser.people, parser.families);
    }

    @Test
    public void testSearchByFullName() {
        List<Person> results = analyzer.searchByName("John Doe");
        assertEquals(1, results.size());
    }

    @Test
    public void testSearchByLastName() {
        List<Person> results = analyzer.searchByLastName("Doe");
        assertEquals(2, results.size());
    }

    @Test
    public void testSearchByFirstName() {
        List<Person> results = analyzer.searchByFirstName("Jane");
        assertEquals(1, results.size());
    }

    @Test
    public void testSearchByNameNoResults() {
        List<Person> results = analyzer.searchByName("xyz");
        assertEquals(0, results.size());
    }

    @Test
    public void testSearchByBirthPlace() {
        List<Person> results = analyzer.searchByBirthPlace("Chicago");
        assertEquals(1, results.size());
    }

    @Test
    public void testSearchByBirthDate() {
        List<Person> results = analyzer.searchByBirthDate("1950");
        assertEquals(1, results.size());
    }

    @Test
    public void testSearchByMarriageDate() {
        List<Family> results = analyzer.searchByMarriageDate("1975");
        assertEquals(1, results.size());
    }
}
