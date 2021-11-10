package homework.shorturl.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@ApiModel(description= "返回响应数据")
public class ResponseDTO<T> implements Serializable {
    @ApiModelProperty(value = "错误码")
    private String code;
    @ApiModelProperty(value = "提示信息")
    private String msg;
    @ApiModelProperty(value = "数据")
    private T data;

    public static <T> ResponseDTO<T> success(T data) {
        ResponseDTO<T> responseDTO = new ResponseDTO<>();
        responseDTO.data = data;
        responseDTO.code = String.valueOf(HttpStatus.OK.value());
        return responseDTO;
    }

    public static <T> ResponseDTO<T> error(String msg) {
        ResponseDTO<T> responseDTO = new ResponseDTO<>();
        responseDTO.code = String.valueOf(HttpStatus.BAD_REQUEST.value());
        responseDTO.msg = msg;
        return responseDTO;
    }

    public static <T> ResponseDTO<T> parmaError() {
        return parmaError(null);
    }

    public static <T> ResponseDTO<T> parmaError(String msg) {
        ResponseDTO<T> responseDTO = new ResponseDTO<>();
        responseDTO.code = String.valueOf(HttpStatus.BAD_REQUEST.value());
        responseDTO.msg = msg;
        return responseDTO;
    }
}
