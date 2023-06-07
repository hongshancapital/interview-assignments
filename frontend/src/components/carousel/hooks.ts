import { useCallback, useEffect, useState } from "react";
import { SliderWidth } from './index.d';

/** 容器窗口变化hooks */
export const useContainerResize = (containerRef: React.RefObject<HTMLDivElement>):SliderWidth => {

    const [slideWidth, setSlideWidth] = useState<number>(0);

    const onWindowResize = useCallback(() => {
        const containerWidth = containerRef.current?.clientWidth;
        containerWidth && setSlideWidth(containerWidth);
    }, [containerRef])

    useEffect(() => {
        //监听窗口变化
        onWindowResize();
        window.addEventListener('resize', onWindowResize);
        return () => { window.removeEventListener('resize', onWindowResize) };
    }, [onWindowResize])

    return {
        slideWidth
    }
}
