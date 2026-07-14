import React, {useEffect, useContext, useRef, forwardRef, useImperativeHandle, ReactElement, useState} from "react"
import {Context} from "./context"

export interface CarouselItemProps {
    style?: React.CSSProperties;
    className?: string;
    children?: any;
    ref?: any;
    name: any;
}

const CarouselItem: React.FC<CarouselItemProps> = forwardRef((props: CarouselItemProps, ref) => {

    const {children, name} = props
    const [transformX, setTransformX] = useState(0)

    useImperativeHandle(ref, () => {
        return {
            name,
            setTransformX
        }
    })

    return <div
        className={'z-carousel__item'}
        style={{transform: `translateX(${transformX}px)`}}
    >
        {children}
    </div>
})

export default (props: CarouselItemProps) => {
    const itemRef = useRef<ReactElement | null>(null)
    const {register, unregister, parentInfo} = useContext(Context)

    useEffect(() => {
        if (itemRef.current) {
            register(itemRef.current)
            return unregister(itemRef.current)
        }
    }, [itemRef.current])
    return <CarouselItem {...props} ref={itemRef}/>
}