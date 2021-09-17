import React, { FC, ReactElement, useEffect, useRef, useState } from "react";
import Progress from './progress'
import './assets/css/index.css'

const requireContext = require.context("./assets/img", true, /^\.\/.*\.png$/);
const _data = requireContext.keys().map(requireContext);

const Carousel: FC = (): ReactElement => {
    const [activeIndex, setActiveIndex] = useState(0);
    const [wraper, setWrap] = useState({
        left: - activeIndex * 1200 + "px",
        transition: `left 300ms linear 0ms`
    })
    const data = [..._data];
    const showData = [...data, data[0]];
    const warpRef = useRef<HTMLDivElement>(null);
    const options = { delay: 3000, speed: 300 };

    useEffect(() => {
        const autoTimer = setInterval(() => {
            autoMove();
        },
            options.delay)
        return () => {
            clearInterval(autoTimer);
        };
    }, [activeIndex]);

    //控制焦点图
    useEffect(() => {
        if (activeIndex === 0) {
            setWrap({
                left: 0 + "px",
                transition: `left 0ms linear 0ms`
            })
        }
        else {
            setWrap({
                left: - activeIndex * 1200 + "px",
                transition: `left 300ms linear 0ms`
            })
        }
    }, [activeIndex])

    const autoMove = () => {
        setActiveIndex(activeIndex => activeIndex + 1);
        if (activeIndex >= 3) {
            setActiveIndex(0);
            setActiveIndex(1);
        }
        let a = warpRef.current!.offsetLeft;
    };


    return (
        <div className="container">
            <div className="warp" ref={warpRef} style={wraper}>
                {
                    showData.map((item: any, index) => {
                        return (
                            <div className="slide" key={index}>
                                <div className="font"><h1>Apple</h1></div>
                                <div className="img"><img src={item.default} alt="test" /></div>
                            </div>
                        );
                    })
                }
            </div>
            <div className="pageNagation">
                {
                    data.map((item, index) => {
                        let tempIndex = activeIndex;
                        if (tempIndex >= 3) {
                            tempIndex = 0
                        }
                        return <Progress key={index} active={tempIndex === index ? true : false} />
                    })
                }
            </div>
        </div>
    )
}

export default Carousel