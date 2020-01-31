package minsu.restapi.persistence.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileUploadResponse {

    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    public FileUploadResponse(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }
}
