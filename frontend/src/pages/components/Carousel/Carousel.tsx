import React, { FC, useState, useEffect } from 'react';
import './index.css'
import { CarouselItem } from './CarouselItem'

interface ListItem {
    id: number,
    color: string
    title: Array<string>,
    details?: Array<string>,
    imageSrc: string,
}

interface CarouselProps {
    dataList: Array<ListItem>,
    interval?: number
}

export const Carousel: FC<CarouselProps> = ({
                                                dataList,
                                                interval
                                            }) => {
    const [activeIndex, setActiveIndex] = useState(-1) // 索引页
    const [list] = useState([...dataList])
    const LENGTH = dataList.length
    const INTERVAL = interval || 3000

    const handleShowPage = () => {
        if(activeIndex < LENGTH - 1){
            setActiveIndex(pre => ++pre)
        } else {
            setActiveIndex(0)
        }
    }

    useEffect(() => {
        handleSetActiveIndex(activeIndex)
    }, [activeIndex])

    useEffect(() => {
        let timer:any = null
        timer = setInterval(handleShowPage, INTERVAL)
        return () => {
            clearInterval(timer)
        }
    },[])

    const handleSetActiveIndex = (index: number) => {
        // 如果索引小于0，循环播放，设置当前页为最后一页，继续向前切换到第一张
        if (index >= LENGTH) {
            // 如果索引大于数组长度，循环播放，设置当前页为第一页，即播到最后一张时切到第一张
            setActiveIndex(0)
        } else {
            // 否则设置当前页为索引页
            setActiveIndex(index)
        }
    }

    // 计算图片偏移量
    const calcTranslate = (index: number) => {
        if (index === activeIndex) {
            return '0'
        } else if (index === activeIndex - 1 || index === activeIndex  + LENGTH - 1){
            return '-100%'
        } else {
            return '100%'
        }
    }

    return <div className='carousel'>
        {
            list.map((item, index) => {
                return <CarouselItem
                    key={item.id}
                    active={index === activeIndex}
                    animating={index === activeIndex ||index === activeIndex - 1 || index + LENGTH - 1 === activeIndex}
                    translate={calcTranslate(index)}
                    {...item}/>
            })
        }
        <ul className='carousel-indicator-wrap'>
            {
                list.map((item, index) =>
                    <li key={item.id}
                        className={['carousel-indicator'].join('')}>
                        <div className={ index === activeIndex ? 'is-indicator-active' : ''}/>
                    </li>)
            }
        </ul>
    </div>
}
