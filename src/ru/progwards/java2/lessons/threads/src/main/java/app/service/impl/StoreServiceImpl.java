package app.service.impl;

import app.Store;
import app.model.Account;
import app.service.StoreService;

import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StoreServiceImpl implements StoreService {
    Lock lock = new ReentrantLock();

    @Override
    public Account get(String id) {
        lock.lock();
        Account account = Store.getStore().get(id);
        if (account == null) {
            lock.unlock();
            throw new RuntimeException("Account not found by id:" + id);
        }
        lock.unlock();
        return account;
    }

    @Override
    public Collection<Account> get() {
        lock.lock();
        if (Store.getStore().size() == 0) {
            lock.unlock();
            throw new RuntimeException("Store is empty");
        }
        lock.unlock();
        return Store.getStore().values();
    }

    @Override
    public void delete(String id) {
        lock.lock();
        if (Store.getStore().get(id) == null) {
            lock.unlock();
            throw new RuntimeException("Account not found by id:" + id);
        }
        Store.getStore().remove(id);
        lock.unlock();
    }

    @Override
    public void insert(Account account) {
        lock.lock();
        Store.getStore().put(account.getId(), account);
        lock.unlock();
    }

    @Override
    public void update(Account account) {
        lock.lock();
        if (Store.getStore().get(account.getId()) == null) {
            lock.unlock();
            throw new RuntimeException("Account not found by id:" + account.getId());
        }
        this.insert(account);
        lock.unlock();
    }
}