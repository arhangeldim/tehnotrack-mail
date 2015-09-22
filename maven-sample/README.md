## Compile and run

> mvn compile exec:java -Dexec.mainClass=ru.mail.track.MiptHello

## Build jar 

> mvn package

## Build jar with dependencies

> mvn clean compile assembly:single

then run it

> java -jar target/sample-1.0-SNAPSHOT-jar-with-dependencies.jar
