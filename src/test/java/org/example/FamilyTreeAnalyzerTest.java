package org.example;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.HashMap;

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

    @Test
    public void testFindAncestors() {
        HashMap<Integer, List<Person>> ancestors = analyzer.findAncestors("@I3@");
        assertEquals(1, ancestors.size()); // only 1 generation since John and Jane have no parents
        assertEquals(2, ancestors.get(1).size()); // 2 parents
    }

    @Test
    public void testFindDescendants() {
        HashMap<Integer, List<Person>> descendants = analyzer.findDescendants("@I1@");
        assertEquals(1, descendants.size()); // 1 generation since Jimmy has no kids
        assertEquals(1, descendants.get(1).size()); // 1 child - Jimmy
    }

    @Test
    public void testFindDescendantsNoChildren() {
        HashMap<Integer, List<Person>> descendants = analyzer.findDescendants("@I3@");
        assertTrue(descendants.isEmpty()); // Jimmy has no kids
    }

    @Test
    public void testFindGenerationDepth() {
        int depth = analyzer.findGenerationDepth("@I3@");
        assertEquals(1, depth); // Jimmy has 1 generation of ancestors (parents)
    }

    @Test
    public void testFindRelationshipPath() {
        List<Person> path = analyzer.findRelationshipPath("@I1@", "@I3@");
        assertNotNull(path);
        assertEquals(2, path.size()); // John → Jimmy (parent to child)
        assertEquals("@I1@", path.get(0).id);
        assertEquals("@I3@", path.get(1).id);
    }

    @Test
    public void testFindRelationshipPathSpouse() {
        List<Person> path = analyzer.findRelationshipPath("@I1@", "@I2@");
        assertNotNull(path);
        assertEquals(2, path.size()); // John → Jane (spouses)
    }

    @Test
    public void testFindRelationshipPathSelf() {
        List<Person> path = analyzer.findRelationshipPath("@I1@", "@I1@");
        assertNotNull(path);
        assertEquals(1, path.size());
        assertEquals("@I1@", path.get(0).id);
    }

}
