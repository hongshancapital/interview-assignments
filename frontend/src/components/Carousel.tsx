import React, { useEffect, useState, useRef } from 'react'
import classnames from 'classnames'
import styles from './Carousel.module.css'

export interface CardItem {
    key: number,
    url: string,
    title: string,
    color: string,
    subTitle?: string,
    text?: string,
    desc?: string
}

interface CarouselProps {
    children: React.ReactNode,
    time?: number,
    delay?: number
}

function Carousel ({ children, time = 2500, delay = 500 }: CarouselProps) {
    const cards = Array.isArray(children) ? children : [children]
    const cardDom = useRef<HTMLDivElement | null>(null)
    useEffect(() => {
        cardDom?.current?.style.setProperty('--timer-time', `${(time / 1000).toFixed(3)}s`)
        cardDom?.current?.style.setProperty('--timer-delay', `${(delay / 1000).toFixed(3)}s`)
    }, [time, delay])

    const [tabIdx, setTabIdx] = useState<number>(0)
    useEffect(() => {
        const timer: NodeJS.Timer = setInterval(() => {
            setTabIdx((tabIdx) => {
                if (tabIdx >= cards.length - 1) {
                    return 0
                }
                else {
                    return tabIdx + 1
                }
            })
        }, time)
        return () => {
            clearInterval(timer)
        }
    }, [cards.length, time])
    return (
        <div ref={ cardDom } className={ styles['carousel-content'] }>
            <ul className={ styles['carousel-ul'] }>
                {
                    cards.map((childItem: React.ReactNode, index: number) => {
                        return (
                            <li
                                className={ classnames(styles['carousel-li'], {
                                    [styles['carousel-li-before']]: index < tabIdx,
                                    [styles['carousel-li-active']]: index === tabIdx,
                                    [styles['carousel-li-after']]: index > tabIdx
                                }) }
                                key={ index }
                            >
                                { childItem }
                            </li>
                        )
                    })
                }
            </ul>
            <div className={ styles['carousel-bar'] }>
                {
                    cards.map((childItem: React.ReactNode, index: number) => {
                        return (
                            <div className={ styles['carousel-dot'] } key={ index }>
                                <div className={ classnames({
                                    [styles['carousel-dot-pregress']]: tabIdx === index
                                }) }></div>
                            </div>
                        )
                    })
                }
            </div>
        </div>
    )
}
export default Carousel