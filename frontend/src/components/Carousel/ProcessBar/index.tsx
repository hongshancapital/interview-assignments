/*
 * @Description: 轮播过程条组件
 * @Version: 1.0
 * @Autor: zhangw
 * @Date: 2023-04-09 18:49:43
 * @LastEditors: zhangw
 * @LastEditTime: 2023-04-12 22:27:22
 */

import React  from "react";
import "./index.css";
import { BaseCompProps } from "../types";
import { useCarouselContext } from "../CarouselContext";
const ProcessBar = (props: BaseCompProps) => {
  const { style, className = '' } = props;
  const { current, itemCount } = useCarouselContext();
  const getBars = () => {
    let bars = [];
    for (let i = 0; i < itemCount; i++) {
      if (current === i) {
        bars.push(
          <div className="process-bar-item" key={`processBar_item_${i}`}  >
            <div className="process-bar-item-inner" />
          </div>
        );
      } else {
        bars.push(<div className="process-bar-item" key={`processBar_item_${i}`} />);
      }
    }
    return bars;
  };
  return <div className={`process-bar ${className}`} style={style}>{getBars()}</div>;
};
export default ProcessBar;
