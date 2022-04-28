package demo.strategy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum URLTransStrategyEnum {
    BASE62("Base62URLTransStrategy");

    private final String name;

    public static URLTransStrategyEnum getByName(String name) {
        for (URLTransStrategyEnum e : URLTransStrategyEnum.values()) {
            if (name.equals(e.getName())) {
                return e;
            }
        }
        return null;
    }
}
