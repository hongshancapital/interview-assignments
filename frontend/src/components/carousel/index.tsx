import React, {
  forwardRef,
  useImperativeHandle,
  useMemo,
  useRef,
  useState
} from "react";

import classNames from "classnames";

import { useIsomorphicLayoutEffect } from "../../hooks/use-isomorphic-layout-effect";
import { useResizeObserver } from "../../hooks/use-resize-observer";

import { Indicators, IndicatorsProps } from "../indicators";

import { Player } from "./player";

import styles from "./styles.module.scss";

export const Carousel = forwardRef<CarouselRefValue, CarouselProps>(
  function Carousel(
    {
      children,
      className,

      autoplay = false,
      defaultActiveIndex = 2,
      direction = "horizontal",
      indicatorPlacement = "bottom",
      interval = 3000,
      showDefaultIndicators = true,

      ...hostProps
    },
    ref
  ): JSX.Element {
    const [activeIndex, setActiveIndex] = useState(defaultActiveIndex);
    const [animPaused, setAnimPaused] = useState(false);

    const listRef = useRef<HTMLUListElement | null>(null);
    const [wrapperRef, rect] = useResizeObserver<HTMLDivElement>();
    const width = rect?.width || 0;
    const height = rect?.height || 0;
    const isVertical = direction === "vertical";

    // 这里处理下 children
    const elements = (React.Children.map(children, (el) => el) || [])
      ?.filter((el) => el != null)
      .map((el, index) => (
        <li
          key={index}
          role="group"
          tabIndex={-1}
          aria-label={`Slide ${index + 1}`}
          aria-hidden={index !== activeIndex}
          className={styles.carouselItem}
          style={isVertical ? { height } : { width }}
        >
          {el}
        </li>
      ));

    const elemCount = elements.length;

    // 内部使用 & 对外通过 ref 暴露的方法
    const ctrlUtils = useMemo<CarouselRefValue>(
      () => ({
        prev() {
          setActiveIndex((prev) => ((prev || elemCount) - 1) % elemCount);
        },
        next() {
          setActiveIndex((prev) => (prev + 1) % elemCount);
        },
        goTo(page: number) {
          setActiveIndex(() => Math.max(page, 0) % elemCount);
        }
      }),
      [elemCount]
    );
    useImperativeHandle(ref, () => ctrlUtils);

    const player = useMemo(
      () =>
        autoplay
          ? new Player(
              // 最小 1000ms
              Math.max(interval, 1000),
              activeIndex
            )
          : null,
      [activeIndex, autoplay, interval]
    );

    useIsomorphicLayoutEffect(() => {
      if (player) {
        if (!animPaused) {
          const onEnded = () => ctrlUtils.next();
          player.addEventListener("ended", onEnded);
          player.play();

          return () => player.removeEventListener("ended", onEnded);
        } else {
          player.paused = true;
        }
      }
    }, [player, ctrlUtils, animPaused]);

    return (
      <div
        {...hostProps}
        ref={wrapperRef}
        role="region"
        aria-label="carousel"
        className={classNames(className, styles.carouselContainer)}
        onMouseEnter={(e) => {
          if (autoplay) {
            setAnimPaused(true);
          }
          hostProps.onMouseEnter?.(e);
        }}
        onMouseLeave={(e) => {
          if (autoplay) {
            setAnimPaused(false);
          }
          hostProps.onMouseLeave?.(e);
        }}
      >
        <ul
          ref={listRef}
          className={styles.carouselInnerContainer}
          style={{
            display: "flex",
            flexDirection: isVertical ? "column" : "row",
            [isVertical ? "height" : "width"]:
              (isVertical ? height : width) * elemCount,
            transform: isVertical
              ? `translateY(-${activeIndex * height}px)`
              : `translateX(-${activeIndex * width}px)`
          }}
        >
          {elements}
        </ul>

        {showDefaultIndicators && (
          <Indicators
            activeIndex={activeIndex}
            count={elemCount}
            duration={interval}
            paused={animPaused}
            // 自动播放的时候才有动画
            animation={autoplay}
            placement={indicatorPlacement}
            onChange={(page) => {
              ctrlUtils.goTo(page);
              (listRef.current?.children[
                activeIndex
              ] as HTMLLIElement)?.focus();
            }}
          />
        )}
        {/*  TODO: 还可以在这里预留下可供自定义的控制区 */}
      </div>
    );
  }
);

export type CarouselProps = JSX.IntrinsicElements["div"] & {
  /** 是否自动播放，默认 false */
  autoplay?: boolean;
  /** 默认激活的索引，默认为 0 */
  defaultActiveIndex?: number;
  /** 方向，默认为 horizontal */
  direction?: "vertical" | "horizontal";
  /** 自动播放时间间隔，默认 3000ms */
  interval?: number;
  /** 是否展示默认的 indicators */
  showDefaultIndicators?: boolean;
  /** indicator 位置，默认 bottom */
  indicatorPlacement?: IndicatorsProps["placement"];
};

export type CarouselRefValue = {
  /** 前往下一页 */
  prev(): void;
  /** 前往上一页 */
  next(): void;
  /** 前往指定页 */
  goTo(page: number): void;
};
