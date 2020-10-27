package ru.progwards.java1.lessons.telegram_bot;

import org.telegram.telegrambots.ApiContextInitializer;
import ru.progwards.java1.testlesson.ProgwardsTelegramBot;

public class PizzaBot extends ProgwardsTelegramBot { // ProgwardsTelegramBot скачан с Progwards
    private final String menu = "У нас есть пицца";


    @Override
    public String processMessage(String text) {
        checkTags(text); // автоподбор по тегам того варианта, который нам подходит
        if (foundCount() == 1) { // проверяем количество найденных совпадений
            if (checkLastFound("привет")) { // если последнее найденное соответствует
                return "Приветствую тебя, о мой повелитель!\n Что желаешь? \n" + menu;
            }
            if (checkLastFound("конец")) {
                return "Спасибо за заказ.";
            }
            if (checkLastFound("дурак")) {
                return "Не надоругаться. Я не волшебник, я только учусь";
            }
        }
        if (foundCount() > 1) {
            return "Под твой запрос подходит: \n" + extract() + "Выбери что-то одно, и я добавлю это в заказ.";  // возвращает полученное множество, которое получилось в результате поиска, отсортированный по количеству совпадений (от самого большого)
        }
        return "Я не понял, возможно у нас этого нет, попробуй сказать по другому. \n" + menu; // вернётся, если совпадений не найдено
    }

    public static void main(String[] args) {
        System.out.println("Hello bot!");
        ApiContextInitializer.init();

        PizzaBot bot = new PizzaBot();
        bot.username = "pizza24maslov_bot"; // имя бота необходимо зарегистрировать у BotFather в Telegram
        bot.token = "1316252274:AAHvFwyVw7exsgUHzFTUa3t4WnKnbu_G79E"; // при регистрации будет выдан оригинальный токен

        bot.addTags("привет", "привет, здрасьте, здравствуй, добр, день, вечер, утро, hi, hello"); // приветствование
        bot.addTags("конец", "конец, все, стоп, нет");
        bot.addTags("дурак", "дурак, придурок, идиот, тупой");

        bot.addTags("Пицца гавайская", "гавайск, пицц, ананас, куриц"); // добавляем тэги: первый параметр - наименование (товара), второй параметр - признаки, по которым товар будет выделяться (теги для поиска)
        bot.addTags("Пицца маргарита", "маргарит, пицц, моцарелла, сыр, кетчуп, помидор");
        bot.addTags("Пицца пеперони", "пеперони, пицц, салями, колбас, сыр, кетчуп, помидор");

        bot.addTags("Торт тирамису", "десерт, кофе, маскарпоне, бисквит");
        bot.addTags("Торт медовик", "десерт, мед, бисквит");
        bot.addTags("Эклеры", "десерт, заварной, крем, тесто");

        bot.addTags("Кола", "напит, пить, кола");
        bot.addTags("Холодный чай", "напит, пить, чай, липтон, лимон");
        bot.addTags("Сок", "напит, пить, сок, апельсиноый, яблочный, вишневый");
        bot.start();

    }
}
