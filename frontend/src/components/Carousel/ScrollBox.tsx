import React from "react";
import classname from "classnames";
import scssStyle from "./ScrollBox.module.scss";
import type { ScrollBoxProps } from "../../type";

const ScrollBox = ({ currentInd, dataList }: ScrollBoxProps) => {
  const { innerWidth } = window;
  const leftStr = `-${innerWidth * currentInd}px`;

  return (
    <div className={scssStyle.item_box}>
      <div className={scssStyle.scroll_box} style={{ left: leftStr }}>
        {dataList.map((item, i) => (
          <div
            className={scssStyle.item}
            key={i}
            style={{
              backgroundImage: `url(${item.url})`,
            }}
          >
            <div className={scssStyle.text_box}>
              {item.title.map((ele, ind) => (
                <div
                  key={ele}
                  className={classname({ [scssStyle.title]: ind === 0 })}
                >
                  {ele}
                </div>
              ))}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ScrollBox;
