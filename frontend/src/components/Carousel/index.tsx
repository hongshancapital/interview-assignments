import React, { useState, useRef, useEffect } from "react";
import "./index.css";

interface AutoplayOptions {
    delay?: number;
}

interface CarouselProps {
    children?: React.ReactNode,
    autoplay?: AutoplayOptions | boolean
}

const Carousel: React.FunctionComponent<CarouselProps> = (props: CarouselProps) => {
    const $ref: any = useRef(null);
    const [dimensions, setDimensions] = useState(0);
    const [current, setCurrent] = useState(0);
    let children = React.Children.toArray(props.children);

    const getDimensions = () => ($ref.current.offsetWidth);

    useEffect(() => {
        const handleResize = () => {
            setDimensions(getDimensions());
        };
        if ($ref.current) {
            setDimensions(getDimensions());
        }
        window.addEventListener('resize', handleResize);
        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, [$ref]);

    useEffect(() => {
        let timer: NodeJS.Timeout | null = null;
        let children = React.Children.toArray(props.children);
        if (props.autoplay) {
            const delay = typeof props.autoplay === 'object' && props.autoplay.delay ? props.autoplay.delay : 3000;
            timer = setTimeout(() => {
                setCurrent(current + 1 >= children.length ? 0 : current + 1)
            }, delay);
        }
        return () => {
            if (timer) {
                clearTimeout(timer);
            }
        }
    }, [props.autoplay, current, props.children])

    const trackStyle = { width: dimensions * children.length, transform: `translate3d(-${dimensions * current}px, 0px, 0px)` };

    return (<>
        <div className="ui-carousel" ref={$ref}>
            <div className="slick-slider">
                <div className="slick-list">
                    <div className="slick-track" style={trackStyle}>
                        {
                            children.map((item, index) => (
                                <div className="slick-slide" key={index}>
                                    <div>
                                        <div style={{ width: dimensions }}>
                                            {item}
                                        </div>
                                    </div>
                                </div>
                            ))
                        }
                    </div>
                </div>
                <ul className="slick-dots">
                    {
                        children.map((item, index) => (
                            <li key={index} className={current === index ? 'slick-active' : ''}><button onClick={() => { setCurrent(index) }} /></li>
                        ))
                    }
                </ul>
            </div>
        </div>
    </>)
}

export default Carousel;
export { Carousel }