import React from "react";
import classnames from "classnames";
import styles from "./CarouselItem.module.css";

export interface CarouselItemProps extends React.RefAttributes<HTMLDivElement> {
  as?: 'div';
  background?: string;
  color?: string;
  className?: string;
  isActive?: boolean;
  isPrevious?: boolean;
  isNext?: boolean;
  direction?: "rtl" | "ltr";
  duration?: number;
  children?: React.ReactNode;
}

const CarouselItem: React.FC<CarouselItemProps> = React.forwardRef((props, ref) => {
  const {
    background, color,
    as: RootComponent = "div",
    children,
    className,
    isActive,
    isPrevious,
    isNext,
    direction,
    duration,
  } = props;

  const dir = direction === "ltr" ? styles.ltr : (direction === "rtl" ? styles.rtl : undefined);

  const classNames = classnames(
    styles.root,
    className,
    isActive && styles.active,
    isPrevious && styles.previous,
    isNext && styles.next,
    dir,
  );

  const style = {
    ...background && { backgroundImage: `url(${background})` },
    ...duration !== undefined && { transitionDuration: `${duration}ms` },
    ...color && { color },
  };

	return (
    <RootComponent ref={ref} className={classNames} style={style}>
      {children}
    </RootComponent>
  );
});

export default CarouselItem;
