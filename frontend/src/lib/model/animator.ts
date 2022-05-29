import { animations } from "./normal-animations";
/**
 * 动画机
 * 1. 生成动画
 * 2. 可以替换成其它动画引擎
 */
export interface Animator {
    [key: string]: any;
    id: string;
    generator(option: optionsType): void;
    play(dom: HTMLElement, state: string): void;
}

// 动画
export enum Animation {
    idel = 'idel',
    entry = 'entry',
    display = 'display',
    exit = 'exit'
}
type AnimationTypes = keyof typeof Animation;

// CSS animation properties
export interface CSSAnimationProperties {
    duration?: string;
    timingFunction?: string;
    delay?: string;
    iterationCount?: number;
    direction?: string;
    fillMode?: string;
    playState?: string;
    keyframes?: string;
}
export type optionsType = { [key in AnimationTypes]?: string | CSSAnimationProperties };

export class Animator {
    static styleSheetId = 'carouser-animations';
    private styleSheet: CSSStyleSheet | undefined;
    [Animation.idel] = 'visibility: hidden;';
    [Animation.entry] = null;
    [Animation.display] = null;
    [Animation.exit] = null;
    reversed: boolean = false;

    constructor(options?: optionsType, id?: string) {
        this.id = id ?? String(Math.trunc(Math.random() * Date.now()));
        // 生成动画style
        this.initStyleSheet(Animator.styleSheetId);
        this.generator({ ...animations, ...options } ?? animations);
    }

    private initStyleSheet(styleSheetId: string) {
        let style = document.getElementById(styleSheetId);
        if (!style) {
            style = document.createElement('style');
            style.id = styleSheetId;
            document.head.appendChild(style);
        }
        this.styleSheet = Array.prototype.find.call(document.styleSheets, styleSheet => styleSheet.ownerNode?.id === styleSheetId);
    }

    private camelTest = /[A-Z]+(?![a-z])|[A-Z]/g;
    private camelToKababCase(str: string): string {
        return str.replace(this.camelTest, ($, i) => (i ? "-" : "") + $.toLowerCase());
    }

    private mkCSSAnimation(options: CSSAnimationProperties): string {
        let anim = '';
        Object.entries(options).forEach(([prop, value]) => {
            anim += `animation-${this.camelToKababCase(prop)}:${typeof value === 'string' ? value.trim() : value};`;
        });
        return anim;
    }

    generator(options: optionsType) {
        Object.entries(options).forEach(([prop, value]) => {
            if (typeof value === 'string') {
                this[prop] = value;
            } else if (this.styleSheet) {
                const { keyframes, ...others } = value;
                const name = `${prop}_${this.id}`;
                const index = Array.prototype.findIndex.call(this.styleSheet.cssRules, rule => rule.name === name);
                if (index !== -1) {
                    this.styleSheet.deleteRule(index);
                }
                this.styleSheet?.insertRule(`@keyframes ${name} ${value.keyframes}`);
                this[prop] = this.mkCSSAnimation(others);
            }
        });
    }

    play(dom: HTMLElement, state: AnimationTypes) {
        let reverse = '';
        if (this.reversed) {
            state = state === Animation.entry
                ? Animation.exit
                : state === Animation.exit
                    ? Animation.entry
                    : state;
            reverse = 'animation-direction: reverse;';
        }
        // 多个dom使用相同的动画，状态要保持在dom上
        dom.dataset.carouserState = state;
        dom.dataset.carouserReversed = Number(this.reversed).toString();
        const anim = this[state] ?? '';
        dom.setAttribute('style', '');
        dom.setAttribute('style', anim + `animation-name:${state}_${this.id};` + reverse);
    }

    // entry / exit 反转
    reverse() {
        this.reversed = !this.reversed;
    }
}