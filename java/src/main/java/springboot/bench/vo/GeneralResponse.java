package springboot.bench.vo;

import springboot.bench.consts.Constants;

public class GeneralResponse {

    private int retCode = Constants.REVOKE_OK;
    private String retInfo;

    public GeneralResponse(String info) {
        this.retInfo = info;
    }
    
    public GeneralResponse(int code, String info) {
        this.retCode = code;
        this.retInfo = info;
    }

    public int getRetCode() {
        return retCode;
    }

    public String getRetInfo() {
        return retInfo;
    }

}
