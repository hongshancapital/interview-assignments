import React from "react";

export type CarouseProps = {
  interval?: number;
  speed?: number;
  dots?: boolean;
  autoPlay?: boolean;
  className?: string;
  style?: React.CSSProperties;
  children?: React.ReactNode;
};

export type CarouselItemProps = {
  className?: string;
  style?: React.CSSProperties;
  children?: React.ReactNode;
};

export type DotsProps = {
  className?: string;
  autoPlay?: boolean;
  activeIndex?: number;
  count?: number;
  interval?: number;
  switchTo?: (index: number) => void;
};

export enum CarouselConfigEnum {
  interval = 3000,
  speed = 400,
}
