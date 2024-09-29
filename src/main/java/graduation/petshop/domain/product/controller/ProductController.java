package graduation.petshop.domain.product.controller;

import graduation.petshop.domain.board.entity.TagType;
import graduation.petshop.domain.product.dto.request.ProductRequest;
import graduation.petshop.domain.product.entity.ProductSortType;
import graduation.petshop.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> save(
            @RequestParam("title") String title,
            @RequestParam("body") String body,
            @RequestParam("price") Long price,
            @RequestParam(value = "reservationStatus", required = false) Boolean reservationStatus,
            @RequestParam(value = "saleStatus", required = false) Boolean saleStatus,
            @RequestParam(value = "tagType") TagType tagType,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        // ProductRequest 객체 생성
        ProductRequest request = new ProductRequest();
        request.setTitle(title);
        request.setBody(body);
        request.setPrice(price);
        request.setReservationStatus(reservationStatus);
        request.setSaleStatus(saleStatus);
        request.setTagType(tagType);
        request.setFile(file);
        return ResponseEntity.ok(productService.save(request));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<?> patch(@RequestParam("title") String title,
                                   @RequestParam("body") String body,
                                   @RequestParam("price") Long price,
                                   @RequestParam(value = "reservationStatus", required = false) Boolean reservationStatus,
                                   @RequestParam(value = "saleStatus", required = false) Boolean saleStatus,
                                   @RequestParam(value = "tagType") TagType tagType,
                                   @RequestParam(value = "file", required = false) MultipartFile file,
                                   @PathVariable(value = "productId") Long productId) {

        // ProductRequest 객체 생성
        ProductRequest request = new ProductRequest();
        request.setTitle(title);
        request.setBody(body);
        request.setPrice(price);
        request.setReservationStatus(reservationStatus);
        request.setSaleStatus(saleStatus);
        request.setTagType(tagType);
        request.setFile(file);

        log.info("request[{}]", request);

        return ResponseEntity.ok(productService.patch(productId, request));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getById(@PathVariable Long productId) {
        log.info("상품 상세조회[{}]", productId);
        return ResponseEntity.ok(productService.getById(productId));
    }


    @GetMapping
    public ResponseEntity<?> getBoardList(@RequestParam(required = false, value = "sort") ProductSortType sort,
                                          @RequestParam(required = false, value = "isReserved") Boolean isReserved) {

        if (sort == null && isReserved == null) {
            return ResponseEntity.ok(productService.getAll());
        } else if (sort == null && isReserved != null) {
            return ResponseEntity.ok(productService.getByReserveStatus(isReserved));
        } else if (sort != null && isReserved == null) {
            return ResponseEntity.ok(productService.getAllBySort(sort));
        } else if (sort != null && isReserved != null) {
            return ResponseEntity.ok(productService.getAllBySortAndReserveStatus(sort, isReserved));
        }
        return ResponseEntity.ok().build();
    }
}
