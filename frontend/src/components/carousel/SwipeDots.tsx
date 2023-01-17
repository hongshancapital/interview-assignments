import React from 'react';
import './index.css';

interface SwipeDotsProps {
    current: number;
    count: number;
    duration?: number
}

const SwipeDots: React.FC<SwipeDotsProps> = (props) => {
    const {current, count, duration = 3000} = props;

    if (count <= 1) {
        return null;
    }

    return (
        <div className='swipe__dots'>
            {
                new Array(count).fill(1).map((_, index) => {
                    const isCurrent = current === index;
                    return (
                        <div
                            className={`swipe__dot ${isCurrent ? 'swipe__dot--active' : ''}`}
                            key={index}><span className="progress"
                                              style={isCurrent ? {transitionDuration: `${duration / 1000}s`} : {}}></span>
                        </div>
                    )
                })
            }
        </div>
    )
}

export default SwipeDots;
