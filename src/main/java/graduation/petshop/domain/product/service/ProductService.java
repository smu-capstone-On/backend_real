package graduation.petshop.domain.product.service;

import graduation.petshop.domain.file.entity.FileInfo;
import graduation.petshop.domain.file.service.FileService;
import graduation.petshop.domain.file.service.ImageType;
import graduation.petshop.domain.product.dto.request.ProductRequest;
import graduation.petshop.domain.product.entity.Product;
import graduation.petshop.domain.product.entity.ProductSortType;
import graduation.petshop.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final FileService fileService;
    private final ProductRepository productRepository;

    @Transactional
    public Product save(ProductRequest request) {

        final List<FileInfo> fileInfos = fileService.saveFile(List.of(request.getFile()), ImageType.PRODUCT);

        final Product product = ProductRequest.of(request);
        product.setFileInfo(fileInfos.get(0));

        return productRepository.save(product);
    }

    @Transactional
    public Product patch(Long productId, ProductRequest request) {
        final Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글"));
        product.update(request.getTitle(), request.getBody(), request.getPrice(), request.getReservationStatus(), request.getSaleStatus(), request.getTagType());

        return productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(Long productId) {return productRepository.findById(productId).get();}

    public List<Product> getByReserveStatus(Boolean isReserved) {
        return productRepository.findAllByReservationStatus(isReserved);
    }

    public List<Product> getAllBySort(ProductSortType sort) {
        List<Product> products = productRepository.findAll();

        return sortProducts(sort, products);
    }

    public List<Product> getAllBySortAndReserveStatus(ProductSortType sort, Boolean isReserved) {
        List<Product> byReserveStatus = getByReserveStatus(isReserved);

        return sortProducts(sort, byReserveStatus);
    }

    private List<Product> sortProducts(ProductSortType sort, List<Product> products) {
        switch (sort) {
            case LOW_PRICE -> products.sort((product, t1) -> (int) (product.getPrice() - t1.getPrice()));
            case LATEST -> products.sort((product, t1) -> (int) (product.getId() - t1.getId()));
        }
        return products;
    }
}
