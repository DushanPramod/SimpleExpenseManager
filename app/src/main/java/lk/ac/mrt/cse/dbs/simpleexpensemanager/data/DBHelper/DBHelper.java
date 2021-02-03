package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE = "180486R";

    private static final String ACCOUNT_TABLE = "accountDetails";
    private static final String TRANSACTIONS_TABLE = "transactionsDetails";

    public DBHelper(Context context){
        super(context,DATABASE +".db",null,1);
    }

    public Map<String, Account> getAccounts(){
        Map<String, Account> accounts = new HashMap<>();
        String query = "SELECT * FROM " + ACCOUNT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery(query, null);
        if(result.moveToFirst()) {
            do {
                String accountNo = result.getString(0);
                String bankName = result.getString(1);
                String accountHolderName = result.getString(2);
                double balance = result.getDouble(3);

                Account account = new Account(accountNo, bankName, accountHolderName, balance);
                accounts.put(account.getAccountNo(), account);

            } while ( result.moveToNext() );
        } else {

        }
        result.close();
        db.close();
        return accounts;
    }

    public void addAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("accountNo", account.getAccountNo());
        cv.put("bankName", account.getBankName());
        cv.put("accountHolderName", account.getAccountHolderName());
        cv.put("balance", account.getBalance());

        long insert = db.insert(ACCOUNT_TABLE, null, cv);
        if( insert == -1) {
            //return false;
        }else{
            //return true;
        }
    }



    public List<Transaction> getTransactions () {
        List<Transaction> transactionsList = new LinkedList<>();
        String queryString = "SELECT * FROM " + TRANSACTIONS_TABLE;
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

    public void transaction(Date date, String accountNo, ExpenseType type, double amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy" );

        cv.put("date", dateFormat.format(date));
        cv.put("accountNo", accountNo);
        cv.put("type", type.toString());
        cv.put("amount", amount);

        long insert = db.insert(TRANSACTIONS_TABLE, null, cv);
        if( insert == -1) {
            //return false;
        }else{
            //return true;
        }
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ ACCOUNT_TABLE + " (accountNo TEXT PRIMARY KEY, bankName TEXT, accountHolderName TEXT, balance REAL)");
        db.execSQL("CREATE TABLE " + TRANSACTIONS_TABLE + " (id INTEGER  PRIMARY KEY AUTOINCREMENT, date TEXT, accountNo TEXT, type TEXT, amount REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TRANSACTIONS_TABLE);
    }
}
