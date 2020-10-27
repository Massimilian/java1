package ru.progwards.java1.lessons.telegram_bot;

import com.google.common.collect.TreeMultimap;
import org.telegram.telegrambots.ApiContextInitializer;
import ru.progwards.java1.testlesson.ProgwardsTelegramBot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class StudyBot extends ProgwardsTelegramBot {
    String sep = System.lineSeparator();

    @Override
    public String processMessage(String text) {
        checkTags(text);
        if (foundCount() == 1) {
            if (checkLastFound("Оглавление")) {
                return packing(Paths.get("src/ru/progwards/java1/lessons/telegram_bot/text/Оглавление.txt"));
            }
            if (checkLastFound("привет")) {
                return "Приветствую. Есть вопрос по Java? Попробую ответить! Только прошу - спрашивай по-русски, либо - если приходится писать на английском - пиши точное название соответствующей главы. А если хочешь посмотреть оглавление - то так и напиши! P.S. Предложения и пожелания горячо приветствуются, просьба направлять их на адрес: javahelptelegrambot@gmail.com";
            }
            if (checkLastFound("кто ты есть")) {
                return "Я бот!";
            }
            if (checkLastFound("конец")) {
                return "Спасибо за уделённое время. Удачи, и я тоже обещаю учиться.";
            }
            if (checkLastFound("дурак")) {
                return "Не надо ругаться. Я не кодер, я только учусь";
            }
            if (checkLastFound("молодец")) {
                return "Как приятно читать тёплые слова! Рад стараться!";
            }
            if (checkLastFound("тест")) {
                return "Здесь осуществляется тестировка. Вы как вообще сюда попали?";
            }
            if (checkLastFound("скилы")) {
                return "Я помогаю с вопросами, связанными с работой на Java. Самый простой способ что-то спросить - выбрать какую-либо тему из оглавления. Либо написать по-русски тему вопроса, я постараюсь подобрать информацию.";
            }
            String crutch = extract().toLowerCase();
             return packing(Paths.get("src/ru/progwards/java1/lessons/telegram_bot/text/" + crutch.substring(0, crutch.length() - 1) + ".txt"));
            //return "src/ru/progwards/java1/lessons/telegram_bot/text/" + crutch.substring(0, crutch.length() - 1) + ".txt";
        }
        if (foundCount() > 1 && foundCount() < 20) {
            return "Под этот вопрос подходят разные варианты:" + sep + modifiedExtract() + "Надо выбрать что-то одно, и я смогу рассказать более подробно."; // + " Отладка:" + getFound() + " ";
        }
        if (foundCount() >= 20) {
            return "Под этот вопрос подходит слишком много вариантов... Попробуйте выбрать что-то более конкретное." + sep +
                    "На всякий случай, привожу полный список того, что подошло" + sep + extract();
        }

        return "Я не понял, мои знания весьма ограничены. Прошу прощения. Попробуем спросить по-другому?";
    }

    private String packing(Path path) {
        ArrayList<String> list = new ArrayList<>();
        try {
            list = (ArrayList<String>) Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return preparation(list);
    }

    private String preparation(ArrayList<String> list) {
        StringBuilder result = new StringBuilder(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            result.append(sep);
            result.append(list.get(i));
        }
        return result.toString();
    }

    private String modifiedExtract() {
        StringBuilder result = new StringBuilder();
        TreeMultimap<Integer, String> map = getFound();
        for (Integer key : map.keySet()) {
            result.append(map.get(key));
            result.append(sep);
        }
        return result.toString();
    }


    public static void main(String[] args) {
        ApiContextInitializer.init();

        StudyBot bot = new StudyBot();
        bot.username = "JavaHelp1_bot";
        bot.token = "1314830050:AAHt1LgpN0kNNPCDcH3dQZZK9B23AMYJPJ4";

//      история
        bot.addTags("Java", "кофе, справка, истори, создани, когда, появлен, возникновен, джава, баз, мультиплатформенн");
        bot.addTags("Оглавление", "chap, глав");

//      вспомогательные данные
        bot.addTags("привет", "привет, здрасьте, здравств, добр, день, вечер, утро, hi, hello, /start");
        bot.addTags("кто ты есть", "кто ты, ты кто, что ты такое, что ты вообщзе такое, ты бот, ты точно бот, как тебя зовут, как тебя величать, как тебя звать");
        bot.addTags("конец", "конец, все, стоп, нет, до свидания, спасибо, прощай, чао");
        bot.addTags("дурак", "дурак, придурок, идиот, тупой, чёрт");
        bot.addTags("молодец", "умный, хороший, умни, умён, добрый, великолепый, замечательный, продвинутый, молодец, крут, мил");
        bot.addTags("тест", "тест");
        bot.addTags("скилы", "что ты можешь, как ты работаешь, что ты умеешь, чем ты можешь, как ты можешь, чем ты занимаешься, зачем ты нужен, можешь помочь, можешь мне помочь");

//      типы данных
        bot.addTags("Boolean", "правда, ложь, true, false, булев, переменн, boolean, данны");
        bot.addTags("Byte", "цел, числ, байт, переменн, цифр, данны");
        bot.addTags("Short", "цел, числ, шорт, переменн, цифр, данны");
        bot.addTags("IntVar", "цел, числ, интежер, инт, интеджер, переменн, цифр, данны");
        bot.addTags("Long", "целое, числ, лонг, переменн, цифр, данны, больш");
        bot.addTags("Float", "дробное, вещественное, числ, флот, флоат, переменн, цифр, точность, данны");
        bot.addTags("Double", "дроб, вещественное, числ, флот, дабл, даубл, мантисса, порядок, точность, переменн, цифр, данны");
        bot.addTags("Character", "символ, букв, знак, цифр, числ, код, переменн, данны");
        bot.addTags("String", "строк, текст, переменн, данны");
        bot.addTags("Wrapper", "класс, обёртк, враппер");

//      особые числа
        bot.addTags("Number", "числ, цифр, номер");
        bot.addTags("BigDecimal", "дроб, числ, огромн, больш, гигантск, крупн");
        bot.addTags("BigInteger", "числ, огромн, больш, гигантск, крупн");

//      базовые знания
        bot.addTags("Compilator", "трансля, компиля, интерпрета, .class");
        bot.addTags("JDK", "инструмент, разработ, .class, байт-код, компиля");
        bot.addTags("JRE", "инструмент, выполнени, средство, библиотек, верификация, компиля");
        bot.addTags("JVM", "виртуальн, машин, компиля, сбор");
        bot.addTags("JIT", "мгновенн, компиля");
        bot.addTags("ClassLoader", "класс, лоадер, загруз, поиск, связыван, линковк, верификация, подготовка, разрешение, инициализация, бутстрап, класспас");
        bot.addTags("Assignment", "присваивани, приведени, тип");
        bot.addTags("Priority", "приоритет, логическ");
        bot.addTags("Comparison", "равн");
        bot.addTags("Enum", "перечислен, енум");
        bot.addTags("Static", "статическ, статик");
        bot.addTags("Count", "систем, счислени, двоичн, десятичн, шестандцатеричн");
        bot.addTags("Bits", "битов, операц, бинарн");
        bot.addTags("Bit2", "bit2");
        bot.addTags("Memory", "памят");

//      классы
        bot.addTags("Nested Class", "статическ, вложенн, внутр, класс");
        bot.addTags("Nested Inner Class", "нестатическ, вложенн, внутр, класс");
        bot.addTags("Local Inner Class", "локальн, нестатическ, вложенн, внутр, класс");
        bot.addTags("Anonimous Class", "анонимн, внутр, класс");
        bot.addTags("Interface", "интерфейс, абстрактн, класс, полиморфизм");

//      особые классы
        bot.addTags("System", "систем, класс, вспомогательн, print");
        bot.addTags("Runtime", "рантайм, класс, вспомогательн");
        bot.addTags("Process", "процесс, класс, вспомогательн");

//      лямбда-выражение
        bot.addTags("Lambda", "лямбд, сокращённ, интерфейс");
        bot.addTags("lam2", "lam2");
        bot.addTags("Functional I", "интерфейс, функциональн, абстрактн, консумер, предик, сапла, саппла");
        bot.addTags("QuadroPoint", "оператор, квадроточ");
        bot.addTags("Stream", "стрим, апи");
        bot.addTags("Strea2", "strea2");
        bot.addTags("Stre3", "stre3");
        bot.addTags("Optional", "опци");
        bot.addTags("Composition", "композиц");
        bot.addTags("Switch", "свич, оператор");

//      рекурсия
        bot.addTags("Recurtion", "рекурсия, обратн, метод");
        bot.addTags("Recurt2", "recurt2");

//      дженерики
        bot.addTags("Generic", "генерик, дженерик");
        bot.addTags("Generi2", "generi2");
        bot.addTags("Gener3", "gener3");
        bot.addTags("Gene4", "gene4");
        bot.addTags("Gen5", "gen5");

//      массивы
        bot.addTags("Array", "массив, хранение, данн");
        bot.addTags("DynArr", "динамическ, массив, хранение, данн");
        bot.addTags("DynArrBlocked", "динамическ, массив, хранение, данн, блоч, блок");
        bot.addTags("PageArr", "страничн, массив, хранение, данн");

//      сборщики мусора
        bot.addTags("Garbage", "сборщик, мусор, коллектор");
        bot.addTags("Garb2", "garb2");
        bot.addTags("Serial", "сборщик, мусор, коллектор, сериал");
        bot.addTags("Parallel", "сборщик, мусор, коллектор, параллел");
        bot.addTags("G1", "сборщик, мусор, коллектор");
        bot.addTags("CMS", "сборщик, мусор, коллектор");
        bot.addTags("Shenandoah", "сборщик, мусор, коллектор, шенандо");

//      типы ссылок
        bot.addTags("Link", "ссыл, слабая, сильная, фантомная, мягкая, рантайм");
        bot.addTags("Lin2", "lin2");

        bot.start();
    }
}