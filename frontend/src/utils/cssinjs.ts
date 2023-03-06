
import type * as CSS from 'csstype';

declare const SKIP_CHECK = "_skip_check_";
declare class Keyframes {
    private name;
    style: CSSInterpolation;
    constructor(name: string, style: CSSInterpolation);
    getName(hashId?: string): string;
    _keyframe: boolean;
}
declare type ArrayCSSInterpolation = CSSInterpolation[];

export declare type InterpolationPrimitive = null | undefined | boolean | number | string | CSSObject;
export declare type CSSInterpolation = InterpolationPrimitive | ArrayCSSInterpolation | Keyframes;

export declare type CSSProperties = Omit<CSS.PropertiesFallback<number | string>, 'animationName'> & {
    animationName?: CSS.PropertiesFallback<number | string>['animationName'] | Keyframes;
};
export declare type CSSPropertiesWithMultiValues = {
    [K in keyof CSSProperties]: CSSProperties[K] | Extract<CSSProperties[K], string>[] | {
        [SKIP_CHECK]: boolean;
        value: CSSProperties[K] | Extract<CSSProperties[K], string>[];
    };
};
export declare type CSSPseudos = {
    [K in CSS.Pseudos]?: CSSObject;
};
export declare type CSSOthersObject = Record<string, CSSInterpolation>;
export interface CSSObject extends CSSPropertiesWithMultiValues, CSSPseudos, CSSOthersObject {
}
export type { CSSObject }