import React, { useCallback, useMemo, useState } from "react";
import "./index.css"
import pick from "@/utils/pick"
import useControlled from "@/hooks/useControlled"
import useInterval, { EachInterval } from "@/hooks/useInterval"
export type CarouselOnChange = (index: number) => void


export type CarouselPorps = {

    /**current index */
    value: number,

    /**change callback funciton*/
    onChange: CarouselOnChange,
    
    /**autoplay*/
    autoplay?: boolean,

    /**whether to disable dot */
    disableDots?: boolean

    /**ani duration(ms) */
    duration?: number

    /**children required */
   
} & Required<{ children: React.ReactNode | React.ReactNode[] }>

const Carousel: ReactFCStyle<CarouselPorps> = (props) => {
    const across = pick(['style'])(props)
    /** ani lock */
    const [isInTransition, isInTransitionSet]=useState(false)

    const { onChange, value, autoplay, children, disableDots, duration } = props
   
    const onTransitionEnd: React.DOMAttributes<HTMLDivElement>['onTransitionEnd'] =(event)=>{
        isInTransitionSet(false)
    }
    

    /**memo duration */
    const durationMemo = useMemo(() => duration??2000, [duration])

    /**click dot func */
    const handleClickDot=(index:number)=>{
        onChange(index)
    }
    /** offset distance (vw) */
    const contentOffset = useMemo(() => {
        return `calc(${-1 * (value ?? 0)} * 100vw)`
    }, [value])

    /** make component controlled*/
    useControlled(value, onChange, console.log)

    /** recall every  Interval  */
    const eachInterval: EachInterval = useCallback((next) => {
        if (isInTransition){
            return next()
        }
        if (value + 2 > (children as React.ReactNode[]).length) {
            onChange(0)
            isInTransitionSet(true)
            return next()
        }
        isInTransitionSet(true)
        onChange(value + 1)
        return next()
    }, [children, value, onChange,isInTransition])

    /** recall in cycles at regular intervals*/
    useInterval(durationMemo, {
        enable: autoplay,
        each: eachInterval
    })

    return <div {...across} className="carousel-container">
        <div className="carousel-content" style={{ left: contentOffset }} onTransitionEnd={onTransitionEnd}>
            {(children => {
                if (Array.isArray(children)) {
                    return children.map((child, index) => {
                        return <CarouselChild key={index} className="carousel-child">{child}</CarouselChild>
                    })
                }
                return children
            })(children)}
        </div>
        {/* <p style={{color:'red',position:'fixed',top:"10px",left:"10px",zIndex:"999"}}>  {isInTransition ? "过度中" : "平静"}</p> */}
        {!disableDots&& <div className="carousel-dot-container">
            {(children => {
                if (Array.isArray(children)) {
                    return children.map((child, index) => {
                        return <CarouselDot key={index} active={index===value} onClick={()=>handleClickDot(index)}></CarouselDot>
                    })
                }
                return children
            })(children)}
        </div>}
    </div>
}
const CarouselChild: ReactFCStyle<{}> = (props) => {
    const across = pick(['style', 'className'])(props)
    return <div {...across}>
        {props.children}
    </div>
}

const CarouselDot: ReactFCStyle<{ active:boolean,onClick?:()=>void }> = (props) => {
    const across = pick(['style', 'className','onClick'])(props)
    return <div {...across} className={props.active ? 'carousel-dot-item carousel-dot-item-active' :"carousel-dot-item"}/>
}

export default Carousel