## Polish
- Comprehensive JUnit tests (edge cases, null fields, empty strings)
- Comment pass on all classes
- Update README with project description, how to run, sample output

## Features to Add
- findAncestors() - DFS up the tree
- findDescendants() - DFS down the tree
- findRelationshipPath() - BFS between two people
- isLikelyLiving() - privacy/PII detection
- findPeopleWithNoParents() - top of tree
- Migration pattern analysis
- Surname frequency analysis
- Date converter for range queries

## Future
- Spring Boot web app
- Interactive visualization
- Privacy/security auditor
  Graph/Tree Traversal (shows DSA skills)


Find all ancestors of a person (DFS up the tree)
Find all descendants of a person (DFS down the tree)
Find the relationship path between two people (BFS)
Calculate how many generations deep the tree goes
Find the most common ancestor between two people

Data Analysis (shows analytical thinking)

Migration patterns — track where your family moved over generations
Surname frequency — which last names appear most
Average number of children per family over time
Birth location clustering — where did most of your family come from
Marriage age trends across generations
Divorce rates across generations

Search and Query (practical features)

Search for a person by name
Find everyone born in a specific location
Find everyone born in a specific date range
Find all people with no death date (possibly still living)
Find all people missing data (no birth date, no parents, etc.)

Cybersecurity Angle

PII exposure auditor — flag living people's sensitive data
Data completeness report — what info is exposed vs missing
OSINT risk scoring for each person

Visualization (makes it demo-able)

Output a family tree to the console in a readable format
Generate a DOT file for Graphviz visualization
Export data to JSON for later use with a web frontend