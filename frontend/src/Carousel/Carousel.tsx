import { useRef, useState, useEffect } from "react";
import "./Carousel.less";
import classnames from "classnames";
import classNames from "classnames";

function Carousel() {
  const intervalRef = useRef<NodeJS.Timeout>();
  const [count, setCount] = useState<number>(0);
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

  useEffect(() => {
    const timer: NodeJS.Timeout = setInterval(() => {
      setCount((count) => (count >= 2 ? 0 : count + 1));
    }, 2000);
    intervalRef.current = timer;

    return () => clearInterval(intervalRef.current);
  }, []);

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
          >
            <span className="inner"></span>
          </div>
        ))}
      </div>
    </div>
  );
}
export default Carousel;
