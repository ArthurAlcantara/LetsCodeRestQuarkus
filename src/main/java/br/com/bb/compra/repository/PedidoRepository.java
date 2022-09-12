package br.com.bb.compra.repository;

import br.com.bb.compra.model.Produto;
import br.com.bb.compra.model.entity.PedidoEntity;
import br.com.bb.compra.model.entity.ProdutoEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepositoryBase<PedidoEntity, Long> {

}
