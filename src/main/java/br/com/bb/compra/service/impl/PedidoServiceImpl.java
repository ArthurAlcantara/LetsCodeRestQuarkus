package br.com.bb.compra.service.impl;

import br.com.bb.compra.model.Cliente;
import br.com.bb.compra.model.ItemDoPedido;
import br.com.bb.compra.model.PedidoRequestDto;
import br.com.bb.compra.model.PedidoResponseDto;
import br.com.bb.compra.model.entity.ClienteEntity;
import br.com.bb.compra.model.entity.PedidoEntity;
import br.com.bb.compra.model.entity.ProdutoEntity;
import br.com.bb.compra.repository.ClienteRepository;
import br.com.bb.compra.repository.ItemDoPedidoRepository;
import br.com.bb.compra.repository.PedidoRepository;
import br.com.bb.compra.repository.ProdutoRepository;
import br.com.bb.compra.model.entity.ItemDoPedidoEntity;
import br.com.bb.compra.service.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;

import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository repo;
    private final ProdutoRepository prodRepo;
    private final ItemDoPedidoRepository itemPedRepo;
    private final ClienteRepository cliRepo;

    final JsonWebToken accessToken;

    @Transactional
    public List<PedidoEntity> buscarTodos(){
        return repo.listAll();
    }

    @Transactional
    public List<String> buscarTodosItens(){
        return itemPedRepo.listAll().stream().map(e-> e.getProduto().getNome()).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public PedidoResponseDto realizarPedido(PedidoRequestDto pedido) throws Exception {

        final String email = accessToken.getSubject();

        ClienteEntity cli = cliRepo.findByEmail(email);

        try {
                PedidoEntity novoPedido = new PedidoEntity();
                List<ItemDoPedidoEntity> itens = new ArrayList<ItemDoPedidoEntity>();
                pedido.getItens().forEach(p->{
                    ItemDoPedidoEntity itemPedido = new ItemDoPedidoEntity();
                    ProdutoEntity prod = prodRepo.findById(p.getProdutoId());
                    itemPedido.setProduto(prod);
                    itemPedido.setQuantidade(p.quantidade);
                    itens.add(itemPedido);
                });
                //itens.forEach(itemPedRepo::persist);
                novoPedido.setCliente(cli);
                novoPedido.setItens(itens);
                repo.persist(novoPedido);
                itens.forEach(i-> {
                    i.setPedido(novoPedido);
                    itemPedRepo.persist(i);
                });
                log.info("O usuario {} iniciou pedido {}", email, pedido);
                return new PedidoResponseDto(novoPedido.getId());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
