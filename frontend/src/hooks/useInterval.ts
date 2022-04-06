import { useEffect, useRef } from "react";

export default function useInterval(callback: () => void, delay: number) {
    const savedCallback = useRef<() => void>();
  
    // 保存新回调
    useEffect(() => {
        savedCallback.current = callback;
    });
  
    // 建立 interval
    useEffect(() => {
        function tick() {
            savedCallback.current?.();
        }
        if (delay !== null) {
            let id = setInterval(tick, delay);
            return () => clearInterval(id);
        }
    }, [delay]);
}