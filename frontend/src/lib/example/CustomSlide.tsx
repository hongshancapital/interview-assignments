import React, { useEffect, useRef, useContext } from 'react';
import { SlideProps } from '../components/Slide';
import { useAnimation } from '../hooks/useAnimation';
import { useAnimationHooks, AnimationHooks } from '../hooks/useAnimationHooks';
import { LayerContext } from '../hooks/useLayerContext';

export default function CustomSlide({ slide, children }: SlideProps) {
    const selfRef = useRef(null);
    const { layer, from, to, animations } = useContext(LayerContext);

    const updateAnimiton = useAnimation(slide, animations);
    useEffect(() => {
        if (selfRef.current != null && from !== undefined && to !== undefined) {
            const dom = selfRef.current as HTMLElement;
            updateAnimiton(dom, from, to);
        }
    }, [from, to]);

    const hooks: AnimationHooks = {
        onAnimationStart(event) { },
        onAnimationEnd(event) { }
    };
    const updateHooks = useAnimationHooks(animations);
    useEffect(() => {
        if (selfRef.current != null) {
            const dom = selfRef.current as HTMLElement;
            hooks.onAnimationStart = (event: AnimationEvent) => {
            };
            hooks.onAnimationEnd = (event: AnimationEvent) => {
            };
            updateHooks(dom, hooks);
        }
    }, []);

    return <div data-layer={layer} data-slide={slide} ref={selfRef}>{children}</div>;
}