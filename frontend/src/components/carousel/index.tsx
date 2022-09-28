import React, { useState, useEffect, useRef, ReactNode } from "react";
import "./carousel.scss";
import Controls from "./controls";
import Indicator from "./indicator";
import { isEmpty, sliderSort } from "./tool";
import { Options } from "./options";

const Carousel = (props: Options) => {
  const [init] = useState<boolean>(!0);
  const [numo] = useState<number>(React.Children.count(props.children) || 0); // origin
  const [num, setNum] = useState<number>(numo || 0);
  const [width, setWidth] = useState<number>(props.width || 0); // 默认屏幕宽度
  const [height] = useState<number | string>(props.height || 300); // 默认300
  const [loop] = useState<boolean>(props.loop || isEmpty(props.loop)); // 默认循环
  const [indicator] = useState<boolean>(
    props.indicator || isEmpty(props.indicator)
  ); // 默认有指示器
  const [duration] = useState<number>(props.duration || 3000); // 默认3000ms
  const [autoplay] = useState<boolean>(
    props.autoplay || isEmpty(props.autoplay)
  ); // 默认自动播放
  const [play, setPlay] = useState<boolean>(false); // 播放
  const [controls] = useState<boolean>(
    props.controls || isEmpty(props.controls)
  ); // 默认含有方向按钮
  const [idx, setIdx] = useState<number>(0); // 当前帧
  const [idxs, setIdxs] = useState<Array<Array<number>>>(sliderSort(num)); // 排序
  const [flag, setFlag] = useState<number>(1); // 上次方向 1next -1prev
  const [items, setItems] = useState<Array<ReactNode>>([]); // children
  const [style, setStyle] = useState<object>({}); // style
  let el = useRef<HTMLDivElement>(null);
  let loopRef = useRef<any>(null);
  let id: any;
  loopRef.current = () => next();

  const prev = (): void => {
    if (idx === 0) setIdx(num - 1);
    else setIdx(idx - 1);
    setFlag(-1);
  };

  const next = (): void => {
    if (idx === num - 1) setIdx(0);
    else setIdx(idx + 1);
    setFlag(1);
  };

  const addEvents = (): void => {
    el.current?.addEventListener("pointerenter", () => {
      setPlay(!1);
      console.log("pause");
    });
    el.current?.addEventListener("pointerleave", () => {
      setPlay(!0);
      console.log("resume");
    });
  };

  const indicatorFunc = (i: number): void => {
    if (idx === i) return;
    else setIdx(i);
  };

  useEffect(() => {
    /* 初始化 */
    /* 保持slider显示效果，元素补全(loop) */
    if (!numo) return;
    width === 0 && setWidth(el.current?.clientWidth || 480);
    if (!props.children?.hasOwnProperty("length")) {
      /* 仅一个元素*/
      if (loop) {
        let temp = [props.children, props.children, props.children];
        setItems(temp);
        setNum(3);
        setIdxs(sliderSort(3));
      } else setItems([props.children]);
    } else {
      let temp: Array<ReactNode> = [];
      React.Children.map(props.children, (item: ReactNode) => {
        temp.push(item);
      });
      if (temp.length === 2 && loop) {
        /* 两个子元素 */
        temp = temp.concat(temp);
        setItems(temp);
        setNum(4);
        setIdxs(sliderSort(4));
      } else setItems(temp);
    }
    autoplay && setPlay(!0);
    autoplay && addEvents();
  }, [init]);

  useEffect(() => {
    if (play) id = setInterval(loopRef.current, duration);
    else clearInterval(id);
    return () => clearInterval(id);
  }, [play, idx]);

  useEffect(() => {
    setStyle(Object.assign({ width, height }, props.style || {}));
  }, [width]);

  const controlsBtns = [
    <Controls isprev={true} action={prev} key="prev" />,
    <Controls isprev={false} action={next} key="next" />,
  ];

  const indicators = (
    <Indicator num={numo} idx={idx} duration={duration} click={indicatorFunc} />
  );

  return numo === 0 ? (
    <div></div>
  ) : loop ? (
    <div className="_carousel" style={style} ref={el}>
      <div
        className="_carousel_inner"
        style={{
          width: width * num,
          height,
        }}
      >
        {items.map((item: any, key: number) => {
          return (
            <div
              className="_carousel_item"
              key={key}
              style={{
                width,
                height,
                left: -key * width,
                transform: `translate(${
                  (idxs[idx][key] > 0 ? 1 : idxs[idx][key]) * width
                }px, 0px)`,
                transitionDuration:
                  flag === 1
                    ? idxs[idx][key] > 0
                      ? "0ms"
                      : "300ms"
                    : idxs[idx][key] < 0
                    ? "0ms"
                    : "300ms",
              }}
            >
              {item}
            </div>
          );
        })}
      </div>
      {controls && controlsBtns}
      {indicator && indicators}
    </div>
  ) : (
    <div className="_carousel" style={style} ref={el}>
      <div
        className="_carousel_inner _carousel_inner2"
        style={{
          width: width * num,
          height,
          transform: `translate(${-idx * width}px, 0px)`,
        }}
      >
        {items.map((item: any, key: number) => {
          return (
            <div
              className="_carousel_item2"
              key={key}
              style={{
                width,
                height,
                left: key * width,
              }}
            >
              {item}
            </div>
          );
        })}
      </div>
      {controls && num !== 1 && controlsBtns}
      {indicator && num !== 1 && indicators}
    </div>
  );
};

export default Carousel;
