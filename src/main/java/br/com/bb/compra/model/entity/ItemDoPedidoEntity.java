package br.com.bb.compra.model.entity;

import br.com.bb.compra.model.Produto;
import br.com.bb.compra.model.enums.PerfilEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_itempedido")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDoPedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long quantidade;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private ProdutoEntity produto;
   
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private PedidoEntity pedido;
}