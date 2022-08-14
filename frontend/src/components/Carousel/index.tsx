import React, { useEffect, useState, FC, useMemo, ReactNodeArray  } from 'react';
import './index.css'


interface carouselProps {
    // 轮播宽度
    width?: number,
    // 轮播高度
    height?:number,
    // 过渡时间ms
    duration?:number,
}

const Carousel: FC<carouselProps> = ({ children, width = 800, height = 500, duration = 2000 }) => {
    const [current, setCurrent] = useState<number>(0)

    const childrenLength = useMemo<number>(() => {
        return React.Children.count(children)
    }, [children])

    const run = () => {
        setCurrent((idx) => {
            if (idx < childrenLength - 1) {
                return idx + 1
            } else {
                return 0
            }
        })
    }

    useEffect(() => {
        const timer = setInterval(run, duration);
        return () => {
            clearInterval(timer)
        }
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])
    
    return (
        <div className='carousel' style={{
            width,
            height,
        }}>
            {/* 轮播内容 */}
            <div className='carousel-list' style={{ width: width * childrenLength, left: `-${width * current}px` }}>
                { children }
            </div>
            {/* 轮播点 */}
            <ul className='carousel-current-list'>
               {
                   new Array(childrenLength).fill(1).map((num, index) => (
                       <li key={index}>
                           <span 
                                className={index === current ? 'change-white' : ''}
                                style={{ transitionDuration: index === current ? `${duration / 1000}s` : `0s` }} 
                            />
                       </li>
                   ))
               }
            </ul>
        </div>
    )
}

export { default as CarouselItem } from './CarouselItem'
export default Carousel