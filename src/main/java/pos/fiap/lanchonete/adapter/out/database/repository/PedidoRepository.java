package pos.fiap.lanchonete.adapter.out.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pos.fiap.lanchonete.adapter.out.database.entities.PedidoEntity;

public interface PedidoRepository extends JpaRepository<PedidoEntity, String> {

}