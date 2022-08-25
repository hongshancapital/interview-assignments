import React, {
  CSSProperties,
  FC,
  HTMLAttributes,
  PointerEvent,
  ReactElement,
  ReactNode,
  useCallback,
  useEffect,
  useLayoutEffect,
  useMemo,
  useRef,
  useState,
} from "react";
import { fromEvent, map, merge as mergePipe, Subject, switchMap, takeUntil } from "rxjs";
import { useObservable } from "rxjs-hooks";
import "./index.css";
import { Indicator } from "./Indicator";
import { CarouselItem } from "./Item";
import { DEFAULT_AUTOPLAY, useCarouselState } from "./state";
import { classnames } from "./utils/classnames";
import { merge } from "./utils/merge";

export { Indicator } from "./Indicator";
export type { IIndicatorProps } from "./Indicator";
export { CarouselItem } from "./Item";
export type { ICarouselItemProps } from "./Item";

type CarouselItemNode = ReactElement<typeof CarouselItem>;
type IndicatorNode = ReactElement<typeof Indicator>;
export interface ICarouselProps {
  autoplay?: boolean | null | Partial<Omit<typeof DEFAULT_AUTOPLAY, "autoPlay">>;
  timingFunc?: CSSProperties["transitionTimingFunction"];
  transitionDuration?: string;
  direction?: "vertical" | "horizontal";
  resetPercent?: number;
}

const globalMove$ = fromEvent<PointerEvent>(window, "pointermove");
const globalUp$ = mergePipe(
  fromEvent<PointerEvent>(window, "pointerup"),
  fromEvent<PointerEvent>(window, "pointercancel")
);

