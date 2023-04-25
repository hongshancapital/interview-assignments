import { useState } from "react";
import useInterval from "./useInterval";
import "./Carousel.less";

import classNames from "classnames";

function Carousel() {
  const [count, setCount] = useState<number>(0);
  const [delay, setDelay] = useState(2000);
  const [isRunning, setIsRunning] = useState(true);
  const imgList = [
    {
      url: "iphone",
      textDom: (
        <div className="textBox">
          <div className="bigTitle colorWhite">xPhone</div>
          <div className="smallTitle colorWhite">
            Lots to love. Less to spend
          </div>
          <div className="smallTitle colorWhite">Starting at $399</div>
        </div>
      ),
    },
    {
      url: "tablet",
      textDom: (
        <div className="textBox">
          <div className="bigTitle colorBlack">Tablet</div>
          <div className="smallTitle colorBlack">
            Just the right amount of everything
          </div>
        </div>
      ),
    },
    {
      url: "airpods",
      textDom: (
        <div className="textBox">
          <div className="bigTitle colorBlack">
            Buy a Tablet or xPhone for college
          </div>
          <div className="bigTitle colorBlack">Get arPods</div>
        </div>
      ),
    },
  ];
  const screenWidth = window.screen.width;

  useInterval(
    () => {
      setCount((count) => (count >= 2 ? 0 : count + 1));
    },
    isRunning ? delay : null
  );

  const handleClick = (idx: number) => {
    setCount(idx);
  };

  return (
    <div className="viewBox">
      <div className="innerBox" style={{ left: `-${count * screenWidth}px` }}>
        {imgList.map((item) => (
          <div key={item.url} className={`imgItem ${item.url}Item`}>
            {item.textDom}
          </div>
        ))}
      </div>
      <div className="btnBox">
        {imgList.map((item, index) => (
          <div
            key={item.url}
            className={classNames(
              "btnItem",
              count === index ? "activeBtn" : null
            )}
            onClick={() => handleClick(index)}
          >
            <span className="inner"></span>
          </div>
        ))}
      </div>
    </div>
  );
}
export default Carousel;
