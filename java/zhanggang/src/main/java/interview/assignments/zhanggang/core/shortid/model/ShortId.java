package interview.assignments.zhanggang.core.shortid.model;

import interview.assignments.zhanggang.support.Base62Util;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ShortId {
    private final long seed;

    public String getValue() {
        return Base62Util.base10to62(seed);
    }
}
