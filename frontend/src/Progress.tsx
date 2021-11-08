import React, {useEffect, useState, useMemo, useCallback} from 'react';
import {ContentItemProps} from './Description';
import './Progress.scss';

interface ProgressProps {
    slider: number;
    interval: number;
    progressBgColor?: string;
    progressBarColor?: string;
    progressPosition?: string;
    content: JSX.Element[];
}

const Progress = ({
    slider,
    interval,
    content,
    progressBgColor = '#ddd',
    progressBarColor = '#fff',
    progressPosition = '30px',
}: ProgressProps) => {
    const [hasTransition, setHasTranstion] = useState<boolean>(false);

    useEffect(
        () => {
            setHasTranstion(true);
        },
        [slider, setHasTranstion]
    );

    const generateBar = useCallback(
        index => {
            if (content.length > 1) {
                return {
                    width: slider === index && hasTransition ? '100%': 0,
                    transitionDuration: slider === index ? `${interval}ms` : '0ms',
                    backgroundColor: progressBarColor,
                };
            }
            return {
                width: '100%',
                backgroundColor: progressBarColor,
            };
        },
        [hasTransition, content, interval, slider],
    );

    return (
        <ul 
            className="process"
            style={{
                bottom: progressPosition,
            }}
        >
            {content.map((item, index) => (
                <li
                    key={index}
                    className={`process-bar-${index + 1}`}
                    style={{
                        backgroundColor: progressBgColor,
                    }}
                >
                    <span style={generateBar(index)}></span>
                </li>
            ))}
        </ul>
    );
};

export default Progress;