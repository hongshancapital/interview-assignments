import React, {
    FC,
    Children,
    ReactElement,
    useMemo,
    useLayoutEffect,
    useCallback,
    useState,
    useRef,
    useEffect
} from "react";
import './index.css';
import Slider from "./components/Slider";

interface CarouselProps {
    children: ReactElement[],
    duration?: number
}

const Carousel: FC<CarouselProps> = (props) => {
    const {children, duration = 5000} = props;
    const count = useMemo(() => Children.count(children), [children]);
    const [step, setStep] = useState<number>(0);
    const timer = useRef<any>(null);
    const wrapperRef = useRef<HTMLUListElement>(null);

    const nextPage = useCallback(() => {
        setStep(state => {
            if (state >= count - 1) {
                return 0
            } else {
                return state + 1
            }
        })
    }, [count])

    useLayoutEffect(() => {
        if (wrapperRef.current) {
            if (step === 0) {
                wrapperRef.current.style.transition = `transform ${1200}ms ease`;
                wrapperRef.current.style.transform = 'translateX(0)'
            } else {
                wrapperRef.current.style.transition = `transform ${700}ms ease`;
                wrapperRef.current.style.transform = `translateX(${-step / count * 100}%)`;
            }
        }
    }, [count, step]);

    const startLoop = useCallback(() => {
        if (!timer.current) {
            timer.current = setInterval(() => {
                nextPage();
            }, duration)
        }
    }, [timer, nextPage, duration])

    const renderCarouselWrapperItem = useCallback(() => {
        if (!children) {
            return []
        }
        return Children.map(children, (item) => <li className='carousel-wrapper-item'>{item}</li>)
    }, [children]);

    const renderCarouselSliderItem = useCallback(() => {
        return <Slider duration={duration} count={count} current={step}/>
    }, [duration, count, step])

    useEffect(() => {
        startLoop();
    }, [startLoop])

    return <div className='carousel-container'>
        <div className="carousel-wrapper">
            <ul ref={wrapperRef} style={{width: `${count * 100}%`}}>
                {renderCarouselWrapperItem()}
            </ul>
            <div className="carousel-slider">
                {renderCarouselSliderItem()}
            </div>
        </div>

    </div>
}

export default Carousel