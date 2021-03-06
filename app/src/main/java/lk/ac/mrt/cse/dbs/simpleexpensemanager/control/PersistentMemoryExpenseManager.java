package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DBHelper.DBHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.ui.MainActivity;

public class PersistentMemoryExpenseManager extends ExpenseManager{
    private DBHelper dbHelper;

    public PersistentMemoryExpenseManager(Context context) {
        this.dbHelper = new DBHelper((context));
        setup();
    }

    @Override
    public void setup() {

        TransactionDAO PersistentMemoryTransactionDAO = new PersistentMemoryTransactionDAO(dbHelper);
        setTransactionsDAO(PersistentMemoryTransactionDAO);

        AccountDAO PersistentMemoryAccountDAO = new PersistentMemoryAccountDAO(dbHelper);
        setAccountsDAO(PersistentMemoryAccountDAO);

    }
}
