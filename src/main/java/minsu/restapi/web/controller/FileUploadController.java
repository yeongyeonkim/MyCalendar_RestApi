//package minsu.restapi.web.controller;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import javax.servlet.http.HttpServletRequest;
//
//import minsu.restapi.persistence.model.User;
//import minsu.restapi.persistence.service.FileUploadDownloadService;
//import minsu.restapi.persistence.model.FileUploadResponse;
//import minsu.restapi.persistence.service.UserService;
//import net.bytebuddy.asm.Advice;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//@RestController
//public class FileUploadController {
//    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
//
//    @Autowired
//    private FileUploadDownloadService service;
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/")
//    public String controllerMain() {
//        return "Hello~ File Upload Test.";
//    }
//
//    //수정도 동시에 되게끔.
//    @PostMapping("/uploadFile")
//    public FileUploadResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("email") String mail ) {
////        if(file.getOriginalFilename() != null){
////
////        }
//        User user = userService.findByEmail(mail);
//
//        String fileName = service.storeFile(file);
////파일이 이미 존재하는데 수정하려한다 -> 1. 같은 이미지를 넣으려하면 그냥 return / 2. 다른 이미지 넣으려하면 원래꺼 삭제하고 새로운거 넣고 return
//
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(fileName)
//                .toUriString();
//        System.out.println(fileName + " : " + fileDownloadUri + " : 확인용.");
//        user.setImg(fileDownloadUri);
//        userService.save(user);
//        return new FileUploadResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
//    }
//
////    @PostMapping("/uploadMultipleFiles")
////    public List<FileUploadResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
////        return Arrays.asList(files)
////                .stream()
////                .map(file -> uploadFile(file))
////                .collect(Collectors.toList());
////    }
//
//    //업로드 이미지 삭제 되려나?
//    @DeleteMapping("/deleteFile")
//    public FileUploadResponse deleteFile(@RequestParam("file") MultipartFile file){
//        return new FileUploadResponse(null, null, null, 0);
//    }
//
//    @GetMapping("/downloadFile/{fileName:.+}")
//    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
//        // Load file as Resource
//        Resource resource = service.loadFileAsResource(fileName);
//
//        // Try to determine file's content type
//        String contentType = null;
//        try {
//            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//        } catch (IOException ex) {
//            logger.info("Could not determine file type.");
//        }
//
//        // Fallback to the default content type if type could not be determined
//        if (contentType == null) {
//            contentType = "application/octet-stream";
//        }
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
//    }
//}
