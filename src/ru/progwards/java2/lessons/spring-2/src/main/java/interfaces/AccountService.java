package interfaces;

import exceptions.NotEnoughMoneyException;
import exceptions.UnknownAccountException;

public interface AccountService {
    void withdrow(int accountId, int amount) throws NotEnoughMoneyException, UnknownAccountException;
    void balance(int accountId) throws UnknownAccountException;
    void deposit(int accountId, int amount) throws NotEnoughMoneyException, UnknownAccountException;
    void transfer(int from, int to, int amount) throws NotEnoughMoneyException, UnknownAccountException;
}
