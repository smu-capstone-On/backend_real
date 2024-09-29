package graduation.petshop.domain.product.entity;

import lombok.Getter;

@Getter
public enum ProductSortType {
    LATEST("createDate"),
    LOW_PRICE("price");

    private final String sortBy;

    ProductSortType(String sortBy) {
        this.sortBy = sortBy;
    }
}
