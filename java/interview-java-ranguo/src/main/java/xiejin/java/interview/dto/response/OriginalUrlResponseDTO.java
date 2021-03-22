package xiejin.java.interview.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xiejin
 * @date 2021/3/20 13:22
 */
@Data
@Accessors(chain = true)
public class OriginalUrlResponseDTO implements Serializable {

    private static final long serialVersionUID = -8495886274111326350L;
    private String originalUrl;

}
