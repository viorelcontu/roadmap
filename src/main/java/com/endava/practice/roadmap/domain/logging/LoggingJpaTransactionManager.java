package com.endava.practice.roadmap.domain.logging;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class LoggingJpaTransactionManager extends JpaTransactionManager {

    private static final String DELIMITER = "************************************************************************";

    @Override
    protected void doBegin(final Object transaction, final TransactionDefinition definition) {
        super.doBegin(transaction, definition);
        System.out.println(DELIMITER);
        System.out.println("************************** CREATE TRANSACTION **************************");
        System.out.println(DELIMITER);
        System.out.println();
    }

    @Override
    protected void doCommit(final DefaultTransactionStatus status) {
        super.doCommit(status);
        System.out.println(DELIMITER);
        System.out.println("******************************** COMMIT ********************************");
        System.out.println(DELIMITER);
        System.out.println();
    }

    @Override
    protected void doRollback(final DefaultTransactionStatus status) {
        super.doRollback(status);
        System.out.println(DELIMITER);
        System.out.println("******************************* ROLLBACK *******************************");
        System.out.println(DELIMITER);
        System.out.println();
    }
}
