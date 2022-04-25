import { animationFrames, map } from "rxjs"

// 单例，共用一个时间轴
const timer$ = animationFrames()
    .pipe(
        map(({ elapsed }) => elapsed)
    )

export function useTimer() {
    return { timer$ }
}