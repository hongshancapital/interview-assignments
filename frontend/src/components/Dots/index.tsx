import React from "react";

import './index.css';

export interface DotslProps {
    scrollIndex: number;
    dotNumber: number;
    autoplay?: boolean,
    delay?: number; // 自动播放间隔
    progressColor?: string;
    dotColor?: string;
    dotWidth?: string;
    dotHeight?: string;
    scrollHanlder?: (index: number) => void;
}

const defaultProps = {
    autoplay: true,
    delay: 3000,
    progressColor: '#fff',
    dotColor: 'rgb(168, 168, 168)',
    dotWidth: '100px',
    dotHeight: '4px'
}

const Dots: React.FC<DotslProps> = (_props) => {
    const props = {...defaultProps, ..._props};

    const clickHandle = (e: any, index: number) => {
        props.scrollHanlder!(index);
    }

    return (
        <div className='dots_box'>
            {
                Array(props.dotNumber).fill('').map((item, index) => index).map((item: any, index: number) => {
                    return (
                        <div 
                            className='dot_item' 
                            key={item} 
                            style={{width: props.dotWidth, height: props.dotHeight}}
                            onClick={(e) => clickHandle(e, index)}
                        >
                            <div
                                className='dot_bg'
                                style={{width: props.dotWidth, height: props.dotHeight, background: props.dotColor}}
                            >
                            </div>
                            <div
                                className={`dot_cover ${(props.autoplay && props.scrollIndex === index) && 'animationPlay'}`}  
                                style={{
                                    width: props.dotWidth,
                                    height: props.dotHeight,
                                    background: props.progressColor,
                                    animationDuration: `${props.delay}ms`,
                                }}
                            >
                            </div>
                            <div
                                className='dot_frame' 
                                style={{width: props.dotWidth, height: props.dotHeight}}
                            >
                            </div>
                        </div>
                    )
                })
            }
        </div>
    )
}

export default Dots;