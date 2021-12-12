import React from 'react';
import {omit, isArray, flatten} from 'lodash/fp';
import './Carousel.scss';
import Progress from './Progress';
import useSlider from './hooks/useSlider';
import Banner from './Banner';

interface CarouselProps {
    interval?: number;
    progressBgColor?: string;
    progressBarColor?: string;
    containerHeight?: string;
    progressPosition?: string;
    children?: any;
}

const Carousel = ({
    interval = 5000,
    children = [],
    ...props
}: CarouselProps) => {
    const contentList = isArray(children) ? flatten([...children]) : [children];
    const slider = useSlider(contentList.length, interval);
    const {containerHeight = '650px'} = props;

    return (
        <div
            className="container"
            style={{
                height: containerHeight,
            }}
        >
            <Banner
                slider={slider}
                total={contentList.length}
                children={children}
            />
            <Progress
                slider={slider}
                interval={interval}
                content={contentList}
                {...omit(['content', 'children'], props)}
            />
        </div>
    );
};

export default Carousel;