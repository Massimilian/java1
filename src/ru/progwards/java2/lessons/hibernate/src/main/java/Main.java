import dao.HibernateUtil;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;

/* 
--старт приложения
BEGIN;
CREATE TABLE IF NOT EXISTS user_info (id SERIAL PRIMARY KEY, address TEXT, phone TEXT);
CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, login TEXT UNIQUE NOT NULL, pass VARCHAR(15) NOT NULL, user_info_id INTEGER REFERENCES user_info(id));
CREATE TABLE IF NOT EXISTS mailbox (id SERIAL PRIMARY KEY, name TEXT NOT NULL, num_of_news INTEGER DEFAULT 0, num_of_has_read INTEGER DEFAULT 0, num_of_has_sent INTEGER DEFAULT 0, users_id INTEGER REFERENCES users(id));
CREATE TABLE IF NOT EXISTS letter (id SERIAL PRIMARY KEY, soldDT TIMESTAMP DEFAULT now(), readDT TIMESTAMP, info text, from_mailbox_id INTEGER REFERENCES mailbox(id), to_mailbox_id INTEGER REFERENCES mailbox(id), is_deleted_from_receiver BOOLEAN DEFAULT false, is_deleted_from_sender BOOLEAN DEFAULT false, is_read BOOLEAN DEFAULT false);

--создание автоматического почтового ящика пользователю
CREATE OR REPLACE FUNCTION set_addmailbox_trigger()
RETURNS TRIGGER
AS
$$ BEGIN
INSERT INTO mailbox(name, users_id) VALUES (CONCAT(NEW.login, '@pochta.ru'), NEW.id);
RETURN NEW;
END $$
LANGUAGE 'plpgsql';

CREATE TRIGGER addmailbox
AFTER INSERT ON users
FOR EACH ROW
EXECUTE PROCEDURE set_addmailbox_trigger();

--удаление всех почтовых ящиков пользователя вместе с пользователем
CREATE OR REPLACE FUNCTION delete_mailboxes_trigger()
RETURNS TRIGGER
AS
$$ BEGIN
DELETE FROM mailbox WHERE users_id = OLD.id;
RETURN OLD;
END $$
LANGUAGE 'plpgsql';

CREATE OR REPLACE TRIGGER delete_mailboxes
BEFORE DELETE ON users
FOR EACH ROW
EXECUTE PROCEDURE delete_mailboxes_trigger();

--изменение статистики mailbox в связи с отправкой и получением письма
CREATE OR REPLACE FUNCTION insert_letter_trigger()
RETURNS TRIGGER
AS
$$ BEGIN
UPDATE mailbox SET num_of_news = (num_of_news + 1) WHERE id = NEW.to_mailbox_id;
UPDATE mailbox SET num_of_has_sent = (num_of_has_sent + 1) WHERE id=NEW.from_mailbox_id;
RETURN NEW;
END $$
LANGUAGE 'plpgsql';

CREATE OR REPLACE TRIGGER insert_letter
AFTER INSERT ON letter
FOR EACH ROW
EXECUTE PROCEDURE insert_letter_trigger();

--изменение статистики mailbox в связи с удалением письма "вручную"
CREATE OR REPLACE FUNCTION delete_letter_trigger()
RETURNS TRIGGER
AS
$$ BEGIN
UPDATE mailbox SET num_of_has_sent = (num_of_has_sent - 1) WHERE id=OLD.from_mailbox_id;
IF (OLD.is_deleted_from_receiver='false' AND OLD.is_read='false') THEN
UPDATE mailbox SET num_of_news = (num_of_news - 1) WHERE id=OLD.to_mailbox_id;
END IF;
IF (OLD.is_deleted_from_receiver='true' AND OLD.is_read='false') THEN
UPDATE mailbox SET num_of_news = (num_of_news - 1) WHERE id=0;
END IF;
IF (OLD.is_deleted_from_receiver='false' AND OLD.is_read='true') THEN
UPDATE mailbox SET num_of_has_read = (num_of_has_read - 1) WHERE id=OLD.to_mailbox_id;
END IF;
IF (OLD.is_deleted_from_receiver='true' AND OLD.is_read='true') THEN
UPDATE mailbox SET num_of_has_read = (num_of_has_read - 1) WHERE id = 0;
END IF;
RETURN OLD;
END $$
LANGUAGE 'plpgsql';

CREATE OR REPLACE TRIGGER delete_letter
AFTER DELETE ON letter
FOR EACH ROW
EXECUTE PROCEDURE delete_letter_trigger();

--удаление всех писем,привязанных к ящику, в связи с его удалением.
CREATE OR REPLACE FUNCTION delete_all_letters_from_mailbox_trigger()
RETURNS TRIGGER
AS
$$ BEGIN
UPDATE letter SET is_deleted_from_sender = true WHERE from_mailbox_id = OLD.id;
UPDATE letter SET is_deleted_from_receiver = true WHERE to_mailbox_id = OLD.id;
RETURN OLD;
END $$
LANGUAGE 'plpgsql';

CREATE OR REPLACE TRIGGER delete_all_letters_from_mailbox
BEFORE DELETE ON mailbox
FOR EACH ROW
EXECUTE PROCEDURE delete_all_letters_from_mailbox_trigger();

--удаление адреса при удалении пользователя, если у адреса не осталось привязанных пользователей.
CREATE OR REPLACE FUNCTION delete_empty_address_trigger()
RETURNS TRIGGER
AS
$$ BEGIN
DELETE FROM user_info WHERE OLD.user_info_id = id AND ((SELECT COUNT(*) from users WHERE user_info_id=OLD.user_info_id) = 0);
RETURN OLD;
END $$
LANGUAGE 'plpgsql';

CREATE OR REPLACE TRIGGER delete_empty_address
AFTER DELETE ON users
FOR EACH ROW
EXECUTE PROCEDURE delete_empty_address_trigger();


INSERT INTO users (id, login, pass) VALUES (0, 'Keeper', 'keeper');
UPDATE mailbox SET id = 0 WHERE users_id = 0;

COMMIT;
*/

@Log4j2
public class Main {
    public static void main(String[] args) {
        log.fatal("Hello world from Hibernate!");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Actioner actioner = new Actioner();
        boolean toCont = true;
        while(toCont) {
            toCont = actioner.mainAction();
            actioner.reNew();
        }
        session.close();
        HibernateUtil.shutdown();
    }
}
