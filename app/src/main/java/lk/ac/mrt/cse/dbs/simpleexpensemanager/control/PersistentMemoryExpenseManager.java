package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.ui.MainActivity;

public class PersistentMemoryExpenseManager extends ExpenseManager{
    private static final String DATABASE = "expenseManager";
    private Context context;
    public PersistentMemoryExpenseManager(Context context) {
        this.context = context;
        setup();
    }

    @Override
    public void setup() {

        TransactionDAO PersistentMemoryTransactionDAO = new PersistentMemoryTransactionDAO(this.context);
        setTransactionsDAO(PersistentMemoryTransactionDAO);

        AccountDAO PersistentMemoryAccountDAO = new PersistentMemoryAccountDAO(this.context);
        setAccountsDAO(PersistentMemoryAccountDAO);

    }
}
