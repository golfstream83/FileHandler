Тестовое задание

Требования:
Задание должно быть выполнено на языке Java.
Для сборки использовать любую из распространенных систем сборки (maven, ant, gradle и т.д.).
Исходный код должен быть выложен на github.com или bitbucket.com и снабжён инструкциями по сборке и запуску.

Задание:
Разработать программу на языке Java для обработки файла, в котором задаются зависимости сущностей друг от друга.
Каждая сущность представлена своим уникальным идентификатором в виде целого числа.
Зависимости задаются строкой в текстовом файле, содержащем произвольное количество строк формата:
<id сущности> <id сущности от которой она зависит>. Файл читать из STDIN.
Для прочитанных сведений о связях между сущностями необходимо найти циклические зависимости и вывести их в STDOUT
в виде id сущностей через пробел.

Пример файла:
1 2
2 1
3 4
5 6
6 5

Ожидаемый вывод в STDOUT:
1 2 1
5 6 5

/--------Инструкция по сборке и запуску--------/
1. Для сборки использовать Maven.
2. В папке с файлом pom.xml выполнить команду в консоли: mvn package
3. Запустить из папки target jar-файл командой в консоли: java -jar filehandler-1.0.jar
4. Для запуска тестов выполнить команду в консоли: mvn test