import React from "react";
import type { FC, ReactNode, ReactElement } from "react";
import clsx from "clsx";

import { useIndicator } from "./useIndicator";

import "./index.css";

const DEFAULT_DURATION = 3 * 1_000;

export interface CarouselProps {
  /**
   * 幻灯片持续时间
   */
  duration?: number;
  /**
   * 幻灯片内容
   */
  children: ReactElement[];
}

export const Carousel: FC<CarouselProps> & { Slide: typeof Slide } = (
  props
): JSX.Element => {
  const { children, duration = DEFAULT_DURATION } = props;
  const len = React.Children.count(children);

  const { indicator } = useIndicator({ len, duration });

  return (
    <div className="carousel">
      <SlideAlbum currentIndex={indicator}>{children}</SlideAlbum>
      <DotGroup>
        {Array.from({ length: len }, (_, _i) => _i).map<ReactElement>(
          (_, _index) => (
            <Dot
              key={_index}
              canPlay={_index === indicator}
              duration={duration}
            />
          )
        )}
      </DotGroup>
    </div>
  );
};

function SlideAlbum({
  children,
  currentIndex,
}: {
  children: ReactNode;
  currentIndex: number;
}): JSX.Element {
  return (
    <div
      className="slide_container"
      style={{
        transform: `translateX(-${currentIndex * 100}%)`,
      }}
    >
      {children}
    </div>
  );
}

interface DotProps {
  /**
   * 幻灯片能否播放
   */
  canPlay: boolean;
  /**
   * 每一张幻灯片持续时间
   */
  duration?: number;
}

function DotGroup({ children }: { children: ReactNode }): JSX.Element {
  return <div className="dot_group">{children}</div>;
}

function Dot(props: DotProps): JSX.Element {
  const { duration = DEFAULT_DURATION, canPlay } = props;

  return (
    <div className="dot_progress_wrapper">
      <div className="dot_progress_bar">
        {canPlay ? (
          <div
            className="dot_progress"
            style={{
              animation: `progress ${duration / 1000}s linear`,
            }}
          />
        ) : null}
      </div>
    </div>
  );
}

// import styles from "./index.module.css"

type SlideProps = {
  /**
   * 使用自定义的类名定义样式
   */
  className?: string;
  /**
   * 标题
   */
  title: string;
  /**
   * 背景图片地址
   */
  src: string;
  /**
   * 幻灯片描述
   */
  content?: string;
};

function Slide(props: SlideProps): JSX.Element {
  const { title, src, content, className } = props;

  return (
    <div
      style={{
        backgroundImage: `url(${src})`,
      }}
      className={clsx("slide", className)}
    >
      <h3 className="slide_title">{title}</h3>
      <p className="slide_content">{content}</p>
    </div>
  );
}

Carousel.Slide = Slide;
