import entity.Letter;
import entity.Mailbox;
import entity.UserInfo;
import entity.Users;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
Класс для работы пользователя
 */
public class Actioner {
    private Users user;
    private Scanner scanner = new Scanner(System.in);

    /*
    Конструктор помогает ввести логин-пароль и достаёт из БД соответствующего пользователя
     */
    public Actioner() {
        System.out.println("Если хотите создать нового пользователя - наберите слово 'новый' (или 'new') и нажмите ENTER. Если хотите зайти по старому логину - просто нажмите ENTER.");
        String isNew = scanner.nextLine();
        if (isNew.equalsIgnoreCase("новый") || isNew.equalsIgnoreCase("new")) {
            user = getNewLogin();
        } else {
            user = getOldLogin();
        }
        System.out.println("Здравствуйте, " + user.getLogin()+ "!");
    }

    /*
    Обновление пользователя через БД
     */
    public void reNew() {
        this.user = user.get(user.getId());
    }

    /*
    Главное меню программы
     */
    public boolean mainAction() {
        boolean toCont = true;
        System.out.println("Вы в основном меню программы. Ваши почтовые ящики: ");
        System.out.println(getmailBoxesNames());
        System.out.println("Выберите действие:");
        System.out.println("    1. Перейти к почтовым ящикам.");
        System.out.println("    2. Редактировать информацию.");
        System.out.println("    3. Выйти из программы.");
        String answer = scanner.nextLine();
        if (answer.equals("1")) {
            enterMailBoxes();
        } else if (answer.equals("2")) {
            editInfo();
        } else if (answer.equals("3")) {
            System.out.println("Спасибо за пользование программой! До свидания!");
            toCont = false;
        }
        return toCont;
    }

    /*
    Меню редактирования информации о пользователе
     */
    private int editUserInfo() {
        System.out.println("Введите Ваш адрес.");
        String address = scanner.nextLine();
        System.out.println("Введите Ваш телефон.");
        String phone = scanner.nextLine();
        address = checkInfo(address);
        phone = checkInfo(phone);
        return setUniqueUserInfoAndReturnId(address, phone);
    }

    /*
    Меню редактирования/удаления информации о пользователе
     */
    private void editInfo() {
        boolean toCont = false;
        System.out.println("Вы в меню редактирования информации. Выберите действие. Если хотите вернуться назад - просто нажмите ENTER.");
        while (!toCont) {
            System.out.println("    1. Добавить почтовый ящик.");
            System.out.println("    2. Удалить почтовый ящик.");
            System.out.println("    3. Посмотреть информацию о пользователе.");
            System.out.println("    4. Редактировать информацию о пользователе.");
            System.out.println("    5. Полностью удалить пользователя и почтовые ящики.");
            String answer = scanner.nextLine();
            if (!answer.isEmpty()) {
                if (answer.equals("1")) {
                    createNewMailbox(this.user.getId());
                    toCont = true;
                } else if (answer.equals("2")) {
                    deleteMailbox();
                } else if (answer.equals("3")) {
                    System.out.println(new UserInfo().get(user.getUserInfoId()));
                } else if (answer.equals("4")) {
                    int uiId = editUserInfo();
                    user.setUserInfoId(uiId);
                    user.updateUI(uiId);
                } else if (answer.equals("5")){
                    user.delete();
                    System.out.println("Пользователь успешно удалён. Спасибо за пользование программой!");
                    System.exit(0);
                } else {
                    System.out.println("Выберите корректную опцию.");
                }
            } else {
                toCont = true;
            }
        }
    }

    /*
    Меню удаления почтового ящика
     */
    private void deleteMailbox() {
        if (this.user.getMailboxes().size() > 0) {
            System.out.println("Введите имя ящика (можно без \"@pochta.ru\")");
            String name = getPreparedAddress();
            Mailbox mb = null;
            for (int i = 0; i < this.user.getMailboxes().size(); i++) {
                String temp = this.user.getMailboxes().get(i).getName();
                if (name.equals(temp)) {
                    mb = this.user.getMailboxes().get(i);
                    user.getMailboxes().remove(i);
                    break;
                }
            }
            if (mb == null) {
                System.out.println("Почтовый ящик не найден.");
            } else {
                mb.delete();
                System.out.println(String.format("Почтовый ящик %s удалён.", mb.getName()));
            }
        } else {
            System.out.println("Нет почтовых ящиков для удаления!");
        }

    }

    /*
    Меню создания и привязки нового почтового ящика
     */
    private void createNewMailbox(Integer id) {
        String mailboxName = createMailboxName();
        Mailbox mb = new Mailbox();
        mb.setName(mailboxName);
        mb.setUsersId(id);
        mb.add();
        System.out.println(String.format("Новый почтовый ящик %s привязан к Вашей учётной записи", mailboxName));
    }

    /*
    Создание имени нового почтового ящика
     */
    private String createMailboxName() {
        boolean toCont = false;
        String result = "";
        while (!toCont) {
            System.out.println("Введите логин нового ящика ('@pochta.ru' будет добавлено автоматически). В имение логина допускаются латинские буквы и цифры.");
            result = scanner.nextLine();
            if (!result.matches("[\\w]+")) {
                System.out.println("Найдены недопустимые спецсимволы. ПОпробуйте ещё раз.");
            } else {
                toCont = true;
            }
        }
        return result + "@pochta.ru";
    }

