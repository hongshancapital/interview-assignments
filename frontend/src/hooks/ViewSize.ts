import {useCallback, useEffect, useState} from 'react';

type ViewSize = {
    width: number;
    height: number;
}

function useViewSize(): ViewSize {
    const [viewSize, setViewSize] = useState<ViewSize>({
        width: window.innerWidth,
        height: window.innerHeight
    });

    const resizeHandler = useCallback(() => {
        setViewSize({
            width: window.innerWidth,
            height: window.innerHeight
        });
    }, []);

    useEffect(() => {
        window.addEventListener('resize', resizeHandler);

        return () => {
            window.removeEventListener('resize', resizeHandler);
        }
    }, [])

    return viewSize;
}

export default useViewSize;