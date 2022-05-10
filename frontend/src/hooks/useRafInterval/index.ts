import {useEffect, useRef} from 'react';
import {setRafInterval, clearRafInterval} from './utils'

function useRafInterval(
    fn: () => void,
    delay: number | undefined,
    options?: {
        immediate?: boolean;
    },
) {
    const immediate = options?.immediate;

    const fnRef = useRef<typeof fn>(fn);

    useEffect(() => {
        if (typeof delay !== 'number' || delay < 0) return;
        if (immediate) {
            fnRef.current();
        }
        const timer = setRafInterval(() => {
            fnRef.current();
        }, delay);
        return () => {
            clearRafInterval(timer);
        };
    }, [delay]);
}

export default useRafInterval;
