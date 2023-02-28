import './index.css';
import { FunctionComponent, useEffect, Children, useRef, useState, useCallback, useLayoutEffect, useMemo } from "react";
import { CarouselProps } from "./types";
import Indicator from '../indicator'

const NOOP = () => { };

const smooth = `transform ${1000}ms ease`;
const smooth1 = `transform ${500}ms ease`;

const Index: FunctionComponent<CarouselProps> = (props) => {
    const {
        children,
        interval = 3000,
        showIndicators = true,
        onChange = NOOP,
    } = props;

    /* setInterval**/
    const timerRef = useRef<number | null>(null);
    const sliderRef = useRef<HTMLUListElement>(null)
    const [positions, setPositions] = useState(0)

    /**
     * count , children size
     */
    const count = useMemo(() => Children.count(children), [children])

    /**
    * next 
    * next children
    */
    const next = useCallback(() => {
        setPositions(prv => (prv + 1) % count)
    }, [count]);

    /** view change */
    useEffect(() => {
        if (children?.[positions]) {
            onChange && onChange(positions,children[positions])
        }
    }, [children, onChange, positions])

    useLayoutEffect(() => {
        if (sliderRef.current?.style) {
            if (positions === 0) {
                sliderRef.current.style.transition = smooth1;
                sliderRef.current.style.transform = 'translateX(0)'
            } else {
                sliderRef.current.style.transition = smooth;
                sliderRef.current.style.transform = `translateX(${-positions / count * 100}%)`;
            }
        }
    }, [count, positions])

    const startTimer = useCallback(() => {
        if (timerRef.current !== null) return;
        if (count > 1) {
            timerRef.current = window.setInterval(() => {
                next();
            }, interval);
        }
    }, [ count, interval, next]);

    /**clear timer */
    const clearTimer = () => {
        if(timerRef.current) {
            clearInterval(timerRef.current)
        }
    }

    /** start loop */
    useEffect(() => {
        startTimer()
        return ()=>clearTimer()
    }, [startTimer])


    const renderIndicators = useCallback(() => {
        if (showIndicators) {
            return <Indicator size={count} currentIndex={positions} speed={interval} />
        } else {
            return null
        }
    }, [count, interval, positions, showIndicators])

    const renderItem = (item: React.ReactNode) => {
        return item;
    }

    const renderItems = useCallback(() => {
        if (!children) return []
        return Children.map(children, (item, index) => {
            return (
                <li className='slide'>
                    {renderItem(item)}
                </li>
            )
        })
    }, [children])

    if (!children) return null;
    return (
        <div className='carousel-root' >
            <div className='carousel-slider'>
                {renderIndicators()}
                <div className='slider-wrapper'>
                    <ul className='slider' ref={sliderRef} style={{ width: `${count * 100}%` }} >
                        {renderItems()}
                    </ul>
                </div>

            </div>

        </div>
    )
};

export default Index;