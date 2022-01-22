package at.fhv.hotelmanagement.bdd.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


@Component
public class ScenarioTXBoundary {
    @Autowired
    PlatformTransactionManager txManager;

    private TransactionStatus tx;

    protected void beginTX(){
        this.tx = this.txManager.getTransaction(new DefaultTransactionDefinition());
    }

    protected void rollbackTX(){
        this.txManager.rollback(this.tx);
    }
}
