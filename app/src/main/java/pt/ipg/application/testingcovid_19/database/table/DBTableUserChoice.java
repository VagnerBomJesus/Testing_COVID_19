package pt.ipg.application.testingcovid_19.database.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;

import java.util.Arrays;

public class DBTableUserChoice implements BaseColumns {
    public static final String TABLE_NAME = "user_choice";

    public static final String COLUMN_FK_USER = "fk_user";
    public static final String COLUMN_FK_CHOICE = "fk_choice";

    public static final String COLUMN_FULL_ID = TABLE_NAME + "." + _ID;
    public static final String COLUMN_FULL_FK_USER = TABLE_NAME + "." + COLUMN_FK_USER;
    public static final String COLUMN_FULL_FK_CHOICE = TABLE_NAME + "." + COLUMN_FK_CHOICE;

    // USER FIELD's
    public static final String COLUMN_FULL_FK_USER_NAME = DBTableUser.COLUMN_FULL_NAME + " AS " + DBTableUser.COLUMN_NAME;
    public static final String COLUMN_FULL_FK_USER_GENDER = DBTableUser.COLUMN_FULL_GENDER + " AS " + DBTableUser.COLUMN_GENDER;
    public static final String COLUMN_FULL_FK_USER_TIN = DBTableUser.COLUMN_FULL_TIN + " AS " + DBTableUser.COLUMN_TIN;
    public static final String COLUMN_FULL_FK_USER_EMAIL = DBTableUser.COLUMN_FULL_EMAIL + " AS " + DBTableUser.COLUMN_EMAIL;
    public static final String COLUMN_FULL_FK_USER_PHONE = DBTableUser.COLUMN_FULL_PHONE + " AS " + DBTableUser.COLUMN_PHONE;
    public static final String COLUMN_FULL_FK_USER_BIRTHDAY = DBTableUser.COLUMN_FULL_BIRTHDAY + " AS " + DBTableUser.COLUMN_BIRTHDAY;
    public static final String COLUMN_FULL_FK_USER_DISTRICT = DBTableUser.COLUMN_FULL_DISTRICT + " AS " + DBTableUser.COLUMN_DISTRICT;
    public static final String COLUMN_FULL_FK_USER_COUNTRY = DBTableUser.COLUMN_FULL_COUNTRY + " AS " + DBTableUser.COLUMN_COUNTRY;
    public static final String COLUMN_FULL_FK_USER_CREATED_AT = DBTableUser.COLUMN_FULL_CREATED_AT + " AS " + DBTableUser.COLUMN_CREATED_AT;

    // CHOICE FIELD's
    public static final String COLUMN_FULL_FK_CHOICE_CHOICE = DBTableChoice.COLUMN_FULL_CHOICE + " AS " + DBTableChoice.COLUMN_CHOICE;
    public static final String COLUMN_FULL_FK_CHOICE_WEIGHT = DBTableChoice.COLUMN_FULL_WEIGHT + " AS " + DBTableChoice.COLUMN_WEIGHT;
    public static final String COLUMN_FULL_FK_CHOICE_TYPE = DBTableChoice.COLUMN_FULL_TYPE + " AS " + DBTableChoice.COLUMN_TYPE;

    public static final String[] ALL_COLUMN = {
            _ID, COLUMN_FK_USER, COLUMN_FK_CHOICE
    };

    private SQLiteDatabase db;

    public DBTableUserChoice(SQLiteDatabase db) {
        this.db = db;
    }

    public void create() {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_FK_USER + " INTEGER NOT NULL," +
                COLUMN_FK_CHOICE + " INTEGER NOT NULL," +
                "FOREIGN KEY (" + COLUMN_FK_USER + ") REFERENCES " +
                DBTableUser.TABLE_NAME + "("+ DBTableUser._ID + "), " +
                "FOREIGN KEY (" + COLUMN_FK_CHOICE + ") REFERENCES " +
                DBTableChoice.TABLE_NAME + "("+ DBTableChoice._ID + ")" +
                ")");
    }

    public long insert(ContentValues values) {
        return db.insert(TABLE_NAME, null, values);
    }

    public Cursor query(String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy) {
        if (
                !Arrays.asList(columns).contains(COLUMN_FULL_FK_USER_NAME) &&
                !Arrays.asList(columns).contains(COLUMN_FULL_FK_USER_GENDER) &&
                !Arrays.asList(columns).contains(COLUMN_FULL_FK_USER_TIN) &&
                !Arrays.asList(columns).contains(COLUMN_FULL_FK_USER_EMAIL) &&
                !Arrays.asList(columns).contains(COLUMN_FULL_FK_USER_PHONE) &&
                !Arrays.asList(columns).contains(COLUMN_FULL_FK_USER_BIRTHDAY) &&
                !Arrays.asList(columns).contains(COLUMN_FULL_FK_USER_DISTRICT) &&
                !Arrays.asList(columns).contains(COLUMN_FULL_FK_USER_COUNTRY) &&
                !Arrays.asList(columns).contains(COLUMN_FULL_FK_USER_CREATED_AT) &&

                !Arrays.asList(columns).contains(COLUMN_FULL_FK_CHOICE_CHOICE) &&
                !Arrays.asList(columns).contains(COLUMN_FULL_FK_CHOICE_WEIGHT) &&
                !Arrays.asList(columns).contains(COLUMN_FULL_FK_CHOICE_TYPE)
        ) {
            return db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
        }

        String campos = TextUtils.join(",", columns);

        String sql = "SELECT " + campos;
        sql += " FROM " + TABLE_NAME;

        sql += " INNER JOIN " + DBTableUser.TABLE_NAME;
        sql += " ON " + COLUMN_FULL_FK_USER + "=" + DBTableUser.COLUMN_FULL_ID;

        sql += " INNER JOIN " + DBTableChoice.TABLE_NAME;
        sql += " ON " + COLUMN_FULL_FK_CHOICE + "=" + DBTableChoice.COLUMN_FULL_ID;

        if (selection != null) {
            sql += " WHERE " + selection;
        }

        if (groupBy != null) {
            sql += " GROUP BY " + groupBy;

            if (having != null) {
                sql += " HAVING " + having;
            }
        }

        if (orderBy != null) {
            sql += " ORDER BY " + orderBy;
        }

        return db.rawQuery(sql, selectionArgs);
    }

    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        return db.update(TABLE_NAME, values, whereClause, whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(TABLE_NAME, whereClause, whereArgs);
    }

    public long count(){
        Cursor cursor = query(ALL_COLUMN, null, null, null, null, null);
        return cursor.getCount();
    }
}