import React, {CSSProperties} from "react";
import { ICarouselDataItemProps } from './ICarouselDataItemProps.type copy'
/**
 * @public
*/

export interface ICarouselProps {
    /**
     * 实际内容
     */
    dataSource: ICarouselDataItemProps[];
    /**
     * className
     */
    className?: string;
    /**
     * style
     */
    style?: CSSProperties;
}