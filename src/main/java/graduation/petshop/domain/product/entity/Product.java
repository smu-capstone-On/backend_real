package graduation.petshop.domain.product.entity;

import graduation.petshop.common.entity.Base;
import graduation.petshop.domain.board.entity.TagType;
import graduation.petshop.domain.file.entity.FileInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String body;
    private Long price;
    private Boolean reservationStatus = false;
    private Boolean saleStatus = false;
    private TagType tagType;

    @OneToOne
    @JoinColumn(name = "file_info_id")
    private FileInfo fileInfo;

    public void update(String title, String body, Long price, Boolean reservationStatus, Boolean saleStatus, TagType tagType) {
        this.title = title;
        this.body = body;
        this.price = price;
        this.reservationStatus = reservationStatus;
        this.saleStatus = saleStatus;
        this.tagType = tagType;
    }
}
