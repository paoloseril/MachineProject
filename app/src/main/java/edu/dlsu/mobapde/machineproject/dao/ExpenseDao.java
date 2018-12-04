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

    // future expenses
    @Query("select * from Expense where past = 0 order by dateTime desc")
    List<Expense> getFutureExpenses();

    // expense history
    @Query("select * from Expense where past = 1 order by dateTime desc")
    List<Expense> getPastExpenses();

    @Query("select * from Expense order by name")
    List<Expense> getAllExpenses();

    @Query("select * from Expense where id = :id")
    Expense getExpense(int id);

    @Query("select * from Expense where dateTime == :dateTime")
    List<Expense> getAllExpensesHappeningNow(String dateTime);

}
