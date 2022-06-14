/*
 * @Author: longsiliang longsiliang@sogou-inc.com
 * @Date: 2022-06-10 19:28:04
 * @LastEditors: longsiliang longsiliang@sogou-inc.com
 * @LastEditTime: 2022-06-11 13:57:09
 * @FilePath: /interview-assignments/frontend/src/components/hooks/use-interval.ts
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import { useEffect, useRef } from "react";

type Noop = (...args: any[]) => any;

export const useInterval = (callback: Noop, delay?: number | null) => {
    const savedCallback = useRef<Noop>(() => { });

    useEffect(() => {
        savedCallback.current = callback;
    });

    useEffect(() => {
        if (delay !== null) {
            const interval = setInterval(() => savedCallback.current(), delay || 0);
            return () => clearInterval(interval);
        }
        return undefined;
    }, [delay]);
}