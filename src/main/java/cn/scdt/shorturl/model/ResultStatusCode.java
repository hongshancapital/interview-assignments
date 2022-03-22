package cn.scdt.shorturl.model;

/**
 * 状态码
 * @author TianWenTao
 */
public enum ResultStatusCode {

    UPLOADSUCCESS("1", "住院病案首页上传成功"),
    UPLOADFAIL("0", "住院病案首页上传失败"),
    REMOVESUCCESS("1", "住院病案首页冲销成功"),
    REMOVEFAIL("0", "住院病案首页冲销失败"),

    SUCCESS("200", "住院病案首页上传成功"),
    FAIL("101", "基础校验失败( ( 返回校验失败数据, , 非空、超长、格式错误 [FAILDATA])"),
	
	FEEDATA("1", "费用明细数据"),
	CASDATA("0", "病案首页数据"),
    REMOVEDATA("2", "病案冲销数据"),
    REVERSEFEEDATA("3", "费用明细冲销数据"),
    ORGREGIST("4", "机构注册数据"),
    ORGUPDATE("5", "机构更新数据"),
    HOSPITALINFO("6", "医院信息数据"),
    HOSPITALUPDATE("7", "医院信息更新数据"),
    LCZK("8", "病案质控数据"),
    DRGRESULT("9", "DRG分组数据"),
    DEPTMATCHINFO("10", "DRG科室对照"),
    DEPTMATCHUPDATE("11", "DRG科室对照更新"),
	;

    private String code;
    private String msg;

    private ResultStatusCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
