package com.rcalderon;

import com.rcalderon.cli.ExpenseTrackerCli;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        System.exit(
            new CommandLine(
                new ExpenseTrackerCli()
            ).execute(args)
        );
    }
}