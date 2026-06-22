package org.example;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GedcomParserTest {
    GedcomParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new GedcomParser();
        parser.parse("test-data.ged");
    }

    @Test
    public void testPeopleCount() {
        assertEquals(3, parser.people.size());
    }

    @Test
    public void testFamilyCount() {
        assertEquals(1, parser.families.size());
    }

    @Test
    public void testPersonName() {
        Person p = parser.people.get("@I1@");
        assertEquals("John", p.firstName);
        assertEquals("Doe", p.lastName);
    }

    @Test
    public void testPersonBirth() {
        Person p = parser.people.get("@I1@");
        assertEquals("5 Mar 1950", p.birthDate);
        assertEquals("New York, USA", p.birthPlace);
    }

    @Test
    public void testPersonSex() {
        Person p = parser.people.get("@I1@");
        assertEquals("M", p.sex);
    }

    @Test
    public void testFamcLink() {
        Person child = parser.people.get("@I3@");
        assertEquals("@F1@", child.famcId);
    }

    @Test
    public void testFamsLink() {
        Person parent = parser.people.get("@I1@");
        assertTrue(parent.famsIds.contains("@F1@"));
    }

    @Test
    public void testFamilySpouses() {
        Family f = parser.families.get("@F1@");
        assertEquals("@I1@", f.spouse1Id);
        assertEquals("@I2@", f.spouse2Id);
    }

    @Test
    public void testFamilyChildren() {
        Family f = parser.families.get("@F1@");
        assertTrue(f.childIds.contains("@I3@"));
    }

    @Test
    public void testMarriage() {
        Family f = parser.families.get("@F1@");
        assertEquals("10 Sep 1975", f.marriageDate);
        assertEquals("New York, USA", f.marriagePlace);
    }
}
