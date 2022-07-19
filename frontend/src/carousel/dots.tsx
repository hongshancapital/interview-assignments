import React, { FC } from "react";
import CarouseCodes, { DotsProps } from "./type";

const Dots: FC<DotsProps> = ({
         duration = CarouseCodes.duration,
         count = 0,
         currIndex = 0,
         className = "",
         ...props
     }) => {
    const DotsArray = [];

    const goTo = (i: number): void => {
        props.goTo && props.goTo(i);
    };

    for (let i = 0; i < count; i++) {
        DotsArray.push(
            <li key={i} onClick={() => goTo(i)}>
        <span
            className={currIndex === i ? "slick-active" : ""}
            style={{ animationDuration: `${duration}ms` }}
        ></span>
            </li>
        );
    }

    return <ul className={`slick-dots ` + className}>{DotsArray}</ul>;
};

export default Dots;