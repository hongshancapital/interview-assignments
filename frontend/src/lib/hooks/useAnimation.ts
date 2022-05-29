import React, { useEffect, useState, useRef, useMemo } from "react";
import { AnimationActions } from './useAnimator';


const animationsDefault = {
    entry: () => { },
    exit: () => { },
    display: () => { },
    idel: () => { },
};

export const useAnimation = (slide: number, animActions: AnimationActions = animationsDefault) => {
    const selfSlide = useRef(slide);
    const [animFrom, setFrom] = useState(0);
    const [animTo, setTo] = useState(0);
    const ref = useRef<HTMLElement | null>(null);
    const actions = useRef<AnimationActions>(animActions);

    useEffect(() => {
        if (ref.current) {
            const dom: HTMLElement = ref.current;
            if (selfSlide.current === 0) {
                actions.current.display(dom);
            } else {
                actions.current.idel(dom);
            }
        }
    }, []);

    useEffect(() => {
        if (ref.current) {
            const dom: HTMLElement = ref.current;
            if (selfSlide.current === animFrom) {
                actions.current.exit(dom);
            } else if (selfSlide.current === animTo) {
                actions.current.entry(dom);
            }
        }
    }, [animFrom, animTo]);

    return (dom: HTMLElement, from: number, to: number) => {
        ref.current = dom;
        setFrom(from);
        setTo(to);
    };
};