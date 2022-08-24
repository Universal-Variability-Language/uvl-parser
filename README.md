# build
mvn package

# run
java -cp target/uvl-parser-1.0-SNAPSHOT.jar

# create jar with dependencies (also newly creates the antlr classes for the grammar)
mvn clean compile assembly:single
