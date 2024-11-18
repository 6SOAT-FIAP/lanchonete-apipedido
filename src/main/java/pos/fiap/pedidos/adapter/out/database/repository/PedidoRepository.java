package pos.fiap.pedidos.adapter.out.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pos.fiap.pedidos.adapter.out.database.entities.PedidoEntity;

public interface PedidoRepository extends JpaRepository<PedidoEntity, String> {

}