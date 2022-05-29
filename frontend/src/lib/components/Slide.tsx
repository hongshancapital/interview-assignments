import React, { useRef, useEffect, useContext } from 'react';
import { useAnimationHooks, AnimationHooks } from '../hooks/useAnimationHooks';
import { useAnimation } from '../hooks/useAnimation';
import { LayerContext } from '../hooks/useLayerContext';

export interface SlideProps {
    slide: number;
    children?: JSX.Element | JSX.Element[];
    slidePage?: boolean;
};

export default function Slide({ slide, children }: SlideProps) {
    const selfRef = useRef(null);
    const { layer, from, to, animations } = useContext(LayerContext);

    let childList: (JSX.Element[] | undefined) = [];
    if (children && !Array.isArray(children)) {
        childList.push(children);
    } else {
        childList = children;
    }
    const hooks: AnimationHooks = {
        onAnimationStart(event) { },
        onAnimationEnd(event) { }
    };
    const updateHooks = useAnimationHooks(animations);
    useEffect(() => {
        if (selfRef.current != null) {
            const dom = selfRef.current as HTMLElement;
            setTimeout(() => {
                if (slide === 0) {
                    animations?.display(dom);
                } else {
                    animations?.idel(dom);
                }
            });
            hooks.onAnimationStart = (event: AnimationEvent) => {
                childList?.forEach(child => {
                    child.props.onAnimationStart?.(event, dom.dataset.carouserState, slide, layer);
                });
            };
            hooks.onAnimationEnd = (event: AnimationEvent) => {
                childList?.forEach(child => {
                    child.props.onAnimationEnd?.(event, dom.dataset.carouserState, slide, layer);
                });
            };
            updateHooks(dom, hooks);
        }
    }, []);

    const animPlay = useAnimation(slide, animations);
    useEffect(() => {
        if (selfRef.current != null && from !== undefined && to !== undefined) {
            const dom = selfRef.current as HTMLElement;
            animPlay(dom, from, to);
        }
    }, [from, to]);

    return <div className='carousel-slide' data-layer={layer} data-slide={slide} ref={selfRef}>{children}</div>;
}