package com.rcalderon.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rcalderon.models.Expense;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileHandler {
    private static FileHandler instance;
    private Path filePath = Paths.get("src\\data\\expenses.json");
    private final ObjectMapper mapper;

    private FileHandler() {
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    public static FileHandler getInstance() {
        if (instance == null) {
            instance = new FileHandler();
        }
        return instance;
    }

    public List<Expense> readExpenses() throws IOException {
        return this.mapper.readValue(filePath.toFile(), new TypeReference<List<Expense>>() {});
    }
    public void addExpense(List<Expense> expenses) throws IOException {
        this.mapper.writeValue(filePath.toFile(), expenses);
    }

}

