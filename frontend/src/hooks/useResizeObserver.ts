import { RefObject, useEffect, useRef } from "react";

type ResizeHandler = (rect: DOMRectReadOnly) => void;

export default function useResizeObserver<T extends Element>(ref: RefObject<T>, onResize: ResizeHandler) {
    const onResizeRef = useRef<ResizeHandler | null>(null);
    const resizeObserverRef = useRef<ResizeObserver>();

    useEffect(() => {
        onResizeRef.current = onResize;
    }, [onResize]);

    useEffect(() => {
        const element = ref.current;

        if (!element) return;
        
        resizeObserverRef.current = new ResizeObserver((entries) => {
            const entry = entries[0];
            onResizeRef.current?.(entry.contentRect);
        });

        resizeObserverRef.current.observe(element);

        return () => {
            resizeObserverRef.current?.unobserve(element);
        }
    }, [ref]);
}