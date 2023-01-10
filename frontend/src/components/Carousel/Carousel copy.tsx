import React, { CSSProperties, FC, ReactElement, useEffect, useMemo, useRef, useState } from "react"
import { ISlideProps, Slide } from './Slide'
import styles from './Carousel.module.scss'
import { useStep } from "./hooks";

interface ICarouselProps {
    children: ReactElement<ISlideProps> | Array<ReactElement<ISlideProps>>;
}

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

export const Carousel: FC<ICarouselProps> = ({children}) => {
    const eleRef = useRef<HTMLDivElement>(null);
    // const [step] = useStep(5000);
    const [current, setCurrent] = useState(start);
    const [tranStyle, setTranStyle] = useState<CSSProperties>();
    // const pagiList = useMemo(()=> {
    //     const count = React.Children.count(children);
    //     const eleList = [];
    //     for(let i=0; i<count; i++){
    //         eleList.
    //     }
    // }, [children]);

    useEffect(()=>{
        const val = current.value;
        const elm = eleRef.current!;
        if(elm.firstElementChild){
            const first = elm.firstElementChild.getBoundingClientRect();
            elm.style.justifyContent = val;
            const last = elm.firstElementChild.getBoundingClientRect();
            const deltaX = first.left - last.left;
            // setTranStyle({transform: `translateX(${deltaX})`});

            Array.from(elm.children).forEach((ele: Element)=>{
                // (ele as HTMLElement).style.transform = `translateX(${deltaX})`;
                elm.animate([{
                    transform: `translateX(${deltaX}px)`
                  }, {
                    transform: 'none'
                  }], {
                    duration: 1000,
                    easing: 'ease-in-out',
                    fill: 'both'
                  });
            });

        }

        const timer = setTimeout(()=>{
            console.log('current.next', current.next.value)
            setCurrent(current.next);
        }, 3000);

        return () => {
            clearTimeout(timer);
        };
    }, [current]);
    

    return <div className={styles.root}>
        <div ref={eleRef} className={styles.content}>{children}</div>
        <div className={styles.paginationRoot}>
            pagiList
        </div>
    </div>;
}