/*
 * @Author: longsiliang longsiliang@sogou-inc.com
 * @Date: 2022-06-10 19:27:19
 * @LastEditors: longsiliang longsiliang@sogou-inc.com
 * @LastEditTime: 2022-06-10 20:53:15
 * @FilePath: /interview-assignments/frontend/src/components/carousel/use-carousel.ts
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import { useEffect, useState } from "react";

import { useEvent, useInterval } from '../../hooks';

import type { CSSProperties } from "react";

const animationTime = 300;

export const useCarousel = (count: number, _interval: number | null) => {
    const [interval, changeInterval] = useState(_interval);
    const [current, setCurrent] = useState(0);

    useEffect(() => {
        changeInterval(_interval);
    }, [_interval]);


    const total = count + 1;

    useInterval(() => {
        const next = current + 1;
        setCurrent(next);
        if (next + 1 === total) {
            setTimeout(() => {
                setCurrent(0);
            }, animationTime);

        }
    }, interval);

    const pause = useEvent(() => {
        changeInterval(null);
    });

    const play = useEvent(() => {
        changeInterval(_interval);
    });


    const trackStyle: CSSProperties = {
        width: `${total * 100}%`,
        transform: `translateX(-${100 * (current / total)}%)`,
        transition: current === 0 ? "none" : `transform ${animationTime}ms ease`,
    };

    const slideStyle: CSSProperties = { width: `${100 / total}%` };

    return {
        carouselGoTo: setCurrent,
        play,
        pause,
        current: current + 1 === total ? 0 : current,
        trackStyle,
        slideStyle,
    };
};