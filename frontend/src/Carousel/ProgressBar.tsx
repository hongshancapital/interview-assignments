import { useEffect, useState } from "react"
import './ProgressBar.css'

export interface ProgressBarProps {
    size: number
    index: number
    duration: number
}
export default function ProgressBar(props: ProgressBarProps) {
    const { size = 0, index = 0, duration } = props
    useEffect(() => { }, [index, size])
    return <div className="_progress-bar" data-testid="progress-bar">
        {(new Array(size).fill(1).map((_, i) => <div key={i} className="_item" data-testid="progress-item">
            {i === index && <Progress key={Math.random()} duration={duration} />}
        </div>))}
    </div>
}

function Progress(props: { duration: number }) {
    const [started, setStarted] = useState(false)
    // 渲染后开始变形动画
    useEffect(() => {
        const timer = requestAnimationFrame(() => setStarted(true))
        return () => cancelAnimationFrame(timer)
    }, [])
    return <div
        className="_progress"
        style={{
            width: started ? '100%' : 0,
            transitionDuration: `${props.duration}ms`
        }}
        data-testid="progress"
    ></div>
}