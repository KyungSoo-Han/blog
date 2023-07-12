package kr.hankyungsoo.blog.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileService {
    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName){
        return fileDir + fileName;
    }

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty())
            return null;

        String orgFileName = multipartFile.getOriginalFilename();
        String srvFileName = createSrvFileName(orgFileName);

        multipartFile.transferTo(new File(getFullPath(srvFileName)));
        return srvFileName;
    }

    public List<String> uploadFiles(List<MultipartFile> multipartFiles) throws IOException{
        List<String> srvFileList = new ArrayList<>();

        for (MultipartFile file : multipartFiles) {
            if(!file.isEmpty()){
                srvFileList.add(uploadFile(file));
            }
        }

        return srvFileList;
    }

    private String createSrvFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

}
