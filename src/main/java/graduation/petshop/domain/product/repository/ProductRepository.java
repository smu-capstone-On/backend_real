package graduation.petshop.domain.product.repository;

import graduation.petshop.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByReservationStatus(Boolean isReserved);

}
