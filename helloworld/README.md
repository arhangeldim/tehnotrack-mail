## Compile (dir ./classes has to exist)

> javac -d ./classes src/ru/mail/track/MiptHello.java

## Run

> java -cp ./classes ru.mail.track.MiptHello

## Create jar

> jar cmfv manifest.mf hello.jar -C classes .

## Run jar

> java -jar hello.jar

## Compile with lib dependencies (log4j)

> javac -cp log4j-1.2.17.jar -d classes src/ru/mail/track/MiptHello.java

## Run with lib dependencies

> java -cp .:log4j-1.2.17.jar:classes ru.mail.track.MiptHello 
