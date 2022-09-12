package br.com.bb.compra.model;

import java.util.List;

import lombok.Data;

@Data
public class PedidoRequestDto {
    private List<ItemDoPedido> itens;
}
