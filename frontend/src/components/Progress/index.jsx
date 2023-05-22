import React, { useLayoutEffect, useEffect, useMemo, useRef, useState } from 'react';
import './index.css';

interface IProgressProps {
    index: Number,
    num: Number,
    progressTime: Number,
    class: String
}

const Progress = ({progressTime = 2000, ...props}: IProgressProps) => {
    const box = useRef(null);
    const nowIndex = useMemo(() => {
        if(props.index < props.num + 1) {
            return props.index - 1
        }
        return 0
    }, [props.index, props.num])
    useLayoutEffect(() => {
        const style = document.createElement('style');
        const change = document.createTextNode(`
            .active::after {
                position: absolute;
                top: 0;
                left: 0;
                content: '';
                width: 0%;
                height: 100%;
                border-radius: 2px;
                background: #fff;
                animation: progress ${progressTime/1000}s linear;
            }
            
            @keyframes progress {
                0% {
                    width: 0%;
                }
            
                100% {
                    width: 100%;
                }
            }
        `)
        style.appendChild(change);
        document.body.appendChild(style);
    }, [progressTime])
    return (
        <div className={`${props.class} progress_content`} ref={box}>
            {
                new Array(props.num).fill(1).map((m, i) => {
                    return <div 
                                key={i} 
                                className={`progress_item ${nowIndex === i ? 'active' : ''}`}
                            ></div>
                })
            }
        </div>
    )
}

export default React.memo(Progress)