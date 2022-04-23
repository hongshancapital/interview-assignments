import { useRef } from "react"
import { filter, fromEvent, map, pairwise, switchMap, windowToggle } from "rxjs"
import { useObservableCallback } from "./useObservableCallback"

export interface IUseSwipe {
    onStart: () => void;
    onStop: () => void;
    onMove: (percent: number) => void;
}
export function useSwipe(callback: IUseSwipe) {
    const mouseDown$ = useRef(fromEvent(window, "mousedown"))
    const mouseUp$ = useRef(fromEvent(window, "mouseup"))
    const mouseMove$ = useRef(
        fromEvent<MouseEvent>(window, "mousemove")
            .pipe(
                windowToggle(mouseDown$.current, () => mouseUp$.current),
                switchMap(window$ => window$.pipe(
                    pairwise(),
                    // 滑过半屏算滑完
                    map(([prev, current]) => (current.clientX - prev.clientX) / window.innerWidth * 2),
                    filter(Boolean)
                )),
            )
    )
    useObservableCallback(mouseDown$.current, callback.onStart)
    useObservableCallback(mouseUp$.current, callback.onStop)
    useObservableCallback(mouseMove$.current, callback.onMove)
}