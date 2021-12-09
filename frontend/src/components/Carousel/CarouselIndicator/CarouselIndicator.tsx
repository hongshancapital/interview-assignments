import React, { FC, useState, useEffect } from "react";

import "./CarouselIndicator.css";

const INITIAL_PROGRESS = 0;
const MAXIMUM_PROGRESS = 100;
const PROGRESS_REFRESH_INTERVAL = 10;

export interface CarouselIndicatorProps {
    active?: boolean;
    animationTime?: number;
}

const CarouselIndicator: FC<CarouselIndicatorProps> = ({
    active = false,
    animationTime = 1000,
}) => {
    const [progress, setProgress] = useState(INITIAL_PROGRESS);

    useEffect(() => {
        let interval: NodeJS.Timeout;

        const PROGRESS_INCREMENT =
            (PROGRESS_REFRESH_INTERVAL * MAXIMUM_PROGRESS) / animationTime;

        if (active) {
            interval = setInterval(() => {
                setProgress((progress) => {
                    const newProgress = progress + PROGRESS_INCREMENT;

                    if (newProgress >= MAXIMUM_PROGRESS) {
                        clearInterval(interval);
                    }
                    return newProgress;
                });
            }, PROGRESS_REFRESH_INTERVAL);
        }

        return () => {
            setProgress(INITIAL_PROGRESS);

            if (interval) {
                clearInterval(interval);
            }
        };
    }, [active, animationTime, setProgress]);

    return (
        <div className="casourel-indicator">
            <div
                className="casourel-indicator__progress"
                style={{
                    width: `${progress}%`,
                }}
            ></div>
        </div>
    );
};

export default CarouselIndicator;
