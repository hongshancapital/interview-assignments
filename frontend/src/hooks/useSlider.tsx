import {useState, useEffect} from 'react';

const useTimer = (callback: (diff: number) => void, interval: number) => {
    useEffect(
        () => {
            const startTime = new Date().getTime();
            const timerFun = () => {
                callback(new Date().getTime() - startTime);
                const timer = setTimeout(() => {
                    timerFun();
                    clearTimeout(timer);
                }, interval);
                return timer;
            };
            timerFun();
        },
        [],
    );
};

const useSlider = (total: number, interval: number): number => {
    const [slider, setSlider] = useState<number>(0);
    useTimer((diff: number) => {
        setSlider(Math.floor(diff / interval) % total);
    }, interval);
    return slider;
};

export default useSlider;