import React, { useCallback, useMemo, useState } from "react";
import "./index.css";

export function useCarouselControl(
    length: number
): [JSX.Element, (index: number) => void] {
    const [paginationIndex, setPaginationIndex] = useState(0);

    const slideTo = useCallback((index: number) => {
        setPaginationIndex(index);
    }, []);

    const Pagination = useMemo(
        () => () =>
            (
                <div className="pagination-container">
                    {[...new Array(length)].map((_, index) => {
                        return (
                            <div
                                className={`pagination-bar ${
                                    index === paginationIndex ? "active" : ""
                                }`}
                                key={index}
                            >
                                <div className="progress"></div>
                            </div>
                        );
                    })}
                </div>
            ),
        [paginationIndex, length]
    )();

    return [Pagination, slideTo];
}
