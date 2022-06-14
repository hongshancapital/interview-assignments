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
    const [precent, setPrecent] = useState(0);
    const timerRef = useRef<NodeJS.Timeout>();
    const precentTimerRef = useRef<NodeJS.Timeout>();

    const sum = children.length;

    const doTiming = () => {
      if (autoplay) {
        setPrecent(0);
        timerRef.current && clearInterval(timerRef.current);
        precentTimerRef.current && clearInterval(precentTimerRef.current);

        timerRef.current = setInterval(() => {
          setCurrent((pre) => (pre + 1 === sum ? 0 : pre + 1));
        }, autoplayTime);

        precentTimerRef.current = setInterval(() => {
          setPrecent((pre) => {
            const newPrecent = pre + (calcTime / autoplayTime) * 100;
            return newPrecent > 100 ? 100 : newPrecent;
          });
        }, calcTime);
      }
    };

    const changeCurrent = (index: number) => {
      setCurrent(index);
    };

    useImperativeHandle(ref, () => ({
      changeCurrent,
    }));

    useEffect(() => {
      doTiming();
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
          {children.map((node) => (
            <div className="components-carousel-node">{node}</div>
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
                  className="components-carousel-dot-precent"
                  style={{ width: `${precent}%` }}
                />
              )}
            </div>
          ))}
        </div>
      </div>
    );
  }
);
