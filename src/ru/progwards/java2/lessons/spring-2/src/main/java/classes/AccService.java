package classes;

import exceptions.NotEnoughMoneyException;
import exceptions.UnknownAccountException;
import interfaces.AccountService;
import interfaces.Store;

import java.util.ArrayList;
import java.util.List;

public class AccService implements AccountService {
    private List<Account> accounts;
    private final Store store;

    public AccService(Store store) {
        this.store = store;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void init() {
        this.accounts = store.read();
        if (this.accounts.size() == 1 && accounts.get(0) == null) {
            this.accounts = new ArrayList<>();
            fillTen();
        }
    }

    private void fillTen() {
        for (int i = 0; i < 10; i++) {
            Account temp = new Account();
            temp.setHolder("CardHolder №" + i);
            temp.setId(i);
            temp.setAmount(i * 1000);
            this.accounts.add(temp);
        }
    }

    public void destoy() {
        for (Object account : accounts) {
            store.write(account);
        }
    }

    @Override
    public void withdrow(int accountId, int amount) throws NotEnoughMoneyException, UnknownAccountException {
        boolean hasNoAccount = true;
        for (Account account : accounts) {
            if (account.getId() == accountId) {
                hasNoAccount = false;
                if (account.getAmount() >= amount) {
                    account.setAmount(account.getAmount() - amount);
                } else {
                    throw new NotEnoughMoneyException();
                }
                break;
            }
        }
        if (hasNoAccount) {
            throw new UnknownAccountException();
        }
    }


    @Override
    public void balance(int accountId) throws UnknownAccountException {
        boolean hasNoAccount = true;
        for (Account account : accounts) {
            if (account.getId() == accountId) {
                hasNoAccount = false;
                System.out.printf("Your balance = %d, card holder's name is %s.", account.getAmount(), account.getHolder());
            }
        }
        if (hasNoAccount) {
            throw new UnknownAccountException();
        }
    }

    @Override
    public void deposit(int accountId, int amount) throws NotEnoughMoneyException, UnknownAccountException {
        this.withdrow(accountId, amount * -1);
    }

    @Override
    public void transfer(int from, int to, int amount) throws NotEnoughMoneyException, UnknownAccountException {
        this.withdrow(from, amount);
        this.deposit(to, amount);
    }
}
