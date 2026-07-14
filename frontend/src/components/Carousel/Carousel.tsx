import React, {useEffect, useRef, useState, cloneElement} from "react"
import ReactDOM from "react-dom";
import {Context} from "./context"
import CarouselItem, {CarouselItemProps} from "./CarouselItem"
import Slider from "./Slider"
import "./zCarousel.css"
import {TriggerEnum} from "./enums/triggerEnum"

export interface CarouselProps {
    children: any;
    height?: string | number;
    autoplay?: boolean;
    interval?: number;
    trigger?: string;
    style?: object;
    easing?: string;
    onChange?: (name: any, index: number) => void;
}

interface CarouselInterface extends React.FC<CarouselProps> {
    Item: typeof CarouselItem
}

const Carousel: CarouselInterface = (props: CarouselProps)  => {

    const {
        children = [],
        height = 300,
        autoplay = true,
        interval = 2000,
        trigger = TriggerEnum.Click,
        style = {},
        easing = 'linear',
        onChange = () => {}
    } = props

    const parentRef = useRef<HTMLDivElement | null>(null)
    const childrenRef = useRef<{id?: string}>({})

    const [activeIndex, setActiveIndex] = useState(0)

    const getChildren = () => children

    const parentInfo = () => ({
        width: parentRef.current?.offsetWidth || 0
    })

    const onClickSlider = (sliderName: any, sliderIndex: number = 0) => {
        setActiveIndex(sliderIndex)
        const ins = childrenRef.current
        Object.keys(ins).forEach(name => {
            // @ts-ignore
            const instance = ins[name]
            const index = children.findIndex((child: { props: { name: string; }; }) => child.props.name === name)
            instance?.setTransformX(parentInfo().width * (Number(index) - sliderIndex))
        })
        onChange(sliderName, sliderIndex)
    }

    const register = (content: {name: any, setTransformX: () => void}) => {
        childrenRef.current = {...childrenRef.current, [content.name]: {setTransformX: content.setTransformX}}
    }

    const unregister = (id: any) => {
        // @ts-ignore
        childrenRef.current[id] = null
    }

    useEffect(() => {
        if (children.length) {
            onClickSlider(children[0].props.name, 0)
        }
    }, [children])

    // @ts-ignore
    return <Context.Provider value={{register, unregister, getChildren, parentInfo}}>
        <div className={'z-carousel'} style={{...style}} ref={parentRef}>
            <div
                className='z-carousel__view'
                style={{height}}
            >
                {
                    children.map((child: React.DetailedReactHTMLElement<React.HTMLAttributes<HTMLElement>, HTMLElement>) => {
                        return cloneElement(child, {style: {transitionTimingFunction: easing}})
                    })
                }
            </div>

            <Slider
                onChange={onClickSlider}
                items={children}
                activeIndex={activeIndex}
                interval={interval}
                trigger={trigger}
                autoplay={autoplay}
            />


        </div>
    </Context.Provider>
}

Carousel.Item = CarouselItem

export default Carousel