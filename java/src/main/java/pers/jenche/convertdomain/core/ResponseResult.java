package pers.jenche.convertdomain.core;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import pers.jenche.convertdomain.dto.ResponseResultDTO;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @author jenche E-mail:jenchecn@outlook.com
 * @project convertdomain
 * @date 2021/11/15 12:24
 * @description 输出结果数据的工具类
 */
@Slf4j
public class ResponseResult<T> {
    private ResponseResultDTO resultDTO = new ResponseResultDTO();

    public ResponseResult() {
    }

    public ResponseResult(Object data) {
        this.resultDTO.setData(data);
    }

    public ResponseResult(ResponseResultDTO responseResultDTO) {
        this.resultDTO = responseResultDTO;
    }

    public ResponseResult(ExceptionMessage exceptionMessage) {
        this.resultDTO.setCode(exceptionMessage.getCode());
        this.resultDTO.setMessage(exceptionMessage.getMsg());
    }

    public ResponseResult(BindingResult bindingResult, CallBack callBack) throws SystemException {
        if (bindingResult.hasErrors()) {
            String defaultMessage = Objects.requireNonNull(bindingResult.getFieldError()).getField().concat(" Field ").concat("is Blank.");
            log.debug("<{}> detail:{}", ExceptionMessage.C_50_PARAMS_EXCPTION, defaultMessage);
            throw new SystemException(ExceptionMessage.C_50_PARAMS_EXCPTION, defaultMessage);
        }
        this.resultDTO.setData(callBack.execute());
    }

    /**
     * 将结果转成{@link String}
     *
     * @return 转换成string类型的结果，通过JSON转换器
     * @throws SystemException 自定义的系统异常
     */
    public String toJson() throws SystemException {
        validData();
        return JSON.toJSONString(this.resultDTO);
    }

    /**
     * 提交DTO数据
     *
     * @return 提交DTO数据传输实体
     * @throws SystemException 自定义的系统异常
     */
    public ResponseResultDTO toDTO() throws SystemException {
        validData();
        return this.resultDTO;
    }

    public ResponseResult<T> succeed() {
        this.resultDTO.setCode(0);
        this.resultDTO.setMessage(null);
        this.resultDTO.setData(true);
        return this;
    }

    /**
     * 默认转成DTO
     *
     * @return 使用 {@link ResponseResultDTO}
     * @throws SystemException 自定义的系统异常
     */
    public ResponseResultDTO send() throws SystemException {
        return toDTO();
    }

    /**
     * 检验检查数据，根据数据类型检查并抛出异常
     *
     * @throws SystemException 自定义的系统异常
     */
    private void validData() throws SystemException {
        if (resultDTO.getCode() > 0) {
            return;
        }

        Object data = this.resultDTO.getData();
        if (data == null) {
            throw new SystemException(ExceptionMessage.C_50_DATA_IS_EMPTY);
        }

        //如果是List
        if (data instanceof List) {
            List<?> _ldata = (List<?>) data;
            if (_ldata.size() < 1) {
                throw new SystemException(ExceptionMessage.C_50_DATA_IS_EMPTY);
            }
        }

        //如果是Map
        if (data instanceof Map) {
            Map<?, ?> _mdata = (Map<?, ?>) data;
            if (_mdata.size() < 1) {
                throw new SystemException(ExceptionMessage.C_50_DATA_IS_EMPTY);
            }
        }
    }
}
