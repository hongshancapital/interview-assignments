import React, {CSSProperties} from "react";
/**
 * @public
*/

export interface IContainerProps {
    /**
     * 实际内容
     */
    children?: React.ReactNode;
    /**
     * className
     */
    className?: string;
    /**
     * style
     */
    style?: CSSProperties;
}