import { Children, ReactElement, forwardRef, useEffect, useImperativeHandle, useMemo, useRef, useState } from "react";
import { isNumber, isUndefined } from "../util";
import useResizeObserver from "../../../hooks/useResizeObserver";
import useInterval from "../../../hooks/useInterval";
import { ForwardRefRenderFunction } from "react";

interface CarouselSliderProps {
    autoplay?: boolean,
    height?: number | string,
    transitionDuration?: number,
    children?: ReactElement<any> | ReactElement<any>[],
    interval?: number,
}
type DotInnerStyle = {
    transition?: string,
    width?: string
}
type RefMethods = {
    next: (animation?: boolean) => void,
    prev: (animation?: boolean) => void,
    goTo: (index: number, animation?: boolean) => void
}

const CarouselSlider: ForwardRefRenderFunction<RefMethods, CarouselSliderProps> = (props, ref) => {
    const { height = 400, autoplay = true, transitionDuration = 500, children } = props;
    let { interval = 3000 } = props;
    if (interval < transitionDuration) {
        interval = transitionDuration;
    }
    const [width, setWidth] = useState(0);
    const [index, setIndex] = useState(0);
    const [lazyIndex, setLazyIndex] = useState(-1);
    const [shouldTransition, setShouldTransition] = useState(true);
    const containerRef = useRef<HTMLDivElement | null>(null);

    const total = useMemo(() => Children.count(children), [children]);

    const trackStyle = {
        transform: `translate3d(-${lazyIndex * width}px, 0, 0)`,
        transition: `transform ${shouldTransition ? transitionDuration : 0}ms linear`,
    }

    const getDotInnerStyle = (dotIndex: number) => {
        const style: DotInnerStyle = {};

        if (dotIndex === index && autoplay) {
            style.transition = `width  ${interval}ms linear`;
        }
        if (dotIndex === lazyIndex) {
            style.width = `100%`;
        }

        return style;
    }

    const next = (animation?: boolean) => {
        goTo((index + 1) % total, animation);
    }

    const prev = (animation?: boolean) => {
        goTo((index + total - 1) % total, animation);
    }

    const goTo = (newIndex: number, animation?: boolean) => {
        if (newIndex === index) return;

        if (!isUndefined(animation)) {
            setShouldTransition(animation);
        }

        setIndex(newIndex);
    }

    useEffect(() => {
        setLazyIndex(index);
    }, [index]);

    useEffect(() => {
        // 必须手动触发一次重排，不然仍然会发生动画
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const h = containerRef.current?.offsetHeight;
        setShouldTransition(true);
    }, [lazyIndex])


    useInterval(() => {
        next();
    }, autoplay ? interval : null);

    useResizeObserver(containerRef, () => {
        const width = containerRef.current!.offsetWidth;
        setWidth(width);
    });

    useImperativeHandle(ref, () => ({
        next,
        prev,
        goTo
    }))

    return (
        <div className="carousel-slider__container" style={{ height: isNumber(height) ? `${height}px` : height }} ref={containerRef}>
            <div className="carousel-slider__track" style={trackStyle}>
                {children}
            </div>
            <div className="dots">
                {new Array(total).fill(0).map((_, index) => (
                    <span className="dot" key={index}>
                        <span className="dot-inner" style={getDotInnerStyle(index)}></span>
                    </span>
                ))}
            </div>
        </div>
    )
}

export default forwardRef(CarouselSlider);