import React, { memo, useEffect, useMemo, useRef, useState } from "react";
import './CarouselIndicator.scss';

const ProcessSlice = 0.02; //2%, which can modify the minimum unit increment of KeepTime
const KeepTime = 2000; //ms, the interval.
const Step = KeepTime*ProcessSlice;

interface IndicatorProps {
    sliceNumber: number;
    startCountDown: boolean;
    onFinished(index:number):void;
    onClick(index:number):void;
    processColor?: {
        backgroundColor: string;
        processColor: string;
    } | null;
}

/**
 * Indicator is the Carousel operate comopnent which shown and decide the current slide index or should paused and restart the scroll.
 * Indicator processColor is configured throuel processCollor configuration.
 */
const CarouselIndicator: React.FC<IndicatorProps> = ({sliceNumber, startCountDown, onFinished, onClick, processColor}) => {
    const intervalRef:{ current: NodeJS.Timer | null } = useRef(null);  // Keep the timer reference which can be used to clear the timer.
    const timerCallbackRef = useRef<Function>();    // The timerCallbackRef is used to save the newest reference of timerCallback funciton of current life cycle 
    const processRef = useRef<HTMLDivElement>(null);
    const containerRef = useRef<HTMLDivElement>(null);
    const [costed, setCosted] = useState(0);
    const [pauseTimer, setPauseTimer] = useState(false);    // PauseTimer is used to pause the process recalcuate which looks like the scrolling is paused.

    function updateProcess():void {
        if(pauseTimer) return;
        const curCosted = costed + Step;
        const curProcess = curCosted * 100/KeepTime;
        setCosted(curCosted);
        renderProcess(curProcess);
    }

    function renderProcess(process: number): void {
        if(!processRef.current) return;

        let value = process;
        if(Math.ceil(process) >= 100) {
            timeout();
        }

        processRef.current.style.width = String(value) + "%";
    }

    function timeout():void {
        reset();
        onFinished(sliceNumber);
    }

    function reset() {
        setCosted(0);
        renderProcess(0);
    }

    function indecatorClicked() {
        setPauseTimer(true);
        onClick(sliceNumber)
    }

    function onMouseOut() {
        setPauseTimer(false);
    }
    
    useEffect(() => {
        intervalRef.current && clearInterval(intervalRef.current);
        if(startCountDown) {
            intervalRef.current = setInterval(() => {
                if(timerCallbackRef.current) timerCallbackRef.current();
            }, Step);
        } else {
            reset();
        }
        
        return(() => {
            clearInterval(intervalRef.current as NodeJS.Timer);
        })
    }, [startCountDown])

    useEffect(() => {
        timerCallbackRef.current = updateProcess;
    }, [updateProcess])
    
    useEffect(() => {
        if(processColor && processRef.current && containerRef.current) {
            containerRef.current.style.backgroundColor = processColor.backgroundColor;
            processRef.current.style.backgroundColor = processColor.processColor;
        }
    }, [processColor])

    return (
        <div className="carousel_indicator" onClick={indecatorClicked} onMouseOut={onMouseOut} ref={containerRef}>
            <div className="carousel_indicator_process" ref={processRef}></div>
        </div>
    )
}

export default memo(CarouselIndicator); // memo can forbidden the unused rerender.