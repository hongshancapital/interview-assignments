import React, {CSSProperties} from "react";
/**
 * @public
*/

export interface ICarouselDataItemProps {
    /**
     * title
     * @default ''
     */
    title?: string| React.ReactNode;
    /**
     * desc
     * @default ''
     */
    desc?: string | React.ReactNode;
    /**
     * bgc
     * @default '#000'
     */
    backgroundColor?: string;
    /**
     * font color
     * @default '#fff'
     */
    color?: string;
    /**
     * image
     */
    imgUrl?: string
}