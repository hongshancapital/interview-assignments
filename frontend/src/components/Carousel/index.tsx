import React, {
  CSSProperties,
  ReactNode,
  useEffect,
  useRef,
  forwardRef,
  useImperativeHandle,
  useState,
} from "react";
import "./index.css";

type Props = {
  initialCurrent?: number;
  onChange?: (current: number) => void;
  autoplay?: boolean;
  autoplayTime?: number;
  className?: string;
  style?: CSSProperties;
  children: ReactNode[];
};

export type Instance = {
  changeCurrent: (current: number) => void;
};

const calcTime = 50;

export const Carousel = forwardRef<Instance, Props>(
  (
    {
      initialCurrent = 0,
      onChange,
      autoplay,
      autoplayTime = 3000,
      className,
      style,
      children,
    },
    ref
  ) => {
    const [current, setCurrent] = useState(initialCurrent);
    const [countTime, _setCountTime] = useState(0);
    const countTimeRef = useRef(0);
    const timerRef = useRef<NodeJS.Timeout>();

    const sum = children.length;

    const setCountTime = (s: number) => {
      countTimeRef.current = s;
      _setCountTime(s);
    };

    const doTiming = () => {
      if (autoplay) {
        setCountTime(0);
        timerRef.current && clearTimeout(timerRef.current);

        const cb = (init?: boolean) => {
          if (!init) {
            setCountTime(countTimeRef.current + calcTime);
          }

          const restTime = autoplayTime - countTimeRef.current;
          if (restTime <= 0) {
            setCurrent((pre) => (pre + 1 === sum ? 0 : pre + 1));
          } else if (restTime < calcTime) {
            timerRef.current = setTimeout(cb, restTime);
          } else {
            timerRef.current = setTimeout(cb, calcTime);
          }
        };

        cb(true);
      }
    };

    const changeCurrent = (index: number) => {
      setCurrent(index);
    };

    useImperativeHandle(ref, () => ({
      changeCurrent,
    }));

    useEffect(() => {
      // doTiming 方法无状态，不需要作为 dependence
      doTiming();
      // onChange 方法为外界传入方法，不需要在改变时重新触发
      onChange?.(current);
    }, [current]);

    return (
      <div
        className={`components-carousel${className ? " " + className : ""}`}
        style={style}
      >
        <div
          className="components-carousel-content"
          style={{
            width: `${100 * sum}%`,
            transform: `translate3d(-${(100 / sum) * current}%, 0px, 0px)`,
          }}
        >
          {children.map((node, index) => (
            <div key={index} className="components-carousel-node">
              {node}
            </div>
          ))}
        </div>

        <div className="components-carousel-dots">
          {children.map((_, index) => (
            <div
              key={index}
              className="components-carousel-dot"
              onClick={() => changeCurrent(index)}
            >
              {current === index && (
                <div
                  className="components-carousel-dot-percent"
                  style={{ width: `${countTime / autoplayTime}%` }}
                />
              )}
            </div>
          ))}
        </div>
      </div>
    );
  }
);
