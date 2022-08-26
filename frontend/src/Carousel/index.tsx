import React, {
  CSSProperties,
  FC,
  HTMLAttributes,
  PointerEvent,
  ReactElement,
  ReactNode,
  useCallback,
  useEffect,
  useMemo,
  useRef,
  useState,
} from "react";
import { filter, fromEvent, map, merge as mergePipe, Subject, switchMap, takeUntil } from "rxjs";
import { useObservable } from "rxjs-hooks";
import { useInterval } from "./hooks/useInterval";
import "./index.css";
import { Indicator } from "./Indicator";
import { CarouselItem } from "./Item";
import {
  DEFAULT_AUTOPLAY,
  useAutoPlayDurationState,
  useAutoPlayState,
  useCurrentState,
  useInDragState,
  useInTransitionState,
  useItemsCountState,
  useStopAtGestureState,
} from "./state";
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
  fromEvent<PointerEvent>(window, "pointerup").pipe(filter((e) => e.button === 0)),
  fromEvent<PointerEvent>(window, "pointercancel")
);

const resetDrag$ = new Subject<void>();
const gesture$ = new Subject<PointerEvent>();

function useInDrag() {
  const [, setInDrag] = useInDragState();
  const inDrag = useObservable<boolean>(
    () => mergePipe(gesture$.pipe(map(() => true)), mergePipe(globalUp$, resetDrag$).pipe(map(() => false))),
    false
  );
  useEffect(() => {
    setInDrag(inDrag);
  }, [inDrag, setInDrag]);
}

export const Carousel: FC<ICarouselProps & HTMLAttributes<HTMLDivElement>> = ({
  children,
  className,
  timingFunc = "ease-in-out",
  transitionDuration = ".25s",
  direction = "horizontal",
  resetPercent = 30,
  autoplay,
  ...divProps
}) => {
  const mainRef = useRef<HTMLDivElement | null>(null);
  const currentRef = useRef<number | null>(null);
  const contentRef = useRef<HTMLDivElement | null>(null);

  const [inTransition, setInTransition] = useInTransitionState();
  const [clientSize, updateClientSize] = useState<number>(0);
  const [items, indicator] = useFilterChildren(children);
  const [itemsCount] = useItemsCountState();

  const [current, setCurrent] = useCurrentState();
  useInDrag();
  const [inDrag] = useInDragState();

  useAutoPlay(autoplay);
  const [autoPlay] = useAutoPlayState();
  const [stopAtGesture] = useStopAtGestureState();
  const [duration] = useAutoPlayDurationState();

  const translate = useMemo(
    () => -(clientSize / itemsCount) * (current || 0),
    [current, clientSize, itemsCount]
  );

  const [mx, my] = useObservable(
    () =>
      mergePipe(
        gesture$.pipe(
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
        resetDrag$.pipe(
          map(() => {
            return [null, null] as const;
          })
        )
      ),
    [0, 0]
  );

  const timerDelay = useMemo(() => {
    if (inTransition) return null;
    if (autoPlay) {
      if (stopAtGesture) return !inDrag ? duration : null;
      return duration;
    }
    return null;
  }, [inTransition, autoPlay, stopAtGesture, inDrag, duration]);

  const switchItem = useCallback(() => {
    if (inDrag && autoPlay && !stopAtGesture) {
      resetDrag$.next();
    }
    if (current === itemsCount - 1) {
      setCurrent(0);
    } else {
      setCurrent((current ?? 0) + 1);
    }
  }, [autoPlay, current, inDrag, itemsCount, setCurrent, stopAtGesture]);

  useInterval(switchItem, timerDelay);

  useEffect(() => {
    currentRef.current = current;
  });

  useEffect(() => {
    const id = setTimeout(() => {
      setCurrent(0);
    });
    return () => {
      clearTimeout(id);
    };
  }, [setCurrent]);

  useEffect(() => {
    if (!inDrag) {
      if (direction === "horizontal" && mx !== null) {
        if (Math.abs(mx) > ((contentRef.current?.clientWidth || 0) * resetPercent) / 100) {
          const newCurrent = (currentRef.current ?? 0) - mx / Math.abs(mx);
          setCurrent(newCurrent < 0 || newCurrent > itemsCount - 1 ? 0 : newCurrent);
        }
      }
      if (direction === "vertical" && my !== null) {
        if (Math.abs(my) > ((contentRef.current?.clientHeight || 0) * resetPercent) / 100) {
          const newCurrent = (currentRef.current ?? 0) - my / Math.abs(my);
          setCurrent(newCurrent < 0 || newCurrent > itemsCount - 1 ? 0 : newCurrent);
        }
      }
      resetDrag$.next();
    }
  }, [direction, inDrag, itemsCount, mx, my, resetPercent, setCurrent]);

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

  useEffect(() => {
    let observer: ResizeObserver;
    if (mainRef.current && contentRef.current) {
      observer = new ResizeObserver(() => {
        updateClientSize(
          direction === "horizontal" ? contentRef.current!.scrollWidth : contentRef.current!.scrollHeight
        );
      });
      observer.observe(mainRef.current);
    }
    return () => {
      observer.disconnect();
    };
  }, [direction, itemsCount]);

  useEffect(() => {
    if (contentRef.current) {
      updateClientSize(
        direction === "horizontal" ? contentRef.current.scrollWidth : contentRef.current.scrollHeight
      );
    }
  }, [itemsCount, direction]);

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
          contentRef.current?.addEventListener("pointerdown", (ev) => {
            if (ev.button === 0) {
              gesture$.next(ev as any);
            }
          });
          e?.addEventListener("transitionstart", () => setInTransition(true));
          e?.addEventListener("transitionend", () => setInTransition(false));
        }}
        className={classnames("carousel-content", direction)}
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
  const [, setAutoPlay] = useAutoPlayState();
  const [, setAutoPlayDuration] = useAutoPlayDurationState();
  const [, setStopAtGesture] = useStopAtGestureState();

  const { autoPlay, duration, stopAtGesture } = useMemo<typeof DEFAULT_AUTOPLAY>(() => {
    if (autoplay === true || autoplay === undefined) {
      return merge({} as any, DEFAULT_AUTOPLAY);
    } else if (autoplay === null || autoplay === false) {
      return { autoplay: false };
    } else {
      return merge({ ...DEFAULT_AUTOPLAY }, { ...(autoplay as any) });
    }
  }, [autoplay]);

  useEffect(() => {
    setAutoPlay(autoPlay);
    setAutoPlayDuration(duration);
    setStopAtGesture(stopAtGesture);
  }, [autoPlay, duration, stopAtGesture, setAutoPlay, setAutoPlayDuration, setStopAtGesture]);
}

function useFilterChildren(children: ReactNode) {
  const [, setItemsCount] = useItemsCountState();
  const { items, indicator } = useMemo<{ items: CarouselItemNode[]; indicator: IndicatorNode | null }>(() => {
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

  useEffect(() => {
    setItemsCount(items.length);
  }, [items.length, setItemsCount]);

  return [items, indicator] as const;
}
