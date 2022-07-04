import classNames from 'classnames';
import React from 'react';
import './index.css';

export interface ImageContentProps {
    title: string | React.ReactNode;
    description?: string | React.ReactNode;
    image: string
    className?: string
    basicBackgroundColor?: string
    style?: React.CSSProperties;
}

export const ImageContent = (props: ImageContentProps) => {
    const clz = classNames(`image-content`, props.className)
    return <div className={clz} style={{
        backgroundColor: props?.basicBackgroundColor,
        ...(props?.style || {}),
    }}>
        <div className="image-content-title title">{props.title}</div>
        <div className="image-content-description text">{props.description}</div>
        <div className="image-content-image" style={{
            backgroundImage: `url(${props.image})`
        }}></div>
    </div>
}