import React, { useState, useEffect, useRef } from "react";
import "./index.css"
interface ProgressBarProps {
    totalTime: number;
    onComplete: () => void;
    isStart: boolean
}

const ProgressBar: React.FC<ProgressBarProps> = ({ totalTime, onComplete, isStart }) => {
    const [elapsedTime, setElapsedTime] = useState<number>(0);
    const requestIdRef = useRef<number>(0);

    const animate = (timestamp: number, startTime: number) => {
        const progress = ((timestamp - startTime) / totalTime) * 100;
        if (progress < 100) {
            requestIdRef.current = requestAnimationFrame((timestamp) =>
                animate(timestamp, startTime)
            );
            setElapsedTime(progress);
        } else {
            setElapsedTime(100);
            onComplete();
        }
    };

    useEffect(() => {
        let startTime = 0;
        if (!isStart) return setElapsedTime(0)
        requestIdRef.current = requestAnimationFrame((timestamp) => {
            startTime = timestamp;
            animate(timestamp, startTime);
        });
        return () => {
            cancelAnimationFrame(requestIdRef.current);
        };
    }, [totalTime, onComplete]);

    return (
        <div className="progress-bar">
            <div className="progress" style={{ width: `${elapsedTime}%` }} />
        </div>
    );
};

export default ProgressBar;
