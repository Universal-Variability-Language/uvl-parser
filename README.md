# build
mvn package

# run
java -cp target/uvl-parser-1.0-SNAPSHOT.jar

# create jar with dependencies (TODO does this build antlr classes too with new grammar?)
mvn clean compile assembly:single
