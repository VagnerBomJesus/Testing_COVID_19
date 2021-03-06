package pt.ipg.application.testingcovid_19;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import pt.ipg.application.testingcovid_19.database.Convert;
import pt.ipg.application.testingcovid_19.database.table.DBTableUser;
import pt.ipg.application.testingcovid_19.database.DatabaseOpenHelper;
import pt.ipg.application.testingcovid_19.object.User;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DB_User_AndroidJUnit {
    @Before
    @After
    public void deleteDatabase() {
        getTargetContext().deleteDatabase(DatabaseOpenHelper.NAME_DATABASE);
    }

    @Test
    public void OpenDatabase() {
        // Context of the app under test.
        Context appContext = getTargetContext();
        DatabaseOpenHelper openHelper = new DatabaseOpenHelper(appContext);
        SQLiteDatabase db = openHelper.getReadableDatabase();
        assertTrue(db.isOpen());
        db.close();
    }

    private Context getTargetContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    private long insertUser(DBTableUser tableUser, User user) {
        long id = tableUser.insert(Convert.userToContentValues(user));
        assertNotEquals(-1, id);

        return id;
    }

    private long insertUser(DBTableUser tableUser, String Name, String Gender, String TIN, String Email,
                            String Phone, String Birthday, String District, String Country, String Created_at) {

                User user = new User();
                user.setName(Name);
                user.setGender(Gender);
                user.setTin(TIN);
                user.setEmail(Email);
                user.setPhone(Phone);
                user.setBirthday(Birthday);
                user.setDistrict(District);
                user.setCountry(Country);
                user.setCreated_at(Created_at);

        return insertUser(tableUser, user);
    }

    @Test
    public void canInsertUsers() {
        Context appContext = getTargetContext();

        DatabaseOpenHelper openHelper = new DatabaseOpenHelper(appContext);
        SQLiteDatabase db = openHelper.getWritableDatabase();

        DBTableUser tableUser = new DBTableUser(db);

        long myid = insertUser(tableUser, "Agostinho", "M", "287273962", "a@gmail.com", "934927329", "26/03/1995", "Guarda", "Portugal", "yyyy-MM-dd HH:mm:ss.SSS");

        db.close();
    }

    @Test
    public void canReadUser(){
        Context appContext = getTargetContext();

        DatabaseOpenHelper openHelper = new DatabaseOpenHelper(appContext);
        SQLiteDatabase db = openHelper.getWritableDatabase();

        DBTableUser tableUser = new DBTableUser(db);

        Cursor cursor = tableUser.query(DBTableUser.ALL_COLUMN, null, null, null, null, null);
        int register = cursor.getCount();
        cursor.close();

        insertUser(tableUser, "Luis", "M", "287323962", "b@gmail.com", "934932329", "26/05/1995", "Lisboa", "Portugal", "yyyy-MM-dd HH:mm:ss.SSS");

        cursor = tableUser.query(DBTableUser.ALL_COLUMN, null, null, null, null, null);

        assertEquals(register + 1, cursor.getCount());
        cursor.close();

        db.close();
    }

    @Test
    public void canChangeUser(){
        Context appContext = getTargetContext();

        DatabaseOpenHelper openHelper = new DatabaseOpenHelper(appContext);
        SQLiteDatabase db = openHelper.getWritableDatabase();

        DBTableUser tableUser = new DBTableUser(db);

        // HERE FIND BY _ID ...
        User user = new User();
        user.setName("Agostinho");
        user.setGender("M");
        user.setTin("287273962");
        user.setEmail("a@gmail.com");
        user.setPhone("934927329");
        user.setBirthday("26/03/1995");
        user.setDistrict("Guarda");
        user.setCountry("Portugal");
        user.setCreated_at("yyyy-MM-dd HH:mm:ss.SSS");

        long id = insertUser(tableUser, user);

        // HERE STAY WHAT YOU GONNA CHANGE...
        user.setName("Augusto");
        // ...

        int recordsAffected = tableUser.update(Convert.userToContentValues(user), DBTableUser._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, recordsAffected);

        db.close();
    }

    @Test
    public void canDeleteUser(){
        Context appContext = getTargetContext();

        DatabaseOpenHelper openHelper = new DatabaseOpenHelper(appContext);
        SQLiteDatabase db = openHelper.getWritableDatabase();

        DBTableUser tableUser = new DBTableUser(db);

        long id = insertUser(tableUser, "Hila", "F", "287346362", "d@gmail.com", "9349326438", "16/10/1997", "Lisboa", "Portugal", "yyyy-MM-dd HH:mm:ss.SSS");


        int recordsDeleted = tableUser.delete(DBTableUser._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, recordsDeleted);

        db.close();
    }
}