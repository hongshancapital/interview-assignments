import React, { ReactElement } from 'react';
import './index.scss';

interface SlidProps {
    title: string | ReactElement;
    imgSrc: string;
    theme?: string;
    background?: string;
    description?: string | ReactElement;
}

export default function SlidCard(props: SlidProps) {
    const background = props.background || '#fff';

    return <div className={'card-container ' + (props.theme || 'light')} style={{ background }}>
        <div className="text-body">
            <div className="title">{props.title}</div>
            {
                props.description &&
                <div className="description text">{props.description}</div>
            }
        </div>
        <div className="img-area">
            <img src={props.imgSrc} alt="product-image"/>
        </div>
    </div>
}
