import { useCallback, useEffect, useRef } from 'react'
import { useDot } from './model'
import './index.scss'

export type CarouselProps = {
    list: CarouselItem[]
}

export type CarouselDotProps = {
    delay: number,
    active: boolean,
    dotClick: () => void
}

export interface CarouselItem {
    id: number,
    img: string,
    title: string,
    description: string,
    color: string,
    backgroundColor: string,
    singleWidth?: number
}

const Item = ({ singleWidth, title, img, description, color, backgroundColor }: CarouselItem) => {
    return <div className='carousel-item' style={{width: singleWidth, color: color, backgroundColor: backgroundColor}}>
        <h2 className='title carousel-title'>{title}</h2>
        <p className='text carousel-desc'>{description}</p>
        <img className='carousel-img' src={img} alt="" />
    </div>
}

const ItemDot = ({ delay, active, dotClick }: CarouselDotProps) => {
    let className = 'carousel-dot'
    if (active) {
        className += ' dot-active'
    }
    const handleDotClick = () => {
        dotClick();
    }

    return <div className={className} onClick={handleDotClick}><button style={{animationDuration: `${delay}s`}} /></div>
}

export const Carousel = ({ list }: CarouselProps) => {
    const { wrapWidth, singleWidth, index, setIndex } = useDot(list.length)
    const listCount = list.length
    const delay = 3 // 3s
    const timer = useRef<number>(0)

    const onDotClick = (index: number) => {
        clearTimer()
        setIndex(index)
        runTimer()
    }

    const runTimer = useCallback(() => {
        function running() {
            timer.current = window.setTimeout(() => {
                setIndex(index => (index + 1) % listCount)
                running()
            }, 1000 * delay);
        }
        running()
    }, [delay, listCount, setIndex])

    const clearTimer = () => {
        clearTimeout(timer.current)
    }
    
    useEffect(() => {
        runTimer()
        return () => {
            clearTimer()
        }
    }, [runTimer])
    return <div className='carousel-container'>
        <div className='carousel-wrap'>
            <div className='carousel-list' style={{width: wrapWidth, transform: `translate3d(-${index * singleWidth}px, 0px, 0px)`}}>
                {list.map(item => <Item key={item.id} {...item} singleWidth={singleWidth}></Item>)}
            </div>
        </div>
        <div className='carousel-dots'>
            {list.map((item, dotIndex) => <ItemDot key={item.id} delay={delay} active={index === dotIndex} dotClick={() => onDotClick(dotIndex)}></ItemDot>)}
        </div>
    </div>;
};
