package graduation.petshop.domain.file.service;

import graduation.petshop.domain.file.entity.FileInfo;
import graduation.petshop.domain.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {

    @Value("${path.image}")
    private String ROOT_PATH;
    private final FileRepository fileRepository;

    public UrlResource getFileV1(Long fileId) {
        final FileInfo fileInfo = getFileInfo(fileId);
        final String targetFile = FileUtils.getFilePullPath(fileInfo, ROOT_PATH);

        //return fileInfo;

        try {
            // 서버의 파일 위치를 리턴해주는 소스
            //return new UrlResource("file:" + targetFile);
            return new UrlResource("file:" + "/Users/han-yong-u/Desktop/1111.png");

        } catch (MalformedURLException e) {
            log.error("file not found", e);
            throw new IllegalArgumentException("file not found");
        }
    }

    public byte[] getFileV2(Long fileId) {
        final FileInfo fileInfo = getFileInfo(fileId);
        final String targetFile = FileUtils.getFilePullPath(fileInfo, ROOT_PATH);

        try {
            return FileCopyUtils.copyToByteArray(new File(targetFile));
        } catch (IOException e) {
            log.error("file not found", e);
            throw new IllegalArgumentException("file not found");
        }
    }

    @Transactional
    public List<FileInfo> saveFile(List<MultipartFile> files, ImageType imageType) {

        if (files.isEmpty()) {
            return List.of();
        }

        final String directoryName = switch (imageType) {
            case BOARD -> "/board";
            case USER_PROFILE -> "/profile";
            case PRODUCT -> "/product";
        };

        final List<FileInfo> result = new ArrayList<>();

        for (MultipartFile multipartFile : files) {
            final String randomFileName = UUID.randomUUID().toString();
            final String fileExt = FileUtils.extractExt(Objects.requireNonNull(multipartFile.getOriginalFilename()));

            final FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(randomFileName);
            fileInfo.setFileOriName(multipartFile.getOriginalFilename());
            fileInfo.setFileUrl(directoryName);
            fileInfo.setFileExt(fileExt);
            fileInfo.setFileSize(multipartFile.getSize());
            log.info("fileInfo[{}]", fileInfo);

            // Image Save To Server
            saveImage(multipartFile, directoryName, randomFileName, fileExt);

            // DB save
            result.add(fileRepository.save(fileInfo));
        }

        return result;
    }

    private FileInfo getFileInfo(Long fileId) {
        return fileRepository.findById(fileId).orElseThrow(() -> new IllegalArgumentException("file not found"));
    }

    private void saveImage(MultipartFile multipartFile, String directoryName, String fileName, String ext) {
        final String path = ROOT_PATH + directoryName;
        createImageDirectory(path);

        try {
            multipartFile.transferTo(new File(path + "/" + fileName + "." + ext));
        } catch (IOException e) {
            log.error("file save fail", e);
        }
    }

    private void createImageDirectory(String path) {
        final File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉터리 생성
        }
    }
}