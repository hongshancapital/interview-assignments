import { useCallback } from "react"
import { animationFrames, switchMap, map, startWith, Subject, merge, windowToggle, withLatestFrom, takeUntil, scan } from "rxjs"

// 开始自然时间轴
const play$ = new Subject<void>()
// 暂停自然时间轴
const pause$ = new Subject<void>()
// 重置自然时间轴，支持初始时间
const reset$ = new Subject<number>()
// 快进或快退
const advance$ = new Subject<number>()

const naturalTimer$ = reset$.pipe(
    startWith(0),
    switchMap(
        startTime => animationFrames().pipe(
            takeUntil(pause$),
            map(({ elapsed }) => elapsed + startTime)
        )
    )
)

const manualTimer$ = advance$.pipe(
    windowToggle(pause$, () => play$),
    withLatestFrom(naturalTimer$),
    switchMap(([window$, elapsed]) => window$.pipe(
        scan((a, b) => a + b, 0),
        map((advanceTime) => elapsed + advanceTime)
    ))

)
// 单例，共用一个时间轴
const timer$ = merge(
    naturalTimer$,
    manualTimer$
)

play$.pipe(
    withLatestFrom(timer$, (_, b) => b),
).subscribe(reset$)

export function useTimer() {
    const restartFrom = useCallback((startTime: number) => {
        reset$.next(startTime)
    }, [])
    const play = useCallback(() => {
        play$.next()
    }, [])
    const pause = useCallback(() => {
        pause$.next()
    }, [])
    const advance = useCallback((startTime: number) => {
        advance$.next(startTime)
    }, [])

    return {timer$, restartFrom, advance, play, pause}
}