    /*
    Меню выбора почтового ящика
     */
    private void enterMailBoxes() {
        boolean toCont = true;
        while(toCont) {
            System.out.println("Наберите имя почтового ящика (можно без '@pochta.ru'). Если хотите вернуться назад - просто нажмите ENTER.");
            String answer = getPreparedAddress();
            if (!answer.isEmpty()) {
                ArrayList<String> names = new ArrayList<>();
                for (int i = 0; i < user.getMailboxes().size(); i++) {
                    names.add(user.getMailboxes().get(i).getName());
                }
                if (names.contains(answer)) {
                    Mailbox mb = new Mailbox();
                    mb = mb.getByTwoParameters(answer, String.valueOf(user.getId()));
                    workWithMailBox(mb);
                    toCont = false;
                } else {
                    System.out.println("Имя ящика не найдено");
                }
            } else {
                toCont = false;
            }
        }
    }

    /*
    Меню информации о почтовом ящике
     */
    private void workWithMailBox(Mailbox mb) { // todo
        System.out.println(String.format("Вы в почтовом ящике '%s'. Количество новых сообщений: %d. Чтобы вернуться в основное меню, нажмите ENTER.", mb.getName(), mb.getNumOfNews()));
        String answer = " ";
        while (!answer.isEmpty()) {
            System.out.println("Выберите действие:");
            System.out.println("    1. Прочитать новые сообщения.");
            System.out.println("    2. Прочитать старые сообщения.");
            System.out.println("    3. Прочитать отправленные сообщения.");
            System.out.println("    4. Написать письмо.");
            answer = scanner.nextLine();
            if (answer.equals("1")) {
                lookletters("new", mb);
            } else if (answer.equals("2")) {
                lookletters("old", mb);
            } else if (answer.equals("3")) {
                lookletters("sent", mb);
            } else if (answer.equals("4")) {
                sendletter(mb);
            }
            reNew();
        }
    }

    /*
    Меню отсылки письма
     */
    private void sendletter(Mailbox mb) {
        Letter letter = new Letter();
        Integer toId = null;
        String address = null;
        do {
            System.out.println("Введите адресата письма (можно без '@pochta.ru'):");
            address = getPreparedAddress();
            toId = mb.findIdByName(address);
            if (toId == null) {
                System.out.println("Адрес не найден. Уточните адрес.");
            }
        } while (toId == null);
        System.out.println("Введите сообщение для пользователя:");
        String message = scanner.nextLine();
        letter.setInfo(message);
        letter.setFromMailboxId(mb.getId());
        letter.setToMailboxId(toId);
        letter.setSolddt(Timestamp.from(Instant.now()));
        letter.setIsDeletedFromSender(false);
        letter.setIsDeletedFromReceiver(false);
        letter.setIsRead(false);
        letter.add();
        System.out.println("Сообщение отправлено на адрес " + address + ".");
    }

    private String getPreparedAddress() {
        String result = scanner.nextLine();
        if (!result.endsWith("@pochta.ru")) {
            result += "@pochta.ru";
        }
        return result;
    }

    /*
    Меню просмотра сообщений
     */
    private void lookletters(String type, Mailbox mb) {
        List<Letter> letters = null;
        if (type.equals("new")) {
            letters = mb.getNews();
        } else if (type.equals("old")) {
            letters = mb.getOlds();
        } else if (type.equals("sent")){
            letters = mb.getSents();
        }
        if (letters == null || letters.isEmpty()) {
            System.out.println("У Вас нет сообщений");
        } else {
            boolean fin;
            for (int i = 0; i < letters.size(); i++) {
                fin = readMessage(letters.get(i), type, mb);
                if (fin) {
                    break;
                }
            }
            System.out.println("Больше сообщений нет");
        }
    }

    /*
    Меню читки сообщений
     */
    private boolean readMessage(Letter letter, String status, Mailbox mb) {
        boolean fin = false;
        String action = status.equals("sent")? "отправлено" : "получено";
        System.out.println(String.format("Сообщение %s от %s, %s.", action, mb.get(letter.getFromMailboxId()).getName(), letter.getSolddt().toLocalDateTime().toString().substring(0, 10)));
        System.out.println("-----------------");
        System.out.println(letter.getInfo());
        System.out.println("-----------------");
        if (status.equals("new")) {
            letter.workWithNew();
            status = "old";
        }
        boolean act = true;
        while (act) {
            System.out.println("Выберите действие:");
            System.out.println("    1. Перейти к следующему сообщению.");
            System.out.println("    2. Удалить и перейти к следующему сообщению.");
            System.out.println("    3. Вернуться в главное меню");
            String answer = scanner.nextLine();
            if (answer.equals("1")) {
                act = false;
            } else if (answer.equals("2")) {
                if (status.equals("old")) {
                    letter.deleteOld();
                }
                if (status.equals("sent")) {
                    letter.deleteSent();
                }
                act = false;
                reNew();
            } else if (answer.equals("3")) {
                act = false;
                fin = true;
            }
            reNew();
        }
        return fin;
    }

