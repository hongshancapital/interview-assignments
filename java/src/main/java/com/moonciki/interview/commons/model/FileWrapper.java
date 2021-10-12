package com.moonciki.interview.commons.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 内容为base64格式的文件
 */
@Data
@ApiModel("文件封装类")
public class FileWrapper {
    @ApiModelProperty("base64内容")
    private String base64;
    @ApiModelProperty("文件名称")
    private String fileName;
    @ApiModelProperty("二进制")
    private byte[] dataBytes;
    @ApiModelProperty("文本内容")
    private String content;

    public void setBase64(String base64) {
        this.base64 = base64;

        setDataByBase64();
    }

    /**
     * 解码base64
     * @return
     */
    public void setDataByBase64(){
        if(dataBytes == null){
            if(StringUtils.isNotEmpty(base64)){
                dataBytes = Base64.decodeBase64(base64);
            }
        }
    }

    public FileWrapper() {
    }

    /**
     * 转换 multipartFile
     * @param multi
     * @throws Exception
     */
    public FileWrapper(MultipartFile multi) throws Exception{
        String fileName = multi.getOriginalFilename();

        byte[] fileData = multi.getBytes();

        this.fileName = fileName;
        this.dataBytes = fileData;
    }

    /**
     * 获取数据大小
     * @return
     */
    public long getSize(){
        long size = 0l;

        if(dataBytes != null){
            size = dataBytes.length;
        }
        return size;
    }

    /**
     * 清理数据
     */
    public void clearData(){

        base64 = null;
        dataBytes = null;
        content = null;

    }

}
