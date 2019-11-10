package com.moneytransfer;

import com.moneytransfer.api.TransactionService;
import com.moneytransfer.core.AccountDTO;
import com.moneytransfer.db.AccountDAO;
import com.moneytransfer.resources.AccountResource;
import com.moneytransfer.resources.TransactionResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MoneyTransferApplication extends Application<MoneyTransferConfiguration> {

    public static void main(final String[] args) throws Exception {
        new MoneyTransferApplication().run(args);
    }

    private final HibernateBundle<MoneyTransferConfiguration> hibernateBundle =
            new HibernateBundle<MoneyTransferConfiguration>(AccountDTO.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(MoneyTransferConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public String getName() {
        return "moneytransfer";
    }

    @Override
    public void initialize(final Bootstrap<MoneyTransferConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(final MoneyTransferConfiguration configuration,
                    final Environment environment) {

        final AccountDAO dao = new AccountDAO(hibernateBundle.getSessionFactory());
        final TransactionService transactionService = new TransactionService(dao);

        environment.jersey().register(new AccountResource(dao));
        environment.jersey().register(new TransactionResource(transactionService));
        environment.jersey().register(new JsonProcessingExceptionMapper(true));
    }

}
