import React, { useEffect, useState, useRef, useCallback } from "react"
import classNames from 'classnames';
import './index.css'

export interface CarouselProps {
    autoPlay?: boolean
    prefixCls?: string
    style?: React.CSSProperties;
    initial?: number
    // 是否受控
    current?: number
    // onChange
    onChange?: (current: number) => void;
    children?: React.ReactNode;
}

const CarouselContentContainer = (props: {
    totalCount: number
    current?: number
    children?: React.ReactNode;
    parentRef: any
}) => {
    const { current, children, parentRef } = props
    const [offset, setOffset] = useState(0)
    useEffect(() => {
        setOffset(-parentRef?.current?.offsetWidth * (current || 0) || 0)
    }, [parentRef, current])
    return <div className="carousel-content-container"
        style={{
            left: offset
        }}
    >
        {React.Children.toArray(children).map((child, index) => {
            return <div key={`carousel-item-container-${index}`} className="carousel-item-container">
                {child}
            </div>
        })}
    </div>
}

const Dots = (props: {
    totalCount: number
    onChange: (current: number) => void
    onAutoChange: () => void
    current?: number
    autoPlay?: boolean
}) => {
    const { totalCount, onChange, autoPlay = true, current, onAutoChange } = props
    const [st, setSt] = useState<any>(null)

    useEffect(() => {
        if (st) {
            clearInterval(st)
        }
        if (autoPlay) {
            let st = setInterval(() => {
                onAutoChange()
            }, 2000)
            setSt(st)
        }
        return () => {
            clearInterval(st)
            setSt(null)
        }
    }, [autoPlay])

    const subs = new Array(totalCount).fill('').map((_, index) => {
        const clz = classNames("carousel-dot", {
            [`active`]: current === index
        })

        return <div className={clz} key={`carousel-dot-${index}`} onClick={() => {
            onChange(index)
        }}>
            <div className="active-dot-process">
            </div>
        </div>
    })
    return <div className="carousel-dot-container">
        {subs}
    </div>
}


export const Carousel = (props: CarouselProps) => {
    const [current, setCurrent] = useState(props?.initial ? props.initial : 0)
    const containerRef = useRef(null)
    const totalCount = React.useMemo(() => React.Children.count(props.children), [props.children]);
    const className = classNames(props?.prefixCls, 'carousel')
    const onChange = useCallback((value: number) => {
        if (props.onChange) {
            props.onChange(value)
        } else {
            setCurrent(value)
        }
    }, [props.onChange])

    return <div className={className} ref={containerRef} style={props.style}>
        <CarouselContentContainer
            parentRef={containerRef}
            totalCount={totalCount}
            current={typeof props.current === 'number' ? props.current : current}
        >{props.children}</CarouselContentContainer>
        <Dots
            autoPlay={props.autoPlay}
            totalCount={totalCount}
            current={typeof props.current === 'number' ? props.current : current}
            onChange={onChange}
            onAutoChange={() => {
                setCurrent((_v) => {
                    const targetValue = _v + 1 < totalCount ? _v + 1 : 0
                    if(props.onChange) {
                        props.onChange(targetValue)
                    }
                    return targetValue
                })
                
            }}
        ></Dots>
    </div>
}
