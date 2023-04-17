// React may ship this hook later, this is a polyfill
// see: https://github.com/reactjs/rfcs/blob/useevent/text/0000-useevent.md
import { useCallback, useLayoutEffect, useRef } from 'react';

const useEvent = (fn: Function) => {
    const fnRef = useRef(fn);

    useLayoutEffect(() => {
        fnRef.current = fn;
    });

    return useCallback((...args: unknown[]) => {
        return fnRef.current(...args);
    }, []);
};

export default useEvent;
