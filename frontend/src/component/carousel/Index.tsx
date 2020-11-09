import * as React from 'react'
import CountDown from './CountDown'
import './carousel.css'

interface Item {
    title: string
    info?: string
    icon: string
    theme?: string // 通过此项可以选择item的主题风格，目前支持三种风格（white|grey|black）可增加更多配置
}

interface Props {
    items: Item[]
    duration?: number
    stop?: boolean
}

// 渲染列表
const renderItem = (item: Item, index: number): React.ReactNode => {
    return (
        <div className={`carousel-item ${item.theme? item.theme : 'white'}`} key={index}>
            <div className="title" dangerouslySetInnerHTML={{__html:item.title}}/>
            <div className="info" dangerouslySetInnerHTML={{__html:item.info || ''}}/>
            <div className="img">
                <img alt="" src={item.icon} />
            </div>
        </div>
    )
}
let interval:any = null

// 创建定时器，通过作用执行轮播动画
function useInterval( callback: () => void , duration: number, stop: boolean) {
    React.useEffect(() => {
        if (stop) {
            clearInterval(interval)
            return
        }
        interval = setInterval(callback, duration)
        return () => {
            clearInterval(interval)
        }
    }, [stop, duration, callback])
}

function setStyles(w: number, l: number) {
    return {
        width: `${w * 100}%`,
        transform: `translateX(-${l * 100}vw)`
    }
}

const Carousel: React.FC<Props> = ({ items, duration = 3000, stop= false }) => {
    const list = items || [], len = list.length
    const [current, setCurrent] = React.useState<number>(0)
    let styles = setStyles(len, current)

    useInterval(() => {
        setCurrent(current => {
            return current < len - 1 ? current + 1 : 0
        })
        styles = setStyles(len, current)
    }, duration, stop)

    const speedProps = {
        current,
        duration,
        count: len,
        stop,
        onStop: (i: number) => {
            setCurrent(current => i)
        }

    }
    return (
        <div className="ui-carousel">
            <div className="carousel-container" style={styles}>
                { list.map(renderItem) }
            </div>
            <CountDown {...speedProps}/>
        </div>
    )
}

export default Carousel
