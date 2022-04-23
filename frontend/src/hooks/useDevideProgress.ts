import { useMemo } from "react"
import { distinctUntilChanged, map } from "rxjs"
import { useTimer } from "./userTimer"

export function useDevideProgress(duration: number, count: number) {
    const {timer$} = useTimer()

    return useMemo(() => {
        const progress$ = timer$
            .pipe(
                map(elapsed => (elapsed / duration * 100) % (count * 100)),
            )

        return Array.from({
            length: count
        }).map((_, i) =>
            progress$.pipe(
                map((v) => v > (i + 1) * 100 ? 0 : Math.max(v - i * 100, 0)),
                distinctUntilChanged()
            )
        )
    }, [duration, count, timer$])
}