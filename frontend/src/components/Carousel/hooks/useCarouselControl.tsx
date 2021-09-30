import React, { RefObject, useEffect, useMemo, useState } from "react";
import "./index.css";

const LOOP_INTERVAL = 4000;

export function useCarouselControl(ref: RefObject<HTMLDivElement>) {
    const [children, setChildren] = useState<Element[]>();
    const [loopIndex, setLoopIndex] = useState(0);

    useEffect(() => {
        const parsedChildren = Array.from(ref.current?.children || []);
        setChildren(parsedChildren);
    }, [ref]);

    // loop slide
    useEffect(() => {
        const loopHandler = () => {
            const index = ((loopIndex || 0) + 1) % (children?.length || 0);
            children?.[index].scrollIntoView({
                behavior: "smooth",
            });
            setLoopIndex(index);
        };

        const timerId = window.setInterval(loopHandler, LOOP_INTERVAL);
        return () => clearInterval(timerId);
    }, [children, loopIndex]);

    const Pagination = useMemo(
        () => () =>
            (
                <div className="pagination-container">
                    {children?.map((_, index) => {
                        return (
                            <div
                                className={`pagination-bar ${
                                    index === loopIndex ? "active" : ""
                                }`}
                                key={index}
                            >
                                <div className="progress"></div>
                            </div>
                        );
                    })}
                </div>
            ),
        [children, loopIndex]
    )();

    return [Pagination];
}
