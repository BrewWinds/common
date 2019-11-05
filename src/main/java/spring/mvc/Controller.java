package spring.mvc;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Date: 2019/9/18 15:37
 * @Description:
 */
@RequestMapping("xxx")
@org.springframework.stereotype.Controller
public class Controller {


    class Response{}
    class DTO{}
    @GetMapping("inputTableData")
    @ResponseBody
    public Response getInputTables(@RequestParam("modelId") Long modelId){
//
//        ModelDo modelDo = modelService.getAndValidateModel(modelId);
//        List<InputOutputTableDo> tables = modelInputOutputTableService.listTables(null, InputOutputTableDo.TableInoutTypeEnum.IN.code(), null, modelDo.getModelType());
//
//        return ResponseHelper.buildOk(tables);
        return null;
    }
    @PostMapping("addOrUpdateTableData")
    @ResponseBody
    public Response addOrUpdateTableData(@RequestBody DTO req){
//        req.setUpdateBy(UserContext.getCurrentUserName());
//        TableDataEditResultDto dto = modelInputOutputTableService.addOrUpdateInputData(req);
//        return ResponseHelper.buildOk(dto);
        return null;
    }


    //download
    @GetMapping("table")
    @ResponseBody
    public ResponseEntity<Byte> download( @RequestParam("fileName") String fileName){

       /* String fullPath = exportService.getDownloadPath(fileName);
        File f = new File(fullPath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData(
                "attachment", new String(fileName.getBytes(UTF_8), ISO_8859_1)
        );

        if(!f.exists()){
            Assert.isTrue(f.exists(), "文件不存在");
        }

        try{
            return new ResponseEntity(FileUtils.readFileToByteArray(f), headers,  HttpStatus.CREATED);
        } catch (IOException e) {
            logger.error("--download error, fileName="+fileName, e);
            Assert.isTrue(e==null, "下载出错" );
        }
        return null;*/
       return null;
    }


    @RequestMapping(value="/file",method= RequestMethod.POST)
    @ResponseBody
    public Response getFile(DTO downloadDTO, HttpServletRequest req, HttpServletResponse resp){
        /*try{
            String fileName = downloadDTO.getFileName();
            if(StringUtils.isBlank(fileName)){
                return Result.failure(ResultCode.ILLEGAL_ARGS_ERR, "文件名不能为空");
            }

            String fullPath = sharePath.endsWith(File.separator) ? sharePath+fileName : sharePath+File.separator+fileName;
            File file = new File(fullPath);
            if(!file.exists()){
                return Result.failure(ResultCode.ILLEGAL_ARGS_ERR, "文件不存在");
            }

            try(InputStream is = new FileInputStream(new File(fileName)); OutputStream os = resp.getOutputStream()){
                resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                resp.addHeader("Content-Type", "application/json;charset=UTF-8");
                IOUtils.copy(is, os);
            }
            return null;
        }catch(Exception e){
            logger.error("get file error", e);
            return Result.failure(ResultCode.ILLEGAL_ARGS_ERR, "操作异常");
        }*/
        return null;
    }


    // Upload

    @PostMapping(value = "upload")
    @ResponseBody
    public Response upload( @RequestParam(name="tableCode")String  tableCode, @RequestParam(name="file") MultipartFile file){

//        Assert.isTrue(userService.isAdmin(), MsgConstants.Admin.NOT_ADMIN_FORBIDS);
//
//        UploadDto dto = new UploadDto();
//        dto.setFile(file);
//        dto.setTableCode(tableCode);
//        dto.setUpdater(UserContext.getCurrentUserName());
//
//        EventInfo info = globalConfigureService.uploadConfigure(dto);
//        return ResponseHelper.buildOk(info);
        return null;
    }


    public File saveUploadFile(MultipartFile file, String tableCode){

//        String newFileName = tableCode+ "_" +FastDateFormat.getInstance("yyyyMMddHHmmss").format(new Date()) +"_"+ UUID.randomUUID().getLeastSignificantBits() + "." + Files.getFileExtension(file.getOriginalFilename());
//        File newFile = new File(uploadPath, newFileName);
//
//        File dir = new File(uploadPath);
//        if(!dir.exists()){
//            dir.mkdir();
//        }
//        file.transferTo(newFile);
//        return newFile;
        return null;
    }
}
