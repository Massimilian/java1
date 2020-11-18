package ru.progwards.java1.lessons.io2;

/**
 * Class to prepare a phone number
 */
public class PhoneNumber {
    /**
     * Main static method of preparing the number
     *
     * @param phone number
     * @return prepared phone number
     * @throws WrongPhoneException if number is impossible
     */
    public static String format(String phone) throws WrongPhoneException {
        PhoneNumber pn = new PhoneNumber();
        return pn.format(phone.toCharArray());
    }

    /**
     * Main non-static method of preparing the number
     *
     * @param phone number
     * @return prepared phone number
     * @throws WrongPhoneException if number is impossible
     */
    public String format(char[] phone) throws WrongPhoneException {
        phone = this.deleteEight(this.clean(phone));
        if (!checkNums(phone)) {
            throw new WrongPhoneException("Impossible number of phone.");
        }
        return new String(finPrepare(phone));
    }

    /**
     * Cleaning the number of different impurities
     *
     * @param phone number
     * @return cleaned number
     */
    private char[] clean(char[] phone) {
        char[] result = new char[phone.length];
        int pos = 0;
        for (int i = 0; i < phone.length; i++) {
            if (i == 0 && phone[i] == '+') {
                result[pos++] = phone[i++];
            }
            if (Character.isDigit(phone[i])) {
                result[pos++] = phone[i];
            }
        }
        return result;
    }

    /**
     * Preparing the number without '8'
     *
     * @param phone with 8 (maybe)
     * @return phone number with '+7'
     */
    private char[] deleteEight(char[] phone) {
        if (phone[0] == '8') {
            char[] number = new char[phone.length + 1];
            number[0] = '+';
            number[1] = '7';
            System.arraycopy(phone, 1, number, 2, phone.length - 1);
            phone = number;
        }
// эта проверка не предусмотрена условиями задачи, но кажется мне уместной
//        if (phone[0] == '0' && phone[1] == '0' && phone[2] == '7') {
//            char[] number = new char[phone.length - 1];
//            number[0] = '+';
//            number[1] = '7';
//            System.arraycopy(phone, 3, number, 2, phone.length - 3);
//            phone = number;
//        }
        return new String(phone).trim().toCharArray();
    }

    /**
     * Check that it is a real phone number
     *
     * @param phone number to check
     * @return real/not
     */
    private boolean checkNums(char[] phone) {
        return phone[0] == '+' && phone[1] == '7' && phone.length == 12;
    }

    /**
     * Final prepare to return the number
     *
     * @param phone non-prepared number
     * @return prepared number
     */
    private char[] finPrepare(char[] phone) {
        char[] result = new char[phone.length + 3];
        int pos = 0;
        for (int i = 0; i < phone.length; i++) {
            result[pos++] = phone[i];
            if (i == 1) {
                result[pos++] = '(';
            }
            if (i == 4) {
                result[pos++] = ')';
            }
            if (i == 7) {
                result[pos++] = '-';
            }
        }
        return result;
    }


    public static void main(String[] args) throws WrongPhoneException {
        assert format("88888888888").equals("+7(888)888-8888");
        assert format("0074954264164").equals("+74954264164");
        assert format("dfgkjhjfhg8fghjgij4ijfg9kjgij5ijgijij1kkjj2hhl3llkj4lkjlkj5-=-mlk6lkm7=-lkm///!@#$%^").equals("+7(495)123-4567");
        assert format("+7(495)123-4567").equals("+7(495)123-4567");
    }
}
