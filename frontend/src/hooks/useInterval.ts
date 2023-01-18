import { useEffect } from "react";
export type EachInterval = <T, >(next: () => T) => T
export type IntervalOptions = {
    enable?: boolean,
    each: EachInterval,
}
const useInterval = (interval: number, options: IntervalOptions) => {
    const { enable, each } = options
    useEffect(() => {
        let timmer: NodeJS.Timeout;
        if (enable && !!interval) {
            timmer = setTimeout(()=>each(() => {
                clearTimeout(timmer)
                return Symbol('next')
            }), interval);
        }
        return () => {
            clearTimeout(timmer)
        }
    }, [interval, each, enable])
}
export default useInterval