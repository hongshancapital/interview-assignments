import React, {
    forwardRef,
    useCallback,
    useState,
    useImperativeHandle,
    useRef,
    useEffect,
} from 'react'
import classNames from 'classnames'
import s from './carousel.module.scss'

export interface CarouselRef {
    goTo: (index: number) => void
}
export interface CarouseProps {
    className?: string
    autoPlay?: number
    duration?: number
    showDots?: boolean
    dots?: React.ReactNode
    style?: React.CSSProperties
    children?: React.ReactNode
    onChange?: (selected: number, prevSelected: number) => void
}

const useStateRef = (initialValue: any): Array<any> => {
    const [value, setValue] = useState(initialValue)
    const ref = useRef(value)
    useEffect(() => {
        ref.current = value
    }, [value])
    return [value, setValue, ref]
}
const debounce = (fn: Function, delay: number): any => {
    let timer: any = null
    const debounced = (...args: any) => {
        timer && clearTimeout(timer)
        timer = setTimeout(() => {
            fn.apply(this, args)
        }, delay)
    }

    debounced.cancel = () => {
        if (timer) {
            clearTimeout(timer)
            timer = null
        }
    }

    return debounced
}

const Carousel = forwardRef<CarouselRef, CarouseProps>(
    (
        {
            className = '',
            autoPlay = 3000,
            duration = 300,
            showDots = true,
            dots = null,
            style = {},
            children,
            onChange = () => { }
        }: CarouseProps,
        ref
    ) => {
        const [node, setNode] = useState<HTMLDivElement | null>(null)
        const container = useRef<HTMLDivElement | null>(null)
        const [itemWidth, setItemWidth] = useState(0)
        const [active, setActive, activeRef] = useStateRef(0)
        const [carouselStyle, setCarouselStyle, carouselStyleRef] = useStateRef({
            transform: 'translate3d(0px, 0, 0)',
            transitionDuration: '0ms',
            nodeAttr: '0px'
        })
        const count = React.Children.count(children)

        let carouselItems: any = []

        // 获取carousel
        const carouselRef = useCallback((node) => {
            if (node !== null) {
                setNode(node)
            }
        }, [])
        // 移动方法
        const translate = (distance: number, selected: number) => {
            setCarouselStyle({
                ...carouselStyleRef.current,
                ...{
                    transform: `translate3d(${distance}px, 0px, 0)`,
                    transitionDuration: duration + 'ms'
                }
            })
            setTimeout(() => {
                onChange(selected, activeRef.current)
                setActive(selected)
            }, duration)
        }
        // 跳转到某一页
        const moveTo = (index: number) => {
            const distance = -index * itemWidth
            translate(distance, index)
        }
        useImperativeHandle(ref, () => ({
            goTo: (index: number) => {
                const target = index >= count ? count - 1 : index
                moveTo(target)
            }
        }))
        const [interValFn, setInterValFn] = useState<any>(null)
        const play = (dn?: number) => {
            if (interValFn) {
                return
            }
            let num = dn || 0
            setInterValFn(setInterval(() => {
                if (num < count - 1) {
                    num += 1
                } else {
                    num = 0
                }
                moveTo(num)
            }, autoPlay))
        }
        const stop = () => {
            clearInterval(interValFn)
            setInterValFn(null)
        }

        // init
        useEffect((): any => {
            if (node) {
                const measure = () => {
                    setItemWidth(node.getBoundingClientRect()['width'])
                }
                const handleResize = debounce(measure, 200)
                measure()
                window.addEventListener('resize', handleResize)
                return () => {
                    window.removeEventListener('resize', handleResize)
                    handleResize.cancel()
                }

            }
        }, [node])
        useEffect(() => {
            itemWidth && play()
        }, [itemWidth])

        useEffect(() => {
            const distance = - active * itemWidth
            setCarouselStyle({
                ...carouselStyle,
                ...{
                    transform: `translate3d(${distance}px, 0px , 0)`,
                    transitionDuration: '0ms'
                }
            })
        }, [itemWidth])

        const handleMouseOver = () => {
            stop()
        }
        const handleMouseOut = () => {
            play(active)
        }

        carouselItems = React.Children.map(
            children as any[],
            (item: React.ReactChild, index: number) => (
                <div
                    className={s['carousel-item']}
                    key={index + 1}
                    style={{ width: itemWidth + 'px' } as React.CSSProperties}
                >
                    {item}
                </div>
            )
        )
        const renderDots = () => {
            if (dots) return dots
            return (
                <div className={s['carousel-dots-wrap']}>
                    {Array(...Array(count)).map((item, key) => {
                        const isActive = key === active
                        const classes = classNames([s['carousel-dots-progress'], { [s['active']]: isActive }])
                        const duration = isActive ? autoPlay / 1000 : 0
                        const style = {
                            transition: `all ${duration}s`
                        }
                        return <div
                            key={key}
                            onClick={() => moveTo(key)}
                            className={classNames(s['carousel-dots'])}
                        >
                            <span className={classes} style={style}></span>
                        </div>
                    })}
                </div>
            )
        }

        return (
            <div
                className={s['carousel-wrap']}
                ref={ref as any}
                onMouseOver={handleMouseOver}
                onMouseOut={handleMouseOut}>
                <div
                    ref={carouselRef as any}
                    className={classNames(s['carousel'], className)}
                    style={style}>
                    <div
                        style={carouselStyle}
                        className={s['carousel-container']}
                        ref={container as any}
                    >
                        {carouselItems}
                    </div>
                    {showDots ? renderDots() : null}

                </div>
            </div>
        )
    }
)

export default Carousel
