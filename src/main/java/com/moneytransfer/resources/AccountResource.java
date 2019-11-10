package com.moneytransfer.resources;

import com.moneytransfer.api.Account;
import com.moneytransfer.core.AccountDTO;
import com.moneytransfer.db.AccountDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/accounts")
public class AccountResource {

    private final AccountDAO accountDAO;

    public AccountResource(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @POST
    @Path("/create")
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(Account account) {
        return Response.ok(accountDAO.createAccount(account)).build();
    }

    @GET
    @Path("{id}")
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAccount(@PathParam("id") String id) {
        return Response.ok(accountDAO.findByAccountNumber(id)).build();
    }
}
