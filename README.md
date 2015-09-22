## Работа в командной строке
Командная строка (или консоль или терминал) - интерфейс для работы с операционной системой с помощью текстовых команд 

**Windows**
1. Win+R откроет вам окно "Выполнить" там печатаете cmd затем жмете Enter
2. Пуск - Стандартные - Командная строка
3. Команды [читать здесь](http://windows.microsoft.com/ru-ru/windows/command-prompt-faq#1TC=windows-7) и [здесь](http://yroki-kompa.ru/vse_komandy_komandnoj_stroki_windows.html)


**nix**
Ну, вы сами знаете :) Если нет, то [читать здесь](http://pingvinus.ru/note/command-line-fundamentals)


Нам точно понадобятся команды (попробуйте ввести help в консоли и увидите полный список команд)

 * cd <куда> - перейти в папку по пути
 * dir - список файлов в папке (win)
 * ls - список файлов в папке (nix)


У вас должны быть настроены переменные среды
Попробуйте ввести в консоли (Так выглядит нормально настроенное окружение)

> $ java -version

> java version "1.8.0_60"

> Java(TM) SE Runtime Environment (build 1.8.0_60-b27)

> Java HotSpot(TM) 64-Bit Server VM (build 25.60-b23, mixed mode)

Если видите такую ошибку то Вам нужно настроить переменные окружения:

> $ C:\Users\arhangeldim>java -version

> "java" не является внутренней или внешней командой, исполняемой программой или пакетным файлом.


## Компиляция приложения
Переходим в рабочую директорию

> $ cd C:\mail\tehnotrack
 
В этой директории у вас должна быть создана структура проекта

```java
tehnotrack/
    src/фамилия/имя/проект
    classes/
```

В *classes* компилятор разместит скомпилированные файлы. 
в директории *C:\mail\tehnotrack\src\ru\mail\track* создаем новый текстовый файл и меняем его формат на .java
У меня выглядит так *C:\mail\tehnotrack\src\ru\mail\track\HelloMipt.java*

Пишем текст программы:

```java
package ru.mail.track;

public class HelloMipt {
    public static void main(String[] args) {
        System.out.println("Hello mipt");
    }
}
```

Из папки tehnotrack запускаем (Из командной строки)

> $ javac -d .\classes src\ru\mail\track\HelloMipt.java

Опция **-d** указывает, куда складывать скомпилированные класс-файлы 

получаем скомпилированный класс (команда dir (Win), ls (nix))

> $ dir classes\ru\mail\track\ 

> $ HelloMipt.class

Чтобы запустить приложение, нужно вызвать виртуальную машину java и передать ей имя запускаемого класса как параметр:
* Класс должен содержать метод main()
* Имя класса указывается без расширения .class
* Имя класса должно совпадать с именем файла


> $ java -сp .\classes ru.mail.track.HelloMipt 

Опция **-cp** указывает classpath для виртуальной машины, где она должна искать класс HelloMipt.class


## jar архивы
Для чего нужен JAR

* Структурированность. Файлы классов проекта хранятся в виде одного архива.
* Защищенность. В JAR-файл можно поместить цифровую подпись, дающею конечному пользователю гарантии, что файл архива не изменился с момента её внесения.
* Независимость от платформы.

1. В папке проекта tehnotrack создать файл manifest.mf - файл описания архива

```js
Manifest-Version: 1.0
Created-By: me
Main-Class: ru.main.track.HelloMipt

```

Обязательно сделайте перевод строки после строчки с Main-Class: имя класса! (то есть нажмите Enter - в итоге в файле должно быть 4 строчки, последняя пустая)


2. Затем запускаем команду сборки архива

```
jar cmf <файл манифеста> <имя архива> <опции> <директория, где лежат скомпилированные классы>
```

> $ jar cvmf manifest.mf hello.jar -C classes .

> added manifest

> adding: ru/mail/track(in = 0) (out= 0)(stored 0%)

> adding: ru/mail/track/HelloMipt.class(in = 439) (out= 300)(deflated 31%)

3. Запускаем наше приложение

> $ java -jar hello.jar

> $ Hello MIPT


Ключи команды jar

* с -- create (Создать)
* v -- Вывести дополнительную информацию
* m -- Манифест 
* f -- Имя архива (порядок mf важен - сначала передаем манифест, а потом архив)
* -С Перед запуском перейти в директорию ./classes
* . - путь к файлам (символ точка означает текущую директорию)


## Подключаем зависимости

log4j.jar - библиотека для логирования ([для чего нужно логирование?](http://www.skipy.ru/useful/logging.html))

теперь нужно в classpath также добавить нашу библиотеку

> $ javac -d ./target/classes -cp .:log4j-1.2.17.jar src/main/java/ru/mail/track/MiptHello.java


для запуска приложения в classpath нужно указать путь к файлам .class, путь к библиотеке логирования и путь к папке ресурсов, где лежит конфигурация логгера

> $ java -cp ./src/main/resources/:./target/classes/:log4j-1.2.17.jar ru.mail.track.MiptHello

## Maven Система сборки проекта


pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>ru.mail.track</groupId>
    <artifactId>sample</artifactId>
    <version>1.0-SNAPSHOT</version>
    
    <dependencies>
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
    </dependencies>
</project>
```

Maven по умолчанию требует определенной [структуры директорий](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html)
 
 pom.xml - конфигурационный файл
 src/main/java - корень для хранению исходного кода
 src/main/resources - хранение ресурсов (конфигураций и тд )
 target - maven сложит сюда скомпилированныей файлы
 
 Скомпилировать и запустить:
  
> $ mvn compile exec:java -Dexec.mainClass=ru.mail.track.MiptHello
    
    
