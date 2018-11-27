package edu.dlsu.mobapde.machineproject.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.dlsu.mobapde.machineproject.entity.Expense;

@Dao
public interface ExpenseDao {

    @Insert
    void addExpense(Expense expense);

    @Query("select * from Expense")
    List<Expense> getAllExpenses();

    @Update
    void updateExpense(Expense expense);

    @Delete
    void deleteExpense(Expense expense);

    @Query("select * from Expense where type = :type")
    List<Expense> getExpensesByType(String type);

    @Query("select * from Expense where regretLevel = :regretLevel")
    List<Expense> getExpensesByRegretLevel(int regretLevel);

    // List<Expense> filterExpensesByTime(long dateTimeMillis);


}
