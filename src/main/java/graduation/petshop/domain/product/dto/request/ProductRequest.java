package graduation.petshop.domain.product.dto.request;

import graduation.petshop.domain.board.entity.TagType;
import graduation.petshop.domain.product.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ProductRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String body;
    @NotNull
    private Long price;
    private Boolean reservationStatus;
    private Boolean saleStatus;
    private TagType tagType;
    private MultipartFile file;

    public static Product of(ProductRequest request) {
        Product product = new Product();
        product.setTitle(request.getTitle());
        product.setBody(request.getBody());
        product.setPrice(request.getPrice());
        product.setReservationStatus(request.getReservationStatus());
        product.setSaleStatus(request.getSaleStatus());
        product.setTagType(request.getTagType());

        return product;
    }
}