export const Carousel: FC<ICarouselProps & HTMLAttributes<HTMLDivElement>> = ({
  children,
  className,
  timingFunc = "ease-in-out",
  transitionDuration = ".2s",
  direction = "horizontal",
  resetPercent = 30,
  autoplay,
  ...divProps
}) => {
  const mainRef = useRef<HTMLDivElement | null>(null);
  const currentRef = useRef<number | null>(null);
  const contentRef = useRef<HTMLDivElement | null>(null);
  const timerRef = useRef<number | null>(null);
  const translateRef = useRef<number>(0);

  const [, setCarouselState] = useCarouselState();
  const resetDrag$ = useRef(new Subject<void>());
  const gesture$ = useRef(new Subject<PointerEvent>());
  const [current, setCurrent] = useState<number | null>(null);
  const [clientWidth, updateClientWidth] = useState<number>(0);
  const { items, indicator } = useFilterChildren(children);

  const translate = useMemo(
    () => -(clientWidth / items.length) * (current || 0),
    [current, clientWidth, items.length]
  );

  const inDrag = useObservable<boolean>(
    () =>
      mergePipe(
        gesture$.current.pipe(map(() => true)),
        mergePipe(globalUp$, resetDrag$.current).pipe(map(() => false))
      ),
    false
  );

  const [mx, my] = useObservable(
    () =>
      mergePipe(
        gesture$.current.pipe(
          switchMap((startEvent) =>
            globalMove$.pipe(
              map(
                (moveEvent) =>
                  [moveEvent.clientX - startEvent.clientX, moveEvent.clientY - startEvent.clientY] as const
              ),
              takeUntil(globalUp$)
            )
          )
        ),
        resetDrag$.current.pipe(map(() => [null, null] as const))
      ),
    [0, 0]
  );

  const { autoPlay, duration, stopAtGesture } = useAutoPlay(autoplay);

  useLayoutEffect(() => {
    setTimeout(() => {
      setCurrent(0);
    });
  }, []);

  useEffect(() => {
    setCarouselState({
      autoPlay,
      current,
      direction,
      duration,
      inDrag,
      stopAtGesture,
      count: items.length,
    });
  }, [autoPlay, duration, stopAtGesture, direction, inDrag, current, items.length, setCarouselState]);

  useEffect(() => {
    if (!inDrag) {
      if (direction === "horizontal" && mx !== null) {
        if (Math.abs(mx) > ((contentRef.current?.clientWidth || 0) * resetPercent) / 100) {
          const newCurrent = (currentRef.current ?? 0) - mx / Math.abs(mx);
          setCurrent(newCurrent < 0 || newCurrent > items.length - 1 ? 0 : newCurrent);
        } else {
          setCurrent(currentRef.current);
        }
      }
      if (direction === "vertical" && my !== null) {
        if (Math.abs(my) > ((contentRef.current?.clientHeight || 0) * resetPercent) / 100) {
          const newCurrent = (currentRef.current ?? 0) - my / Math.abs(my);
          setCurrent(newCurrent < 0 || newCurrent > items.length - 1 ? 0 : newCurrent);
        } else {
          setCurrent(currentRef.current);
        }
      }
      resetDrag$.current.next();
    }
  }, [mx, my, inDrag, items.length, direction, resetPercent]);

  const translateMemo = useMemo(() => {
    if (inDrag) {
      if (direction === "horizontal" && mx !== null) return translate + mx;
      if (direction === "vertical" && my !== null) return translate + my;
      return translate;
    }
    return translate;
  }, [translate, mx, my, inDrag, direction]);

  const transform = useMemo(
    () =>
      `translate3d(${direction === "horizontal" ? translateMemo : 0}px,${
        direction === "vertical" ? translateMemo : 0
      }px,0px)`,
    [direction, translateMemo]
  );

  const setTimer = useCallback(() => {
    timerRef.current = setInterval(() => {
      if (!stopAtGesture) {
        resetDrag$.current.next();
      }
      if (currentRef.current === items.length - 1) {
        setCurrent(0);
      } else {
        setCurrent((currentRef.current ?? 0) + 1);
      }
    }, duration) as any;
  }, [duration, items.length, stopAtGesture]);

  const clearTimer = useCallback(() => {
    if (timerRef.current) {
      clearInterval(timerRef.current);
      timerRef.current = null;
    }
  }, []);

  useEffect(() => {
    let observer: ResizeObserver;
    if (mainRef.current && contentRef.current) {
      observer = new ResizeObserver(() => {
        updateClientWidth(
          direction === "horizontal" ? contentRef.current!.scrollWidth : contentRef.current!.scrollHeight
        );
      });
      observer.observe(mainRef.current);
    }
    return () => {
      observer.disconnect();
    };
  }, [direction, items.length]);

  useEffect(() => {
    currentRef.current = current;
    translateRef.current = translate;
  }, [current, translate]);

  useEffect(() => {
    if (contentRef.current) {
      updateClientWidth(
        direction === "horizontal" ? contentRef.current.scrollWidth : contentRef.current.scrollHeight
      );
    }
  }, [items.length, direction]);

  useEffect(() => {
    if (autoPlay) {
      if (stopAtGesture) {
        if (inDrag && timerRef.current) {
          clearTimer();
        }
        if (!inDrag && !timerRef.current) {
          setTimer();
        }
      } else {
        if (!timerRef.current) {
          setTimer();
        }
      }
    } else {
      if (timerRef.current) {
        clearTimer();
      }
    }
  }, [autoPlay, inDrag, stopAtGesture, clearTimer, setTimer]);

  return (
    <div
      {...divProps}
      className={classnames("carousel", className)}
      ref={(e) => (mainRef.current = e)}
      onContextMenu={(e) => {
        e.preventDefault();
        e.stopPropagation();
      }}
    >
      <div
        ref={(e) => {
          contentRef.current = e;
          contentRef.current?.addEventListener("pointerdown", (ev) => gesture$.current.next(ev as any));
        }}
        className="carousel-content"
        style={{
          transitionTimingFunction: timingFunc,
          transitionDuration,
          transform,
          transitionProperty: inDrag ? "none" : "transform",
        }}
      >
        {items}
      </div>
      {indicator}
    </div>
  );
};

function useAutoPlay(autoplay: ICarouselProps["autoplay"]) {
  return useMemo<typeof DEFAULT_AUTOPLAY>(() => {
    if (autoplay === true || autoplay === undefined) {
      return merge({} as any, DEFAULT_AUTOPLAY);
    } else if (autoplay === null || autoplay === false) {
      return { autoplay: false };
    } else {
      return merge({ ...DEFAULT_AUTOPLAY }, { ...(autoplay as any) });
    }
  }, [autoplay]);
}

function useFilterChildren(children: ReactNode) {
  return useMemo<{ items: CarouselItemNode[]; indicator: IndicatorNode | null }>(() => {
    if (Object.prototype.toString.call(children) === "[object Array]") {
      const childList = children as (CarouselItemNode | CarouselItemNode[] | IndicatorNode)[];
      const items: CarouselItemNode[] = [];
      let indicator: IndicatorNode | null = null;
      childList.forEach((item) => {
        if (Array.isArray(item)) {
          item.forEach((node) => {
            if (node.type === CarouselItem) {
              items.push(node);
            }
          });
        } else {
          if (item.type === Indicator) {
            indicator = item;
          } else if (item.type === CarouselItem) {
            items.push(item);
          }
        }
      });
      return { items, indicator };
    } else if (children && typeof children === "object" && children.hasOwnProperty("type")) {
      if ((children as ReactElement).type === CarouselItem) {
        return { items: [children as CarouselItemNode], indicator: null };
      }
    }
    return { items: [], indicator: null };
  }, [children]);
}
