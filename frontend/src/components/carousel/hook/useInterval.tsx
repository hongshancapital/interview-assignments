import { MutableRefObject, useCallback, useEffect, useRef, useState } from "react";
export type Noop = () => void;
type ChangeInterVal = (config: {callback?: Noop, ms?: number}) => void;

/**
 * 清除定时器
 * @param intervalRef 
 */
const cleanInterval = (intervalRef: MutableRefObject<NodeJS.Timer  | undefined>) => {
    if (intervalRef.current) {
        clearInterval(intervalRef.current!);
        intervalRef.current = undefined;
    }
}

/**
 * interval hook
 * @param callback 
 * @param ms 
 * @param running 初始化是否执行
 * @returns 
 */
export const useInterval = (callback: Noop, ms: number = 2000, running: boolean = true) => {
    const intervalTimerRef = useRef<NodeJS.Timer | undefined>();
    const [currentCB, setCurrentCB] = useState<{callback: Noop, ms: number}>({callback, ms});
    const [isStopInterval, setIsStopInterval] = useState<boolean>(!running);
    
    /** 重置定时器 */ 
    const resetInterval = useCallback(() => {
        cleanInterval(intervalTimerRef);
        if (!intervalTimerRef.current) {
            if (typeof(currentCB.ms) !== "number") {
                throw new Error(`useInterval Error: typeof ms: ${typeof(currentCB.ms)} is not assignable to type 'number' `)
            }
            const validMs = Math.max(currentCB.ms, 0);
            intervalTimerRef.current = setInterval(currentCB.callback, validMs)
        }
    }, [currentCB])

    useEffect(function(){
        if (!isStopInterval) {
            resetInterval();
        }
        return () => cleanInterval(intervalTimerRef);   
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [currentCB]);

    /**
     * 重新生成定时器对象
     * @param callback 重置callback
     * @param ms 重置ms
     * @returns 
     */
    const chagneInterVal: ChangeInterVal = ({callback, ms}) => {
        if (ms === currentCB.ms && callback === currentCB.callback) {
            return;
        }
        const mergeCB = callback || currentCB.callback;
        const mergeMs =  ms && ms >= 0 ? ms : currentCB.ms;
        setCurrentCB({
            callback: mergeCB,
            ms: mergeMs,
        })
    }
    
    /** 工具 tool */
    const intervalTool = {
        /**
         *  重置
         */
        resetInterval, 
        /**
         * 停止定时
         */
        stopInterval() {
            cleanInterval(intervalTimerRef);
            setIsStopInterval(true);
        }, 
        /**
         * 开启定时
         */
        startInterval(){
            setIsStopInterval(false);
            resetInterval();
        },
        /**
         * 是否暂停
         */
        isStopInterval
    }
    return [chagneInterVal, intervalTool] as const;
}