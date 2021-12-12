import React, {Children} from 'react';

interface Props {
    slider: number;
    total: number;
    children: JSX.Element[] | JSX.Element;
}

const Banner = ({
    slider,
    total,
    children,
}: Props) => (
    <div 
        className="wrap"
        style={{
            width: `${total * 100}%`,
            transform: `translateX(-${100 * slider / total}%)`,
        }}
    >
        {Children.map(children, child => (
            <div 
                className="image-item"
                style={{
                    width: `${100 / total}%`,
                }}
            >{child}</div>))}
    </div>
);

export default Banner;