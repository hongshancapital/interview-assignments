import React,{ useEffect, useRef, useCallback, useMemo, useState } from "react";
import Progress from "../Progress";
import './index.css';

interface ICarouselProps {
    children: React.ReactNode,
    circleTime?: Number,
    transitionTime?: Number,
    class: String
}
 
const Carousel = (props: ICarouselProps) => {
    const box = useRef(null);
    const timer = useRef(null);
    const index = useRef(1);
    const [childIndex, setChildInde] = useState(1);
    const createNewChildren = useCallback(() => {
        return [props.children[props.children.length - 1], ...props.children, props.children[0]]
    }, [props.children])
    const childrenLen = useMemo(() => {
        return props.children.length
    }, [props.children])
    const progressTime = useMemo(() => {
        return props.circleTime
    }, [props.circleTime])
    useEffect(() => {
        const item: React.ReactNode = box.current.children;
        let len: Number = item.length;
        box.current.style.width = `${len*100}%`;
        box.current.style.transform = `translateX(${-index.current/len*100}%)`;
        const delay = (wait) => {
            return new Promise(res => {
                setTimeout(() => {
                    res()
                }, wait)
            })
        }
        const delayFunc = async (func) => {
            await delay(props.transitionTime);
            func()
        }
        const initInterval = () => {
            const item: React.ReactNode = box.current.children;
            let circleTime: Number = props.circleTime || 2000;
            let transitionTime: Number = props.transitionTime || 500;
            let len: Number = item.length;
            timer.current = setInterval(() => {
                index.current++;
                delayFunc(() => {setChildInde(index => index+1)})
                box.current.style.transition = `${transitionTime}ms`;
                box.current.style.transform = `translateX(${-index.current/len*100}%)`;
                if(index.current === item.length - 1) {
                    setTimeout(() => {
                        index.current = 1
                        delayFunc(() => {setChildInde(1)})
                        box.current.style.transform = `translateX(${-index.current/len*100}%)`;
                        box.current.style.transition = `none`;
                    }, transitionTime);
                }
            }, circleTime)
        };
        initInterval();
        return () => {
            clearInterval(timer.current)
        }
    }, [props.circleTime, props.transitionTime])
    return (
        <div className={`wraper ${props.class}`}>
            <div 
                className="content" 
                ref={box}
            >
                {createNewChildren()};
            </div>
            <Progress
                class="progress_wraper"
                index={childIndex} 
                num={childrenLen} 
                progressTime={progressTime}
            />
        </div>
    )
}

export default React.memo(Carousel)