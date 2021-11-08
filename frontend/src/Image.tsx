import React from 'react';

interface ImageProps {
    slider?: number;
    src: string;
    name: string;
    children?: JSX.Element[] | JSX.Element;
}

const Image = ({
    src,
    name,
    children,
}: ImageProps): JSX.Element => (
    <>
        {children}
        <img key={name} src={src} alt={name} />
    </>
);

export default Image;