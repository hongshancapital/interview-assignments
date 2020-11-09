import * as React from 'react'

interface Props {
    current: number
    duration: number
    count: number
    stop?: boolean
    onStop?: (i: number) => void
}

const CountDown: React.FC<Props> = (props) => {
    const { current, duration, count, stop, onStop } = props
    const list: any[] = []
    for (let i =0; i < count; i++) {
        list.push(i)
    }

    const ref = React.useRef<HTMLDivElement | null>(null)

    React.useEffect(() => {
        ref.current?.querySelectorAll('span').forEach((item, index) => {
            console.log(item, index)
            if (index === current) {
                if (!stop) {
                    item.style.transitionDuration = `${duration}ms`
                }
                item.style.width = `100%`
            } else {
                item.style.transitionDuration = `${0}ms`
                item.style.width = `0%`
            }
        })
    }, [current, stop, duration])

    return (
        <div className="carousel-countdown" ref={ref}>
            {
                list.map((item, index) => {
                    return (
                        <div key={index} onClick={() => {
                            stop && onStop && onStop(index)
                        }}>
                            <span></span>
                        </div>
                    )
                })
            }
        </div>
    )
}

export default CountDown
