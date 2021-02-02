package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentMemoryTransactionDAO  extends SQLiteOpenHelper implements TransactionDAO {
    private static final String DATABASE = "transactions";
    private static final String TABLE_NAME = "transactionsDetails";

    private final List<Transaction> transactions;

    public PersistentMemoryTransactionDAO(@Nullable Context context) {
        super(context,DATABASE +".db",null,1);
        transactions = getTransactions();
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType type, double amount) {
        Transaction transaction = new Transaction(date, accountNo, type, amount);
        transactions.add(transaction);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy" );

        cv.put("date", dateFormat.format(date));
        cv.put("accountNo", accountNo);
        cv.put("type", type.toString());
        cv.put("amount", amount);

        long insert = db.insert(TABLE_NAME, null, cv);
        if( insert == -1) {
            //return false;
        }else{
            //return true;
        }
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        return transactions;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        int size = transactions.size();
        if (size <= limit) {
            return transactions;
        }
        // return the last <code>limit</code> number of transaction logs
        return transactions.subList(size - limit, size);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (id INTEGER  PRIMARY KEY AUTOINCREMENT, date TEXT, accountNo TEXT, type TEXT, amount REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public List<Transaction> getTransactions () {
        List<Transaction> transactionsList = new LinkedList<>();
        String queryString = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery(queryString, null);
        if (result.moveToFirst()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            do {
                Date date = null;
                try {
                    date = dateFormat.parse(result.getString(1));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                String accountNo = result.getString(2);
                ExpenseType type = ExpenseType.valueOf(result.getString(3).toUpperCase());
                double amount = result.getDouble(4);

                Transaction transaction = new Transaction(date, accountNo, type, amount);
                transactionsList.add(transaction);

            } while (result.moveToNext());
            result.close();
            db.close();
        }
        return transactionsList;
    }
}
