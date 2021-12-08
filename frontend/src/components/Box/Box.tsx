import React, { FC, useMemo } from "react";

import "./Box.scss";

export type WidthVariant = "m" | "l";
export type MaxWidthVariant = "m" | "l";
export type HeightVariant = "m" | "l";
export type MaxHeightVariant = "m" | "l";

export interface BoxProps {
    width?: WidthVariant;
    maxWidth?: MaxWidthVariant;
    height?: HeightVariant;
    maxHeight?: MaxHeightVariant;
}

const getWidthClass = (width: WidthVariant) => `box--width-${width}`;
const getMaxWidthClass = (maxWidth: MaxWidthVariant) =>
    `box--max-width-${maxWidth}`;
const getHeightClass = (height: HeightVariant) => `box--height-${height}`;
const getMaxHeightClass = (maxHeight: MaxHeightVariant) =>
    `box--max-height-${maxHeight}`;

export const Box: FC<BoxProps> = ({
    width,
    maxWidth,
    height,
    maxHeight,
    children,
}) => {
    const widthClass = useMemo(
        () => (width ? getWidthClass(width) : ""),
        [width]
    );
    const maxWidthClass = useMemo(
        () => (maxWidth ? getMaxWidthClass(maxWidth) : ""),
        [maxWidth]
    );
    const heightClass = useMemo(
        () => (height ? getHeightClass(height) : ""),
        [height]
    );
    const maxHeightClass = useMemo(
        () => (maxHeight ? getMaxHeightClass(maxHeight) : ""),
        [maxHeight]
    );

    return (
        <div
            className={`${widthClass} ${maxWidthClass} ${heightClass} ${maxHeightClass}`}
        >
            {children}
        </div>
    );
};

export default Box;
