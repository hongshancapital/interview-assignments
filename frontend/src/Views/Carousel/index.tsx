import React, { FunctionComponent } from 'react';
import Slider from '../../Components/Slider/index';
import { config, params } from './config';
import './index.scss';

const Carousel: FunctionComponent = () => {
    const sliders: JSX.Element[] = config.map(({ title, text, imgSrc }: params, index: number) => {

        return (
            <div
                className={`slider slider_${index}`}
                style={{ backgroundImage: `url("${imgSrc}")` }}
                key={index}
            >
                <h1 className="title">{title}</h1>
                <p className="text">{text}</p>
            </div>
        );
    });

    return <Slider sliders={sliders} />;
}

export default Carousel;

