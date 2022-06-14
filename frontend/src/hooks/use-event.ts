/*
 * @Author: longsiliang longsiliang@sogou-inc.com
 * @Date: 2022-06-10 19:27:51
 * @LastEditors: longsiliang longsiliang@sogou-inc.com
 * @LastEditTime: 2022-06-11 14:00:42
 * @FilePath: /interview-assignments/frontend/src/components/hooks/use-event.ts
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import { useLayoutEffect, useRef } from "react";

type AnyFunction = (...args: any[]) => any;

const noop = () => { };

export function useEvent<T extends AnyFunction>(fn: T): any {
    const latestRef = useRef<T>(noop as any);

    useLayoutEffect(() => {
        latestRef.current = fn;
    }, [fn]);

    const stableRef = useRef<T>(null as any);
    if (!stableRef.current) {
        stableRef.current = function (this: any) {
            return latestRef.current.apply(this, arguments as any);
        } as T;
    }
}

export default useEvent;