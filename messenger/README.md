
# MVN how to

Как установить maven в систему, если его нет
https://docs.google.com/document/d/1vvKWJbUtO1duyRMwn6AxBTMRGMnyuYWxu9RtlJDiUk4/edit?usp=sharing



Запустить любой класс с main()

```java
mvn exec:java -Dexec.mainClass="ru.mail.track.Main"
```

В pom.xml прописана конфигурация для запуска тестового сервера и клиента. Различные способы запуска описываются в виде профилей.
Созданы 2 профиля name=Client, name=Server, для каждого профиля задан свой mainClass (Смотрите тег arguments)

Для запуска определенного профиля используйте команду

> mvn exec:exec -D<имя профиля>

Запуск клиента

```java
mvn exec:exec -DClient
```

Запуск сервера

```java
mvn exec:exec -DServer
```

При работе с pom.xml или при изменении каких-то классов не забудьте выполнить 

```java
mvn clean
mvn compile
```

Также, если кликнуть правой кнопкой на тексте файла pom.xml, можно в контекстном меню выбрать пункт Mave->Reimport чтобы обновить конфигурацию
