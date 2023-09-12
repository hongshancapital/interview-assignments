/*
 * @Description: 轮播组件全局context
 * @Version: 1.0
 * @Autor: zhangw
 * @Date: 2023-04-07 18:11:39
 * @LastEditors: zhangw
 * @LastEditTime: 2023-04-10 23:40:37
 */

import React, { useContext } from "react";
import { CarouselContextProps } from "../types";
const _context = React.createContext<CarouselContextProps>({
    isFlip: false,
    itemCount: 0,
    current: 0,
    animationSecends: 0.8
})
export const useCarouselContext = () => {
    return useContext(_context);
};
export default _context;