import React, { useState, useEffect } from 'react';

interface UseCurrIndexProps {
    defaultIndex?: number;
    indexMax: number;
    time: number;
}

interface UseCurrIndexReturn {
    index: number;
}

type UseCurrIndexType = (props: UseCurrIndexProps) => UseCurrIndexReturn

const useCurrIndex: UseCurrIndexType = (props: UseCurrIndexProps) => {
    const { defaultIndex = 0, indexMax, time } = props;
    const [index, setIndex] = useState(defaultIndex);

    // 滑动定时器
    useEffect(() => {
        const timer = setTimeout(() => {
            if (index < indexMax - 1) {
                setIndex(index + 1)
            } else {
                setIndex(0)
            }
        }, time)

        return () => {
            clearTimeout(timer)
        }
    }, [index, indexMax, time])

    return { index }
}

export default useCurrIndex
