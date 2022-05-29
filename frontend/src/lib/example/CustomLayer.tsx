import React, { useRef, useContext } from 'react';
import { userLayerChildren } from '../hooks/useLayerChildren';
import { CarouselContext } from '../hooks/useCarouselContext';
import { useLayerContext } from '../hooks/useLayerContext';
import { LayerProps } from '../components/Layer';

export default function ControllerLayer({ layer, children, className }: LayerProps) {
    const selfRef = useRef(null);
    const childNodes = userLayerChildren(children);
    const carouselContext = useContext(CarouselContext);
    console.log("🚀 ~ file: CustomLayer.tsx ~ line 11 ~ ControllerLayer ~ carouselContext", carouselContext);
    const contextRender = useLayerContext({});
    return (
        contextRender(<>{<div className={className + ' carousel-layer'} data-layer={layer} ref={selfRef}>{childNodes}</div>}</>)
    );
}
