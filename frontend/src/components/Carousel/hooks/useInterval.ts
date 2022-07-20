import React, {useEffect, useState} from "react"

export interface UseIntervalProps {
    milliseconds?: number;
    maxCount?: number;
}

const useInterval: (...props: any) => [number, boolean, () => () => void] = (...props) => {
    const [milliseconds = 2000, autoplay = true, maxCount = 10] = props

    const [percent, setPercent] = useState(0)
    const [finished, setFinished] = useState(false)

    const startInterval = () => {
        setPercent(0)
        setFinished(false)
        const timer = setInterval(() => {
            // 因为闭包效应，且percent没有纳入useEffect的依赖项中，故而不能获取到最新的percent，需使用回调方式
            setPercent(percent => {
                if (percent >= (1 - 1 / maxCount)) {
                    setFinished(true)
                    clearInterval(timer)
                    return 1
                }
                return percent + (1 / maxCount)
            })
        }, milliseconds / maxCount)
        return () => clearInterval(timer)
    }

    useEffect(() => {
        if (autoplay) {
            return startInterval()
        }
    }, [milliseconds, maxCount, autoplay])

    return [percent, finished, startInterval]
}

export default useInterval
