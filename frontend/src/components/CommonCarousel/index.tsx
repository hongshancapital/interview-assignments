import React, { cloneElement, FC, useEffect, useState } from 'react';

import './style.css';

type Props = {
    time?: number;
    initIndex?: number;
};

const CommonCarousel: FC<Props> = (props) => {
    const { initIndex = 0, time = 2000 } = props;
    const [nowIndex, setNowIndex] = useState(initIndex);

    let timer: number;

    useEffect(() => {
        // eslint-disable-next-line react-hooks/exhaustive-deps
        timer = window.setTimeout(() => {
            setNowIndex((nowIndex + 1) % 3)
        }, time);

        return () => {
            if (timer) {
                clearTimeout(timer);
            }
        }
    });

    const onClickLine = (i: number) => {
        setNowIndex(i);
        document.getAnimations().forEach((animation) => {
            animation.cancel();
            animation.play()
        })
    }

    return (
        <div className='container'>
            {
                React.Children.map(props.children, (child) => {
                    if (!React.isValidElement(child)) {
                        return null;
                    }
                    return cloneElement(child, {
                        style: {
                            'transform': `translateX(-${nowIndex * 100}vw)`
                        }
                    })
                })
            }
            <div className='op'>
                {React.Children.map(props.children, (_, index) => {
                    return (<div
                        className='op-item'
                        onClick={() => onClickLine(index)}>
                        <div className='inside'
                            style={{
                                animationDuration: index === nowIndex ? `${time / 1000}s` : '0s',
                                backgroundColor: index === nowIndex ? '#ffffff' : '',
                            }}>

                        </div>
                    </div>)
                })}
            </div>
        </div>
    );
}

export default CommonCarousel;
export { default as HomeCarouselItems } from './items/HomeCarousel';