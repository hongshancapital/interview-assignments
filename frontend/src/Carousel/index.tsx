import React, { useRef, useState, useLayoutEffect, useEffect, useMemo } from 'react';
import './index.css';

interface PropsFace {
    children: any; // children
    autoplay?: boolean; //  是否自动播放
    time?: number // 时间
}



const Carousel = ({ children, autoplay = false, time = 3 }: PropsFace) => {
    const delayRef: any = useRef(); // 绑定定时器

    const [itemWidth, setItemWidth] = useState<number>(0) // 每个元素宽度

    const [active, setActive] = useState<number>(0) // 索引

    const [animationType, setAnimationType] = useState<'running' | 'paused'>(autoplay ? 'running' : 'paused') // 是否动画

    useLayoutEffect(() => {
        //  获取父元素宽度
        const width: number = document.querySelector('.carousel-main')?.clientWidth ?? 0

        setItemWidth(width)
    }, [])
    useEffect(() => {
        // 判断获取宽度后而且自动播放
        if (itemWidth > 0 && autoplay) {

            start()
        }
    }, [itemWidth, active])
    // 自动播放
    const start = () => {
        delayRef.current = setTimeout(() => {

            setActive((state: number) => {
                if (state >= children.length - 1) {
                    return 0
                }
                return state + 1
            })

            if (animationType !== 'running') {

                setAnimationType('running')

            }
        }, time * 1000)
    }
    // 渲染child
    const renderChild = useMemo(() => {

        const maxWidth = itemWidth * children.length

        const dom = children.map((it: any, index: number) => {
            return <div data-index={index} key={index} style={{ width: itemWidth }} className='list-item'>
                {it}
            </div>
        })

        return <div className='carousel-list'>

            <div style={{ width: maxWidth, transform: `translate3d(${-itemWidth * active}px, 0, 0 )` }} className='carousel-item-main'>

                {dom}

            </div>
        </div>
    }, [children, active, itemWidth])
    // 点击
    const itemClick = (index: number) => {
        setAnimationType('paused')

        clearTimeout(delayRef.current)

        setActive(index)

    }
    // 渲染按钮
    const renderBtn = useMemo(() => {
        return <ul className='ul-btn'>

            {
                children.map((it: any, index: number) => {

                    let newStyle = {}

                    let dom = null;

                    if (autoplay && animationType === 'running') {

                        newStyle = { opacity: index !== active ? 0 : 1, animationPlayState: index !== active ? 'paused' : 'running', animationDuration: `${time}s` }

                        dom = <div style={newStyle} className={`progress ${index === active && autoplay ? 'animation' : ''}`} />

                    } else {
                        if (active === index) {

                            dom = <div style={newStyle} className={`progress ${index === active && autoplay ? 'animation' : ''}`} />

                        }
                    }
                    return <li onClick={() => {itemClick(index)}} key={index}>
                        <div className='btn-progress'>
                            {
                                dom
                            }
                        </div>

                    </li>
                })
            }
        </ul>
    }, [children, animationType, autoplay, active, time])


    return <div className='carousel-main'>
        {
            renderChild
        }
        {
            renderBtn
        }

    </div>
}


export default Carousel;
