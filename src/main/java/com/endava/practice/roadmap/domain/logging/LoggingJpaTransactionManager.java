package com.endava.practice.roadmap.domain.logging;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

import static java.lang.System.out;

public class LoggingJpaTransactionManager extends JpaTransactionManager {

    private static final int LENGTH = 72;
    private static final String DELIMITER = "*".repeat(LENGTH);
    private static final String TRANSACTION_CREATE = messenger("Creating transaction");
    private static final String TRANSACTION_COMMIT = messenger("Commit");
    private static final String TRANSACTION_ROLLBACK = messenger("Rollback");


    @Override
    protected void doBegin(final Object transaction, final TransactionDefinition definition) {
        super.doBegin(transaction, definition);
        printTransactionEvent(TRANSACTION_CREATE);
    }

    @Override
    protected void doCommit(final DefaultTransactionStatus status) {
        super.doCommit(status);
        printTransactionEvent(TRANSACTION_COMMIT);

    }

    @Override
    protected void doRollback(final DefaultTransactionStatus status) {
        super.doRollback(status);
        printTransactionEvent(TRANSACTION_ROLLBACK);
    }

    private static String messenger (String message) {
        var repeater = LENGTH / 2  - (message.length() + 2) / 2;
        return "*".repeat(repeater) + " " + message.toUpperCase() + " " + "*".repeat(repeater);
    }

    private static void printTransactionEvent (String event) {
        out.println(DELIMITER);
        out.println(event);
        out.println(DELIMITER);
        out.println();
    }
}
