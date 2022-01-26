import React, {
  useEffect,
  useState,
  CSSProperties,
  useRef,
  Dispatch,
  useMemo,
} from "react";
import { classNames } from "../../utils";

export interface CommonProps {
  autoPlay?: boolean; // 是否自动轮播
  sliderPosition?: "left" | "right" | "top" | "bottom"; // 轮播进度条位置
  shape?: "rect" | "round"; // 轮播进度条形状
}

export interface SliderProps extends CommonProps {
  currentIndex: number;
  itemNum: number;
  setCurrentIndex: Dispatch<React.SetStateAction<number>>;
}

const Slider: React.FC<SliderProps> = (props) => {
  const { currentIndex, itemNum, setCurrentIndex, autoPlay } = props;
  const animaIdRef = useRef<number>();
  const itemNumRef = useRef<number>(0);
  const startTimeRef = useRef<number>(0);
  itemNumRef.current = itemNum;
  const sliderArr: undefined[] = useMemo(
    () => new Array(itemNum).fill(undefined),
    [itemNum]
  );

  const [width, setWidth] = useState<CSSProperties["width"] | undefined>();

  useEffect(() => {
    if (!autoPlay) return;
    const step: (timestamp: DOMHighResTimeStamp) => void = function (
      timestamp: number
    ) {
      if (startTimeRef.current === 0) {
        // 记录第一次执行时间戳
        startTimeRef.current = timestamp;
      }
      const elapsed = timestamp - startTimeRef.current;
      const newWidth = `${Math.min((elapsed / 3000) * 100, 100)}%`;
      setWidth(newWidth);
      if (elapsed < 3000) {
        animaIdRef.current = requestAnimationFrame(step);
      } else {
        // 切换
        let newCurrentIndex = currentIndex + 1;
        newCurrentIndex =
          newCurrentIndex >= itemNumRef.current ? 0 : newCurrentIndex;
        setCurrentIndex(newCurrentIndex);
      }
    };
    animaIdRef.current = requestAnimationFrame(step);
    return () => {
      // 清除动画帧
      setWidth(0);
      startTimeRef.current = 0;
      cancelAnimationFrame(animaIdRef.current as number);
    };
  }, [currentIndex, setCurrentIndex, autoPlay]);

  return (
    <ul className="slider-wrapper">
      {sliderArr.map((item, index: number) => (
        <li
          className={classNames("slider", {
            "slider-active": !autoPlay && index === currentIndex,
          })}
          key={index}
          onClick={() => setCurrentIndex(index)}
        >
          <span
            className="slider-bar"
            style={index === currentIndex ? { width } : undefined}
          ></span>
        </li>
      ))}
    </ul>
  );
};

Slider.defaultProps = {
  sliderPosition: "bottom",
  autoPlay: true,
  shape: "rect", // 圆环进度条（未完成）
};

export default Slider;
