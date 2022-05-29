import React, { useEffect, useMemo, useRef, useState } from 'react';
import { AnimationActions } from './useAnimator';
import { Animation } from '../model/animator';

const animationsDefault = {
    entry: () => { },
    exit: () => { },
    display: () => { },
    idel: () => { },
};

export interface AnimationHooks {
    onAnimationStart: ((event: AnimationEvent) => void) | undefined;
    onAnimationEnd: ((event: AnimationEvent) => void) | undefined;
}

export const useAnimationHooks = (animActions: AnimationActions = animationsDefault) => {
    const [updateHooks, setUpdateHooks] = useState(0);
    const ref = useRef<HTMLElement | null>(null);
    const hooks = useRef<AnimationHooks | null>(null);
    const actions = useRef<AnimationActions>(animActions);
    useMemo(() => {
        if (ref.current) {
            const dom: HTMLElement = ref.current;
            const animationStart = (event: AnimationEvent) => {
                hooks.current?.onAnimationStart?.(event);
            };
            const animationEnd = (event: AnimationEvent) => {
                hooks.current?.onAnimationEnd?.(event);
                if (dom.dataset.carouserState === Animation.entry) {
                    if (dom.dataset.carouserReversed === '1') {
                        actions.current.idel(dom);
                    } else {
                        actions.current.display(dom);
                    }
                } else if (dom.dataset.carouserState === Animation.exit) {
                    if (dom.dataset.carouserReversed === '1') {
                        actions.current.display(dom);
                    } else {
                        actions.current.idel(dom);
                    }
                }
            };
            dom.addEventListener('animationstart', animationStart);
            dom.addEventListener('animationend', animationEnd);
            return () => {
                dom.removeEventListener('animationstart', animationStart);
                dom.removeEventListener('animationend', animationEnd);
            };
        }
    }, [updateHooks]);
    return (domRef: HTMLElement, slideHooks: AnimationHooks) => {
        ref.current = domRef;
        hooks.current = slideHooks;
        setUpdateHooks(store => store + 1);
    };
};