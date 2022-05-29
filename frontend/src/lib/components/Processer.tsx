import React, { useContext, useEffect, useReducer, useRef, useState } from "react";
import { ControllerContext } from './ControllerLayer';

export default function Processer() {
    const ctrlContext = useContext(ControllerContext);
    const { length, slideAt, options, sideClickCount } = ctrlContext as { length: any; slideAt: any, options: any; sideClickCount: any; };

    const animRef = useRef(0);
    const getAnimName = () => {
        animRef.current++;
        return animRef.current % 2 === 0
            ? 'carousel-bottom-ctrl-process'
            : 'carousel-bottom-ctrl-process1';
    };

    const [childNodes, setChildNodes] = useState<JSX.Element[]>([]);
    useEffect(() => {
        if (length.current) {
            const list = [];
            for (let i = 0; i < length.current + 1; i++) {
                const animName = getAnimName();
                const styles = {
                    animation: slideAt === i ? `${animName} ${options.stayTime}ms linear running` : ''
                };
                list.push(
                    (<span key={i} className='ctrl-btn'>
                        <span className="process" style={styles}></span>
                    </span>)
                );
            }
            setChildNodes(list);
        }
    }, [slideAt, sideClickCount]);

    return (
        <>{childNodes}</>
    );
}