package com.moneytransfer.resources;

import com.moneytransfer.api.Transaction;
import com.moneytransfer.api.TransactionService;
import io.dropwizard.hibernate.UnitOfWork;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/transactions")
public class TransactionResource {

    private final TransactionService transactionService;

    public TransactionResource(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @POST
    @Path("/transfer")
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response transfer(Transaction transaction) {
        return Response.ok(transactionService.transfer(transaction)).build();
    }
}
