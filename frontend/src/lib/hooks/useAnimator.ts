import React, { useRef, useEffect } from 'react';
import { Animator, optionsType, Animation } from '../model/animator';

export { Animation };

export interface AnimationActions {
    entry: (dom: HTMLElement) => void;
    exit: (dom: HTMLElement) => void;
    display: (dom: HTMLElement) => void;
    idel: (dom: HTMLElement) => void;
};

export interface AnimationMethods extends AnimationActions {
    play: (dom: HTMLElement, anim: string) => void;
    reverse: () => void;
    toward: () => void;
}

export function useAnimator(options?: optionsType) {
    const animator = useRef<Animator>();

    useEffect(() => {
        if (!animator.current) {
            animator.current = new Animator(options);
        }
    }, []);

    const play = (dom: HTMLElement, anim: string): void => {
        animator.current?.play(dom, anim);
    };
    const entry = (dom: HTMLElement): void => {
        animator.current?.play(dom, 'entry');
    };
    const display = (dom: HTMLElement): void => {
        animator.current?.play(dom, 'display');
    };
    const exit = (dom: HTMLElement): void => {
        animator.current?.play(dom, 'exit');
    };
    const idel = (dom: HTMLElement): void => {
        animator.current?.play(dom, 'idel');
    };
    const reverse = (): void => {
        animator.current?.reverse();
    };
    const toward = (): void => {
        if (animator.current?.reversed) {
            animator.current?.reverse();
        }
    };

    return { play, entry, exit, display, idel, reverse, toward, animator };
}


export interface animationHooks {
    onAnimationStart: (event: AnimationEvent) => void;
    onAnimationEnd: (event: AnimationEvent) => void;
}