    /*
    Получение имён всех почтовых ящиков и количества новых сообщений в них
     */
    private String getmailBoxesNames() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < user.getMailboxes().size(); i++) {
            sb.append(i + 1);
            sb.append(". ");
            sb.append(user.getMailboxes().get(i).getName());
            if (user.getMailboxes().get(i).getNumOfNews() != 0) {
                sb.append("(непрочитанных сообщений: ");
                sb.append(user.getMailboxes().get(i).getNumOfNews());
                sb.append(" )");
            }
            if (user.getMailboxes().size() == i + 1) {
                sb.append(".");
                sb.append(System.lineSeparator());
            } else {
                sb.append(",");
                sb.append(System.lineSeparator());
            }
        }
        return sb.isEmpty()? "отсутствуют": sb.toString();
    }

    /*
    Меню входа по старому ID
     */
    private Users getOldLogin() {
        int attempt = 0;
        boolean toCont = true;
        Users newUser = new Users();
        while (toCont) {
            if (newUser == null) {
                newUser = new Users();
            }
            System.out.println("Введите имя");
            String login = scanner.nextLine();
            System.out.println("Введите пароль. Если Вы забыли пароль - просто нажмите ENTER.");
            String password = scanner.nextLine();
            if (!login.isEmpty() && password.isEmpty()) {
                newUser = startRenewUser(login);
            } else {
                newUser = newUser.getByTwoParameters(login, password);
            }
            if (newUser == null) {
                if (attempt == 2) {
                    System.out.println("Пользователь не найден. Уточните данные и попробуйте ещё раз.");
                    System.exit(0);
                } else {
                    attempt++;
                    System.out.println("Логин и пароль не найдены. Попробуйте ещё раз.");
                }
            } else {
                toCont = false;
            }
        }
        return newUser;
    }

    /**
     * Старт пользователя при забытом пароле
     * @param login - предполагаемый логин
     * @return null либо пользователь, если сопутствующая информация указана верно
     */
    private Users startRenewUser(String login) {
        Users result = null;
        if (new Users().checkIfIs(login)) {
            System.out.println("Введите адрес, который вы указывали при регистрации. Если Вы не указывали адрес - напишите 'Не указано'.");
            String address = scanner.nextLine();
            System.out.println("Введите номер телефона, который вы указывали при регистрации. Если Вы не указывали телефон - напишите 'Не указано'.");
            String phone = scanner.nextLine();
            UserInfo ui = new UserInfo().getByTwoParameters(address, phone);
            if (ui != null) {
                result = new Users().getByLoginAndUII(login, ui.getId());
            }
        }
        if (result != null) {
            System.out.println("Запомните или запишите Ваш пароль: '" + result.getPass() + "'.");
        }
        return result;
    }

    /*
    Меню регистрации и входа по новому ID
     */
    private Users getNewLogin() {
        boolean isNotGood = true;
        int count = 0;
        String login = "";
        String password = "";
        while (isNotGood) {
            if (count == 3) {
                System.out.println("Допущено критическое количество ошибок. Программа будет завершена.");
                System.exit(0);
            }
            if (count != 0) {
                System.out.println("Давайте ещё раз введём всё заново.");
            }
            isNotGood = false;
            System.out.println("Введите имя нового пользователя. Поле не может быть пустым.");
            login = scanner.nextLine();
            System.out.println("Введите пароль. Поле не может быть пустым и превышать 15 знаков.");
            password = scanner.nextLine();
            if (login.length() == 0 || password.length() == 0) {
                System.out.println("Ошибка. Поле и пароль не могут быть пустыми.");
                isNotGood = true;
                count++;
            } else if (password.length() > 15) {
                System.out.println("Ошибка. Пароль слишком длинный, а он не должен превышать 15 знаков.");
                isNotGood = true;
                count++;
            } else if (new Users().checkIfIs(login)) {
                System.out.println("Такой пользователь уже зарегистрирован. Попробуйте другой логин");
                isNotGood = true;
            }
        }
        int uiId = editUserInfo();
        Users user = new Users(login, password);
        user = user.getByTwoParameters(user.getLogin(), user.getPass());
        if (user == null) {
            user = new Users(login, password);
            user.setUserInfoId(uiId);
            user.add();
        } else {
            System.out.println("Вы уже регистрировались в системе. Незачем было проходить повторную регистрацию.");
        }
        return user.getByTwoParameters(login, password);
    }

    /*
    Меню проверки заполнения адреса и телефона
     */
    private String checkInfo(String info) {
        if (info.isEmpty()) {
            info = "Не указано";
        }
        return info;
    }

    /*
    Проверка уникальности адреса и телефона и возврат ID
     */
    private int setUniqueUserInfoAndReturnId(String address, String phone) {
        UserInfo ui = new UserInfo(address, phone);
        ui.add();
        return ui.getByTwoParameters(address, phone).getId();
     }
}
