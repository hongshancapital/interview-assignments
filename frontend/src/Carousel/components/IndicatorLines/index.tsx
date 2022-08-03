import React from "react";
import './index.css';

interface IProps {
    active: boolean,
    duration: number,
}

export const IndicatorLines = ({ active, duration }: IProps) => {
    return (
        <div className='indicator-lines'>
            <span
                className={`${active ? 'cur' : ''}`}
                style={{ animationDuration: `${duration/1000}s`}}
            ></span>
        </div>
    );
};