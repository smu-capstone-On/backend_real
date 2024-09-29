package graduation.petshop.domain.file.controller;

import graduation.petshop.domain.file.entity.FileInfo;
import graduation.petshop.domain.file.service.FileService;
import graduation.petshop.domain.file.service.ImageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<FileInfo>> saveFile(
            @RequestParam(value = "imageType", defaultValue = "BOARD", required = false) final ImageType imageType,
            @RequestParam(value = "files") List<MultipartFile> files) {

        log.info("files size[{}]", files.size());
        return ResponseEntity.ok(fileService.saveFile(files, imageType));
    }

    @GetMapping("/v1")
    public ResponseEntity<?> getFileV1(@RequestParam(value = "fileId") final Long fileId) {
        log.info("fileId[{}]", fileId);
        return ResponseEntity.ok(fileService.getFileV1(fileId));
    }

    @GetMapping("/v2")
    public ResponseEntity<?> getFileV2(@RequestParam(value = "fileId") final Long fileId) {
        log.info("fileId[{}]", fileId);
        return ResponseEntity.ok(fileService.getFileV2(fileId));
    }
}
