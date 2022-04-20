package pers.zhufan.shorturl.domain.shorturl;

/**
 * @Author zhufan
 * @Date 2022-04-18 20:50
 * @ClassName ShorterWithSimpleString
 * @Description 只存储简单String的短链接对象
 */
public class ShorterWithSimpleString implements ShorterUrl {

    private String shorter;

    public ShorterWithSimpleString() {
    }

    public ShorterWithSimpleString(String shorter) {
        setShorter(shorter);
    }
    @Override
    public String getShorter() {
        return shorter;
    }

    public void setShorter(String shorter) {
        this.shorter = shorter;
    }


}
