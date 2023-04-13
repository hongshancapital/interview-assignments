import React, { useCallback, useEffect, useRef, useState } from 'react';

const useCurrentIndex = (data: any[], initialIndex?: number) => {
    const dataRef = useRef(data);
    const [current, setCurrent] = useState(
        initialIndex === undefined
            ? -1
            : Math.min(data.length - 1, initialIndex)
    );
    const setIndex = useCallback(
        (updater: number | React.SetStateAction<number>) => {
            if (dataRef.current.length === 0) {
                return;
            }

            if (typeof updater === 'number') {
                setCurrent(updater % dataRef.current.length);
            } else {
                setCurrent((i) => {
                    const next = updater(i);

                    return next % dataRef.current.length;
                });
            }
        },
        []
    );

    useEffect(() => {
        let isRemoved = true;
        // CASE 1: current data exists => find its position and do update
        if (current >= 0) {
            let newPosition = data.findIndex(
                (item) => item === dataRef.current[current]
            );

            if (newPosition !== -1) {
                setCurrent(newPosition);
                isRemoved = false;
            }
        }

        // CASE 2: current data removed
        if (isRemoved) {
            // CASE 2.1: has sufficient data => keep current position
            if (current < data.length) {
                // keep it
            }
            // CASE 2.2: no enough data => go to head
            else {
                setCurrent(data.length > 0 ? 0 : -1);
            }
        }

        dataRef.current = data;
    }, [current, data]);

    return [current, setIndex] as const;
};

export default useCurrentIndex;
