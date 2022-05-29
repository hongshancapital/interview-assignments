import React from "react";
import { SlideProps } from '../components/Slide';

type ChildType = (arg: SlideProps) => JSX.Element;

export const userLayerChildren = (children: JSX.Element | JSX.Element[] | undefined, Child?: ChildType) => {
    // 按slide分组
    let contentGroup: JSX.Element[][] = [];
    if (Array.isArray(children)) {
        Array.prototype.forEach.call(children, (child: JSX.Element) => {
            let slideNum = child.props.slide;
            (contentGroup[slideNum] = contentGroup[slideNum] ?? []).push(child);
            if (child.props.slidePage) {
                contentGroup[slideNum] = [child];
            }
        });
    } else if (children) {
        let slideNum = children.props.slide;
        contentGroup[slideNum] = [children];
    }

    const renderChildren = contentGroup.map((elements, i) => {
        const slide = elements[0].props.slide;
        if (elements[0].props.slidePage) {
            return elements[0];
        }
        if (Child) {
            return <Child key={i} slide={slide}>{elements}</Child>;
        }
        return <div key={i} slide={slide}>{elements}</div>;
    });

    return renderChildren;
};
