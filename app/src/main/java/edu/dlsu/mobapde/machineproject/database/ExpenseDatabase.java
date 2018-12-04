package edu.dlsu.mobapde.machineproject.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import edu.dlsu.mobapde.machineproject.dao.ExpenseDao;
import edu.dlsu.mobapde.machineproject.entity.Expense;

@Database(entities = {Expense.class}, version = 8, exportSchema = false)
public abstract class ExpenseDatabase extends RoomDatabase {

    private static ExpenseDatabase INSTANCE;

    public abstract ExpenseDao dao();

    public static ExpenseDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ExpenseDatabase.class, "expense_db")
                    .fallbackToDestructiveMigration()
                            // allow queries on the main thread.
                            // Don't do this on a real app!
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }
}
