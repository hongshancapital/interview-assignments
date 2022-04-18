import  React, {useState} from 'react';

export default function useMergedState<T extends (string | number)>(
    defaultStateValue: T | (() => T),
    option?: {
        defaultValue?: T | (() => T);
        value?: T;
    },
): [T, (value: ((val: T) => T) | T) => void] {
    const { defaultValue, value } = option || {};
    // 获取初始化值，如果是受控组件，则使用外部传入的value。否则使用默认
    const [innerValue, setInnerValue] = useState(() => {
        if (value !== undefined) {
            return value;
        }
        if (defaultValue !== undefined) {
            return typeof defaultValue === 'function'
                ? (defaultValue as any)()
                : defaultValue;
        }
        return typeof defaultStateValue === 'function'
            ? (defaultStateValue as any)()
            : defaultStateValue;
    });

    const prevValueRef = React.useRef(value);

    React.useEffect(() => {
        if (value !== prevValueRef.current) {
            setInnerValue(value);
            prevValueRef.current = value;
        }
    }, [value]);
    return [innerValue, setInnerValue];
}