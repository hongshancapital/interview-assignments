import {HAS_CAF, HAS_RAF} from "./const";

interface Timer {
    id: number | NodeJS.Timer;
}

export function setRafInterval(cb: () => void, delay: number = 0): Timer {
    if (HAS_RAF) {
        return {
            id: setInterval(cb, delay),
        };
    }
    let startTime = new Date().getTime();
    const timer: Timer = {
        id: 0,
    };
    const loop = () => {
        const currentTime = new Date().getTime();
        if (currentTime - startTime >= delay) {
            cb();
            startTime = new Date().getTime();
        }
        timer.id = requestAnimationFrame(loop);
    };
    timer.id = requestAnimationFrame(loop);
    return timer;
}

export function clearRafInterval(timer: Timer) {
    if (HAS_CAF) {
        return clearInterval(timer.id as NodeJS.Timer);
    }
    cancelAnimationFrame(timer.id as number);
}
