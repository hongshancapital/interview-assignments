/**
 * 这只是一个简单的轮播，有很多用户交互上的优化没有做完
 * 待优化：轮播悬浮停顿、手动切换
 * 可扩展：动画执行时间自定义、轮播距离自定义（像swiper那种可以实现一次移动四分之一，假如每个轮播图只是占据轮播区域的四分之一）
 *        纵向轮播、循环轮播等等
 */
import React, { useEffect, useRef, CSSProperties, useState } from "react"
import './index.css'

interface CarouselProps {
    children: JSX.Element[] | JSX.Element,
    speed: number,
    slideStyle: CSSProperties,
    width: string,
    height: string
}

interface BrakePadProps {
    isStart: boolean,
    speed: number,
    width: number,
    isEnd?: Function
}

function BrakePad({ isStart = false, speed, width, isEnd }: BrakePadProps) {
    const [lon, setLon] = useState(0)

    useEffect(() => {
        const animation: Function = () => {
            let start_time: number = 0
            const timer = (time: number) => {
                if (start_time == 0) {
                    start_time = time
                    return requestAnimationFrame(timer)
                }
                if (time - start_time >= speed) {
                    setLon(width)
                    isEnd && isEnd()
                } else {
                    setLon((time - start_time) / speed * width)
                    return requestAnimationFrame(timer)
                }
            }
            return requestAnimationFrame(timer)
        }
        if (isStart) animation()
    }, [isStart])

    return <div className="brake-pad" style={{
        width
    }}>
        <div style={{
            width: isStart ? lon : 0
        }}></div>
    </div>
}

function Carousel({ speed, children, height, width, slideStyle }: CarouselProps) {
    const [containerWidth, setContainerWidth] = useState(0)
    const [containerHeight, setContainerHeight] = useState(0)
    const [indexNow, setIndexNow] = useState(0)
    const container: any = useRef(null);

    useEffect(() => {
        // 这地方要实现实时的宽高监听，引入react-use-observer看似可以解决这个问题
        // 处理这个问题稍微有点麻烦，介于只是展示一下思路，先不处理这个小bug
        if (container.current && 'clientHeight' in container.current) {
            setContainerHeight(container.current.clientHeight)
        }
        if (container.current && 'clientWidth' in container.current) {
            setContainerWidth(container.current.clientWidth)
        }
    })

    function speedOver() {
        if (indexNow < (children instanceof Array ? children : [children]).length - 1) {
            setIndexNow(indexNow + 1)
        } else {
            setIndexNow(0)
        }
    }

    return <div className='container' ref={container} style={{
        height,
        width
    }}>
        <div className="background" style={{
            left: `${-indexNow * containerWidth}px`
        }}>
            {
                (children instanceof Array ? children : [children]).map((item, idx) => {
                    return <div key={idx} className="slide" style={{
                        height: containerHeight,
                        width: containerWidth,
                        ...slideStyle
                    }}>{item}
                    </div>
                })
            }
        </div>
        <div className="bottom-tips">
            {
                (children instanceof Array ? children : [children]).map((item, idx) => {
                    return <BrakePad isStart={indexNow == idx} speed={speed} width={30} isEnd={speedOver}></BrakePad>
                })
            }
        </div>
    </div>
}


Carousel.defaultProps = {
    speed: 3000,
    width: '100%',
    height: '100%',
    slideStyle: {}
}

export default Carousel
