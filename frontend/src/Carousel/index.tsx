import React, {
  CSSProperties,
  FC,
  HTMLAttributes,
  PointerEvent,
  ReactElement,
  ReactNode,
  useEffect,
  useMemo,
  useRef,
  useState,
} from "react";
import { fromEvent, map, Subject, switchMap, takeUntil } from "rxjs";
import { useObservable } from "rxjs-hooks";
import "./index.css";
import { Indicator } from "./Indicator";
import { CarouselItem } from "./Item";
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
}

const DEFAULT_AUTOPLAY = { autoPlay: true, duration: 1000, stopAtGesture: false };

export const Carousel: FC<ICarouselProps & HTMLAttributes<HTMLDivElement>> = ({
  children,
  className,
  timingFunc = "ease-in-out",
  transitionDuration = ".2s",
  autoplay,
  ...divProps
}) => {
  const gesture$ = useRef(new Subject<PointerEvent>());
  const [current, setCurrent] = useState<number | null>(2);
  const currentRef = useRef<number | null>(null);
  const contentRef = useRef<HTMLDivElement | null>(null);
  const timerRef = useRef<number | null>(null);
  const [clientWidth, updateClientWidth] = useState<number>(0);
  const { items, indicator } = useFilterChildren(children);

  const [] = useObservable(
    () =>
      gesture$.current.asObservable().pipe(
        switchMap((startEvent) =>
          fromEvent<PointerEvent>(contentRef.current!, "pointermove").pipe(
            map(
              (moveEvent) =>
                [
                  [startEvent.clientX, startEvent.clientY],
                  [moveEvent.clientX - startEvent.clientX, moveEvent.clientY - startEvent.clientY],
                ] as const
            ),
            takeUntil(fromEvent(contentRef.current!, "pointerup"))
          )
        )
      ),
    [
      [0, 0],
      [0, 0],
    ]
  );

  const transform = useMemo(
    () => `translate3d(-${(clientWidth / items.length) * (current || 0)}px,0,0)`,
    [current, clientWidth, items.length]
  );

  useEffect(() => {
    currentRef.current = current;
  }, [current]);

  useEffect(() => {
    if (contentRef.current) {
      updateClientWidth(contentRef.current.scrollWidth);
    }
  }, [items.length]);

  const { autoPlay, duration } = useAutoPlay(autoplay);

  useEffect(() => {
    if (autoPlay) {
      if (timerRef.current) {
        clearInterval(timerRef.current);
      }
      timerRef.current = setInterval(() => {
        if (currentRef.current === items.length - 1) {
          setCurrent(0);
        } else {
          setCurrent((currentRef.current ?? 0) + 1);
        }
      }, duration) as any;
    } else {
      if (timerRef.current) {
        clearInterval(timerRef.current);
      }
    }
  }, [autoPlay, duration, items.length]);

  return (
    <div className={classnames("carousel", className)} {...divProps}>
      <div
        ref={(e) => (contentRef.current = e)}
        className="carousel-content"
        style={{ transitionTimingFunction: timingFunc, transitionDuration, transform }}
        onPointerDown={(e) => gesture$.current.next(e)}
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
    } else if (autoplay === null) {
      return { autoplay: false };
    } else {
      return merge({ ...DEFAULT_AUTOPLAY }, { ...(autoplay as any) });
    }
  }, [autoplay]);
}

function useFilterChildren(children: ReactNode) {
  return useMemo<{ items: CarouselItemNode[]; indicator: IndicatorNode | null }>(() => {
    if (Object.prototype.toString.call(children) === "[object Array]") {
      const childList = children as (CarouselItemNode | IndicatorNode)[];
      const items = childList.filter((item) => item.type === CarouselItem) as CarouselItemNode[];
      const indicator = (childList.find((item) => item.type === Indicator) as IndicatorNode) ?? null;
      return { items, indicator };
    } else if (children && typeof children === "object" && children.hasOwnProperty("type")) {
      if ((children as ReactElement).type === CarouselItem) {
        return { items: [children as CarouselItemNode], indicator: null };
      }
    }
    return { items: [], indicator: null };
  }, [children]);
}
