package io.back3nd.backend.domain.app;

import io.back3nd.backend.domain.dao.ImageFilesRepository;
import io.back3nd.backend.domain.entity.ImageFiles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ImageFileService {

    private final ImageFilesRepository imageFilesRepository;

    @Value("${custom.file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        return fileDir+fileName;
    }

    public ImageFiles storeFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName=createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        log.info("storeFileName = "+storeFileName);
        ImageFiles imageFile = ImageFiles.builder()
                .uploadFileName(originalFilename)
                .storeFileName(storeFileName)
                .build();

        return imageFilesRepository.save(imageFile);
    }

    private String createStoreFileName(String originalFilename) {
        String ext=extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid+"."+ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos+1);
    }
}
