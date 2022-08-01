import React, {
  FC,
  memo,
  useState,
  useRef,
  useEffect,
  useCallback,
} from "react";

import "./carousel.scss";

interface Props {
  autoPlay?: boolean; // 自动切换 默认为true
  interval?: number; // 自动切换的时间间隔 默认3000毫秒
}

interface DotProps {
  count: number;
  currentIndex: number;
}
const DotList: FC<DotProps> = memo((props) => {
  const { count, currentIndex } = props;
  const dotClass = useCallback(
    (index: number) => {
      return "dot-item " + (index === Math.abs(currentIndex) ? "active" : "");
    },
    [currentIndex]
  );

  return (
    <div className="dot-list">
      {new Array(count).fill(1).map((item, index) => {
        return (
          <div className={dotClass(index)} key={index}>
            <div className="bar"></div>
          </div>
        );
      })}
    </div>
  );
});

const Carousel: FC<Props> = memo((props) => {
  const { autoPlay = true, interval = 3000 } = props;

  // 容器宽度
  const [offsetWidth, setOffsetWidth] = useState(0);
  // 当前轮播图下标
  const [currentIndex, setCurrentIndex] = useState(0);
  // 子元素个数
  const [itemCount] = useState(React.Children.count(props.children));

  // 位移距离
  const [translateX, setTranslateX] = useState(0);

  const listWrapper = useRef<HTMLDivElement | null>(null);
  // 定时器
  let timer = useRef<NodeJS.Timer>();

  // 计算最新的移动位置
  const changeTranslate = useCallback(() => {
    let index = currentIndex - 1;
    if (Math.abs(index) === itemCount) {
      index = 0;
    }

    setCurrentIndex(index);

    let newTranslateX = index * offsetWidth;
    setTranslateX(newTranslateX);
  }, [currentIndex, itemCount, offsetWidth]);
  // 计算最新的移动位置 end

  // 获取容器元素宽度
  const getOffsetWidth = useCallback(() => {
    const offsetWidth = listWrapper.current?.offsetWidth || 0;
    setOffsetWidth(offsetWidth);
  }, []);

  useEffect(() => {
    getOffsetWidth();
    window.addEventListener("resize", getOffsetWidth);
    return () => {
      window.removeEventListener("resize", getOffsetWidth);
    };
  }, [getOffsetWidth]);
  // 获取容器元素宽度 end

  useEffect(() => {
    if (!autoPlay || !offsetWidth) {
      return;
    }
    timer.current = setInterval(() => {
      changeTranslate();
    }, interval);

    return () => clearInterval(timer.current);
  }, [autoPlay, changeTranslate, timer, offsetWidth, interval]);

  return (
    <div className="container">
      <div className="list-wrapper" ref={listWrapper}>
        <div
          className="list carousel-list"
          style={{ transform: `translateX(${translateX}px)` }}
        >
          {props.children}
        </div>
      </div>
      <DotList count={itemCount} currentIndex={currentIndex}></DotList>
    </div>
  );
});

export default Carousel;
