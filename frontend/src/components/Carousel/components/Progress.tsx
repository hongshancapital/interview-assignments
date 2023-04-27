import React, { useState, useEffect, ReactElement } from "react";
import "./Progress.scss";

interface imagesProps {
    url: string;
    renderText: () => ReactElement;
  }

interface ProgresslProps {
    images: imagesProps[];
    activeIndex: number;
    interval: number;
}

/**
 * 将Progress抽离，降低父组件的render次数
 * @param param0 
 * @returns 
 */
const Progress: React.FC<ProgresslProps> = ({ images, activeIndex, interval }) => {
    const [progress, setProgress] = useState(0);
    useEffect(() => {
        const timeout = setInterval(() => {
            setProgress(prevProgress => {
                const newProgress = prevProgress + (100 / interval) * 31.5
                return newProgress >= 100 ? 100 : newProgress;
            });
            if (progress >= 100) {
                clearInterval(timeout);
            }
        }, 30);
        return () => clearInterval(timeout);
    }, [progress]);

    useEffect(() => {
        setProgress(0)
    }, [activeIndex])


    return <div className="progress-container">
        {
            images.map((item, index) => {
                return (
                    <div className="progress-bg" key={item.url}>
                        {
                            activeIndex === index && <div className="progress-bar" style={{ width: `${progress}%` }}></div>
                        }
                    </div>
                )
            })
        }
    </div>
}

export default Progress;