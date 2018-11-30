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

    @Update
    void updateExpense(Expense expense);

    @Delete
    void deleteExpense(Expense expense);

    @Query("select * from Expense where name like :name")
    List<Expense> getExpensesBy(String name);

    @Query("select * from Expense where type = :type")
    List<Expense> getExpensesByType(String type);

    @Query("select * from Expense where regretLevel = :regretLevel")
    List<Expense> getExpensesByRegretLevel(int regretLevel);

    //@Query("select * from Expense where dateTimeMillis ")
    //List<Expense> filterExpensesDuring(long dateTimeMillis);

    // future expenses
    @Query("select * from Expense where dateTimeMillis > :currentMillis order by dateTimeMillis desc")
    List<Expense> getFutureExpenses(long currentMillis);

    // expense history
    @Query("select * from Expense where dateTimeMillis <= :currentMillis order by dateTimeMillis desc")
    List<Expense> getPastExpenses(long currentMillis);
}
