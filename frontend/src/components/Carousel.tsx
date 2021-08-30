import React, {
    CSSProperties,
    FunctionComponent,
    forwardRef,
    useRef,
    useImperativeHandle,
    useState, ReactElement
} from "react";
import './Carousel.css'

type Position = 'top' | 'bottom'

interface CarouselProps {
    children: ReactElement[]
    indicatorPosition: Position
    styles: CSSProperties
    showIndicator: boolean
    infinite: boolean
    delay: number
    autoPlay: boolean
    prefixCls: string
}

interface RefProps {
    onPrev: () => void
    onNext: () => void
    goTo: (n: number) => void
    pause: () => void
    play: () => void
    element: HTMLDivElement | null
}

interface IndicatorProps extends  Pick<CarouselProps, 'indicatorPosition' | 'showIndicator' | 'prefixCls'> {
    current: number
    count: number
    delay: number
    enableIndicatorClick ?: boolean,
    handleJump: (to: number) => void
}
interface CarouselItemProps {
    active: boolean
    prefixCls: string
}
export const CarouselItem: FunctionComponent<CarouselItemProps> = (props) => {
    const { active, children, prefixCls } = props
    return <div
        className={`${prefixCls}carousel-item ${active ? 'active' : 'inactive'}`}
    >{children}</div>
}

export const CarouselIndicator: FunctionComponent<IndicatorProps> = (props) => {
    const {
        current,
        count,
        showIndicator,
        indicatorPosition,
        delay,
        enableIndicatorClick = false,
        handleJump,
        prefixCls
    } = props
    if (showIndicator) return <div className={`${prefixCls}indicator-wrapper ${indicatorPosition}`}>
        {
            new Array(count).fill(undefined).map((_, index) => (
                <div
                    className={`${prefixCls}indicator-item ${current === index ? 'active' : 'inactive' }`}
                    onClick={() => enableIndicatorClick && handleJump(index)}
                    style={{'--delay': `${delay}ms`} as CSSProperties}
                />
            ))
        }
    </div>
    return <></>
}
export const Carousel = forwardRef<HTMLDivElement, Partial<CarouselProps> & Pick<CarouselProps, 'children'>>(
    (props, ref) => {
    const {
        children,
        indicatorPosition = 'bottom',
        showIndicator = true,
        infinite = true,
        delay = 3000,
        autoPlay: _autoPlay = true,
        prefixCls = '__interview__'
    } = props
    const [autoPlay, setAutoPlay] = useState(_autoPlay)
    const wrapperRef = useRef<HTMLDivElement | null>(null)
    const [current, setCurrent] = React.useState(0)
    const timerRef = React.useRef<NodeJS.Timeout | null>(null)
    const onPrev = () => {
        if(current > 0 || infinite) {
            setCurrent(current - 1)
        }
    }

    const onNext = () => {
        if(current < children?.length || infinite) {
            setCurrent(current + 1)
        }
    }

    React.useEffect(() => {
        if(autoPlay) {
            timerRef.current = setTimeout(onNext, delay) as NodeJS.Timeout
        }
    }, [current, autoPlay])

    const goTo = (target: number) => {
        if(timerRef.current) {
            clearTimeout(timerRef.current as NodeJS.Timeout)
        }
        setCurrent(target)

    }

    const pause = React.useCallback(() => {
      setAutoPlay(false)
    }, [])


    const play= () => {
        setAutoPlay(true)
    }

    useImperativeHandle<unknown, RefProps>(ref, () => ({
        onPrev,
        onNext,
        goTo,
        pause,
        play,
        element: wrapperRef.current
    }))


    return <div className={`${prefixCls}item-wrapper`} ref={wrapperRef}>
        {
            [...children].map(((child, index) =>(
                <CarouselItem
                    prefixCls={prefixCls}
                    active={current%children.length === index}
                >
                    { child }
                </CarouselItem>
            )))
        }
        <CarouselIndicator
            prefixCls={prefixCls}
            enableIndicatorClick
            handleJump={goTo}
            delay={!autoPlay ? Infinity : delay }
            count={[...children].length}
            current={current%children.length}
            indicatorPosition={indicatorPosition}
            showIndicator={showIndicator}
        />
    </div>
})

export default Carousel
