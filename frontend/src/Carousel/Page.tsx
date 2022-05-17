import React, {FC, HTMLAttributes} from "react";
import s from "./index.module.css";

const Page: FC<HTMLAttributes<HTMLDivElement>> = (props) => {
    const {className, ...rest} = props;
    return <div className={`${s.page} ${className || ''}`} {...rest} />
}
Page.displayName = 'CarouselPage';

export default Page;