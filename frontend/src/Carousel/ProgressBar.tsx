import './ProgressBar.css'

export interface ProgressBarProps {
    size: number
    index: number
    duration: number
}
export default function ProgressBar(props: ProgressBarProps) {
    const { size = 0, index = 0, duration } = props

    if (!size) {
        return null
    }

    return <div className="_progress-bar" data-testid="progress-bar">
        {(new Array(size).fill(1).map((_, i) => <div key={i} className="_item" data-testid="progress-item">
            {i === index && <div
                className="_progress"
                style={{
                    transitionDuration: `${duration}ms`
                }}
                data-testid="progress"
            ></div>}
        </div>))}
    </div>
}
