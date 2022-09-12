package br.com.bb.compra.resource;

import br.com.bb.compra.model.PedidoRequestDto;
import br.com.bb.compra.model.Produto;
import br.com.bb.compra.model.entity.PedidoEntity;
import br.com.bb.compra.service.ProdutoService;
import br.com.bb.compra.service.impl.PedidoServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

@Path("/pedido")
@RequiredArgsConstructor
@Slf4j
public class PedidoController {

    private final PedidoServiceImpl service;

    @GET
    public Response pedidos() {
        return Response.status(HttpStatus.SC_OK).entity(service.buscarTodos()).build();
    }

    @GET
    @Path("itens")
    public Response itens() {
        return Response.status(HttpStatus.SC_OK).entity(service.buscarTodosItens()).build();
    }

    @POST
    @RolesAllowed("CLIENTE")
    public Response novoPedido(
            @HeaderParam("Authorization") String token,
            @Valid PedidoRequestDto pedido) {
        try {
            return Response.ok(service.realizarPedido(pedido)).build();
        } catch (Exception e) {
            return Response.status(HttpStatus.SC_BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

}
