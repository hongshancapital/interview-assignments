import React, { forwardRef, useEffect, useMemo, useRef, useState, useImperativeHandle } from "react"
import css from './index.module.css'

interface Props {
    children?: React.ReactNode,
    autoplay?: boolean,
    showDots?: boolean,
    dotPosition?: string,
    showBtn?: boolean,
    onChange?: (index: number) => void
}
export interface carouselRef {
    next: () => void,
    prev: () => void,
    directShow: (index: number) => void
}

const defaultProps: Props = {
    autoplay: true,
    showDots: true,
    showBtn: false,
    dotPosition: 'bottom',
}

const Carousel = forwardRef<carouselRef, Props>((props, ref) => {
    const { children = null, showDots, dotPosition, autoplay, onChange, showBtn } = props
    const timerRef = useRef<NodeJS.Timeout | null>(null); // 计时器
    const scrollRef = useRef<HTMLDivElement>(null) //slider滚动容器
    const dotWrapperRef = useRef<HTMLDivElement>(null) //指示器
    const sliderCount = React.Children.count(children) //slider个数
    const [currIndex, setCurrIndex] = useState<number>(-1) //当前活跃slider
    const sliderDots = useMemo(() => {//切换提示按钮个数 - Array
        return new Array(sliderCount)?.fill(0) ?? []
    }, [sliderCount])
    const sliderWrapperCls = useMemo(() => {//slider根据提示器位置进行变更 垂直|水平切换
        if (['left', 'right'].includes(dotPosition!)) {
            return css['vertical-slider-wrapper']
        }
        return ''
    }, [dotPosition])
    const dotWrapperCls = useMemo(() => {//提示器位置样式
        if (['left', 'right', 'top', 'bottom'].includes(dotPosition!)) {
            return css[`${dotPosition}-dot-wrapper`]
        } else {
            return css['bottom-dot-wrapper']
        }
    }, [dotPosition])
    /**
     * @description: 设置指示器样式
     * @param {number} curr：指示器下标
     * @return {*} 活跃指示器class
     */
    const dotsCls = (curr: number): string => {
        if (!autoplay) return `${curr === currIndex ? css['active-notauto-carousel-dot'] : ''}`
        return `${curr === currIndex ? css['active-carousel-dot'] : ''}`
    }
    /**
     * @description: 滚动slider
     * @param {string} type 操作类型
     */
    const setCurrentSlider = (type: string = 'next', index: number = 0) => {
        const { clientWidth, clientHeight } = scrollRef.current!
        setCurrIndex(pre => {
            switch (type) {
                case 'next':
                    pre = pre === sliderCount - 1 ? 0 : pre += 1
                    break;
                case 'pre':
                    pre = pre === 0 ? sliderCount - 1 : pre -= 1
                    break;
                case 'direct':
                    pre = index
                    break;
            }
            if (['bottom', 'top'].includes(dotPosition!)) {
                scrollRef.current!.style.transform = `translateX(-${clientWidth * pre}px)`
            } else if (['left', 'right'].includes(dotPosition!)) {
                scrollRef.current!.style.transform = `translateY(-${clientHeight * pre}px)`
            } else {
                scrollRef.current!.style.transform = `translateX(-${clientWidth * pre}px)`
            }
            return pre
        })
    }
    /**
     * @description: 开始播放
     */
    const startPlay = () => {
        if (!autoplay) return
        timerRef.current = setInterval(() => {
            setCurrentSlider()
        }, 3000)
    }
    /**
     * @description: 下一个
     */
    const next = () => {
        if (timerRef.current) {
            clearInterval(timerRef.current)
            timerRef.current = null
            startPlay()
        }
        setCurrentSlider('next')
    }
    /**
     * @description: 上一个
     */
    const prev = () => {
        if (timerRef.current) {
            clearInterval(timerRef.current)
            timerRef.current = null
            startPlay()
        }
        setCurrentSlider('pre')
    }
    /**
     * @description: 直接切换到某一个
     * @param {*} i
     */
    const directShow = (i: number) => {
        if (timerRef.current) {
            clearInterval(timerRef.current)
            timerRef.current = null
            startPlay()
        }
        setCurrentSlider('direct', i)
    }
    /**
     * @description: 抛出切换方法
     */
    useImperativeHandle(ref, () => ({
        next,
        prev,
        directShow
    })
    )
    useEffect(() => {
        onChange && onChange(currIndex)
    }, [currIndex, onChange])
    useEffect(() => {
        setCurrIndex(0)
        startPlay()
        return () => {
            if (timerRef.current) {
                clearInterval(timerRef.current)
                timerRef.current = null
            }
        }
    }, [])

    return (
        <div className={css['carousel-wrapper']}>
            <div ref={scrollRef} className={`${css['carousel-slider-wrapper']} ${sliderWrapperCls}`}>
                {
                    React.Children.map(children, (child, i) => (
                        <div key={i} className={css['carousel-slider']}>
                            {child}
                        </div>
                    ))
                }
            </div>
            {
                showDots ?
                    <div ref={dotWrapperRef} className={`${css['carousel-dot-wrapper']} ${dotWrapperCls}`}>
                        {
                            sliderDots?.map((_, i) =>
                                <div
                                    key={i}
                                    className={`${css['carousel-dot']} ${dotsCls(i)}`}
                                    onClick={() => { directShow(i) }}
                                ></div>
                            )
                        }
                    </div> : null
            }
            {
                showBtn ?
                    <>
                        <div className={css['next-btn']} onClick={() => { next() }}>{'>'}</div>
                        <div className={css['pre-btn']} onClick={() => { prev() }}>{'<'}</div>
                    </> : null
            }

        </div>
    )

})
Carousel.defaultProps = defaultProps

export default Carousel