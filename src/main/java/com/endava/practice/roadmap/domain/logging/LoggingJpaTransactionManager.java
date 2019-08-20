package com.endava.practice.roadmap.domain.logging;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class LoggingJpaTransactionManager extends JpaTransactionManager {

    @Override
    protected void doBegin(final Object transaction, final TransactionDefinition definition) throws TransactionException {
        super.doBegin(transaction, definition);
        System.out.println("************************************************************************");
        System.out.println("************************** CREATE TRANSACTION **************************");
        System.out.println("************************************************************************");
        System.out.println();
    }

    @Override
    protected void doCommit(final DefaultTransactionStatus status) throws TransactionException {
        super.doCommit(status);
        System.out.println("************************************************************************");
        System.out.println("******************************** COMMIT ********************************");
        System.out.println("************************************************************************");
        System.out.println();
    }

    @Override
    protected void doRollback(final DefaultTransactionStatus status) throws TransactionException {
        super.doRollback(status);
        System.out.println("************************************************************************");
        System.out.println("******************************* ROLLBACK *******************************");
        System.out.println("************************************************************************");
        System.out.println();
    }
}
