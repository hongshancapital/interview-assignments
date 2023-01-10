import { useEffect, useState } from "react"

class Step {
    value: string;
    next: Step;
    pre: Step;

    constructor(value: string) {
        this.value = value;
        this.next = this;
        this.pre = this;
    }
}

const start = new Step('start');
const center = new Step('center');
const end = new Step('end');
start.next = center;
center.next = end;
end.next = start;
// start.pre = end;
// center.pre = start;
// end.pre = center;

export const useStep = (delay: number) => {
    const [current, setCurrent] = useState(start);
    useEffect(()=>{
        const timer = setTimeout(()=>{
            console.log('current.next', current.next.value)
            setCurrent(current.next);
            
        }, delay);

        return () => {
            clearTimeout(timer);
        };
    }, [current]);

    return [current];
}