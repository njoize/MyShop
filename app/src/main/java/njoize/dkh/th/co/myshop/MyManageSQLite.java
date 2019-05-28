package njoize.dkh.th.co.myshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MyManageSQLite {

    private Context context;
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public MyManageSQLite(Context context) {
        this.context = context;

        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();
    }

    public long addValueToSQLite(String idPricelistString,
                                 String nameProductString,
                                 String priceString,
                                 String quantityString,
                                 String amountString) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("idPricelist", idPricelistString);
        contentValues.put("nameProduct", nameProductString);
        contentValues.put("price", priceString);
        contentValues.put("quantity", quantityString);
        contentValues.put("amount", amountString);
        return sqLiteDatabase.insert("orderTABLE", null, contentValues);
    }

}
