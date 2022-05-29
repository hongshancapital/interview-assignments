import React, { useRef, useState, useContext, useEffect } from 'react';
import Slide from "./Slide";
import { Animator, optionsType } from '../model/animator';
import { useAnimator } from '../hooks/useAnimator';
import { useLayerContext } from '../hooks/useLayerContext';
import { userLayerChildren } from '../hooks/useLayerChildren';
import { CarouselContext } from '../hooks/useCarouselContext';

export interface LayerProps {
    layer: number;
    animation?: optionsType;
    children?: JSX.Element | JSX.Element[];
    className?: string;
};

export default function Layer({ layer, animation, children, className = '' }: LayerProps) {
    const [slideAt, setSlideAt] = useState(0);
    const lastAt = useRef<number>(0);
    const selfRef = useRef(null);
    const { entry, exit, display, idel, reverse, toward } = useAnimator(animation);
    const contextValue = {
        layer,
        from: lastAt.current,
        to: slideAt,
        animations: { entry, exit, display, idel }
    };
    const contextRender = useLayerContext(contextValue);

    const childNodes = userLayerChildren(children, Slide);

    const { expose, totalLength, setTotalLength } = useContext(CarouselContext);
    expose?.(layer, {
        setSlideAt: (goto: number) => {
            lastAt.current = slideAt;
            setSlideAt(goto);
        },
        reverse: () => {
            reverse();
        },
        toward: () => {
            toward();
        },
    }, childNodes.length);

    useEffect(() => {
        if (selfRef.current) {
            if (childNodes.length > (totalLength?.current ?? 0)) {
                setTotalLength?.(childNodes.length);
            }
        }
    }, []);

    return (
        contextRender(<>{<div className={className + ' carousel-layer'} data-layer={layer} ref={selfRef}>{childNodes}</div>}</>)
    );
}
