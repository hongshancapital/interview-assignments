package xiejin.java.interview.result;

import lombok.extern.slf4j.Slf4j;
import xiejin.java.interview.enums.ResultCode;
import xiejin.java.interview.exception.GlobalException;


/**
 * @author xiaobao
 * @Description 结果集包装类
 */
@Slf4j
public class ResultWrapper {

    /**
     * 结果集包装类处理方法
     * @param action
     * @param <T>
     * @return
     */
    public static <T> ResultJson<T> server(Action<T> action) {
        long timeMillis = System.currentTimeMillis();
        try {
            T t = action.invoke();
            return ResultJson.success(t);
        } catch (GlobalException e) {
            log.error("ServiceException-{}-{}-{}-{}", e.getErrCode(), e.getMessage(), e.getData(),
                    System.currentTimeMillis() - timeMillis);
            e.printStackTrace();
            ResultJson<T> resultJson = new ResultJson<>();
            resultJson.setCode(e.getErrCode());
            resultJson.setMessage(e.getMessage());
            return resultJson;
        } catch (Exception e) {
            log.error("Exception-{}-{}", e, System.currentTimeMillis() - timeMillis);
            e.printStackTrace();
            return ResultJson.error(ResultCode.SERVER_ERROR);
        } catch (Throwable e) {
            log.error("Throwable-{}-{}", e, System.currentTimeMillis() - timeMillis);
            e.printStackTrace();
            return ResultJson.error(ResultCode.SERVER_ERROR);
        }
    }

    /**
     * 自定义Action处理类
     * @param <T>
     */
    @FunctionalInterface
    public interface Action<T> {
        T invoke();

    }
}