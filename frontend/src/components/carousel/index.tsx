import React, {
  forwardRef,
  useImperativeHandle,
  useMemo,
  useRef,
  useState,
} from "react";

import classNames from "classnames";

import { useIsomorphicLayoutEffect } from "../../hooks/use-isomorphic-layout-effect";

import { Indicators, IndicatorsProps } from "../indicators";

import { Player } from "./player";

import styles from "./styles.module.scss";

export const Carousel = forwardRef<CarouselRefValue, CarouselProps>(
  function Carousel(
    {
      children,
      className,

      autoplay = false,
      defaultActiveIndex = 0,
      direction = "horizontal",
      indicatorPlacement = "bottom",
      interval = 3000,
      pauseOnHover = true,
      showDefaultIndicators = true,

      ...hostProps
    },
    ref
  ): JSX.Element {
    const [animPaused, setAnimPaused] = useState(false);
    const [activeIndex, setActiveIndex] = useState(defaultActiveIndex);
    const listRef = useRef<HTMLUListElement | null>(null);
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
        >
          {el}
        </li>
      ));
    const elemCount = elements.length;
    const cssProperties = {
      "--page-count": elemCount,
      "--current-page": activeIndex,
    } as React.CSSProperties;

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
        },
      }),
      [elemCount]
    );

    useImperativeHandle(ref, () => ctrlUtils);
    useIsomorphicLayoutEffect(() => {
      if (player) {
        if (!animPaused) {
          const onEnded = () => ctrlUtils.next();

          player.play();
          player.addEventListener("ended", onEnded);

          return () => player.removeEventListener("ended", onEnded);
        } else {
          player.paused = true;
        }
      }
    }, [player, ctrlUtils, animPaused]);

    return (
      <div
        {...hostProps}
        role="region"
        aria-label="carousel"
        className={classNames(className, styles.carouselContainer)}
        onMouseEnter={(e) => {
          if (autoplay && pauseOnHover) {
            setAnimPaused(true);
          }
          hostProps.onMouseEnter?.(e);
        }}
        onMouseLeave={(e) => {
          if (autoplay && pauseOnHover) {
            setAnimPaused(false);
          }
          hostProps.onMouseLeave?.(e);
        }}
      >
        <ul
          ref={listRef}
          style={cssProperties}
          data-direction={direction}
          className={styles.carouselInnerContainer}
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
              const el = listRef.current?.children[activeIndex];

              ctrlUtils.goTo(page);
              (el as HTMLLIElement | null)?.focus();
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
  /** indicator 位置，默认 bottom */
  indicatorPlacement?: IndicatorsProps["placement"];
  /** autoplay 时是否启用鼠标 hover 停止切换功能 */
  pauseOnHover?: boolean;
  /** 是否展示默认的 indicators */
  showDefaultIndicators?: boolean;
};

export type CarouselRefValue = {
  /** 前往下一页 */
  prev(): void;
  /** 前往上一页 */
  next(): void;
  /** 前往指定页 */
  goTo(page: number): void;
};
