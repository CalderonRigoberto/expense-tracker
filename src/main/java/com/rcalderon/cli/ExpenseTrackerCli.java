package com.rcalderon.cli;


import com.rcalderon.models.Expense;
import com.rcalderon.models.ExpenseCategory;
import com.rcalderon.utils.FileHandler;
import picocli.CommandLine;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;



@CommandLine.Command(name = "expense-tracker")
public class ExpenseTrackerCli {
    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Command(name = "add", description = "Add an expense made it to tracker")
    void add(
            @CommandLine.Option(names = "--description", paramLabel = "<description>", description = "Add description to your purchase") String description,
            @CommandLine.Option(names = "--amount", paramLabel = "<amount>", description = "Add the amount of your purchase") BigDecimal amount,
            @CommandLine.Option(names = "--category", paramLabel = "<category>", description = "Add the category of your purchase") String category

    ) {

        try {
           var expenses = new ArrayList<>(FileHandler.getInstance().readExpenses());

           var expense = new Expense.Builder()
                    .setDescription(description)
                    .setAmount(amount)
                    .setCategory(ExpenseCategory.valueOf(category))
                    .setDate(LocalDateTime.now())
                    .build();

           expenses.add(expense);

            FileHandler.getInstance().addExpense(expenses);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @CommandLine.Command(name = "list", description = "Get all details in list from the current month and year of your expenses")
    void list() {
        try {
            var expenses = FileHandler.getInstance().readExpenses();
            if(expenses.isEmpty()) {
                System.out.println("There is not expenses added to your document.");
                return;
            }

            System.out.println("uuid Date Description Amount Category");
            expenses.forEach(e -> {
                System.out.printf("%s %s %s %s %s", e.getUuid(), e.getAddDate(), e.getDescription(), e.getAmount(), e.getCategory());
                System.out.println();
            });
        } catch (IOException e) {
            System.out.println("An error has occured trying to read the file that stores your expenses");
        }
    }

    @CommandLine.Command(name = "delete", description = "Delete your expense by uuid")
    void delete(
            @CommandLine.Option(names = "--uuid", paramLabel = "<uuid>", description = "Add the uuid to delete an specific added expense") String uuid
    ) {
        try {
           var expenses = FileHandler.getInstance().readExpenses();
            for (Iterator<Expense> iterator =  expenses.iterator(); iterator.hasNext();) {
                if(!iterator.next().getUuid().equals(uuid)) continue;
                iterator.remove();
                System.out.printf("Expense with uuid %s has been deleted", uuid);
            }

            FileHandler.getInstance().addExpense(expenses);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @CommandLine.Command(name = "summary")
    void summary(
            @CommandLine.Option(
                    names = "--month",
                    paramLabel = "<number_of_month>",
                    required = false
            ) Integer month
    ) {
        try {
            var expenses = FileHandler.getInstance().readExpenses();

            var total = expenses.stream()
                    .map(e -> e.getAmount())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            System.out.printf("Total expenses: $%s", total);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
