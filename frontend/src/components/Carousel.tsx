import React from "react";
import classnames from "classnames";
//import CarouselItem from "./CarouselItem";
import { ElementType } from "@~/utils";
import styles from "./Carousel.module.css";
import Transition, { TransitionStages } from "./Transition";

interface RootComponentProps extends React.HTMLProps<HTMLElement> {
}

export interface CarouselProps {
  as?: ElementType<RootComponentProps>;
  children: React.ReactElement | React.ReactElement[];
  interval?: number;
  className?: string;
  defaultActiveIndex?: number;
  onSlidingStart?: (idx: number, prev?: number) => void;
  onSlidingEnd?: (idx: number) => void;
}

const Carousel: React.FC<CarouselProps> = props => {
  const {
    as: InnerComponent = "div",
    children,
    interval = 3000,
    defaultActiveIndex = 0,
  } = props;
  const [ currIdx, setIndex ] = React.useState(defaultActiveIndex);

  const handleEnterOrExit = React.useCallback((node) => {
    if (node) {
      void node.innerText;
    }
  }, []);

  const duration = 600;
  let globalDirection: "rtl" | "ltr" = "rtl";
  const childrenCount = React.Children.count(children);

  React.useEffect(() => {
    if (childrenCount > 1) {
      const tid = setInterval(() => {
        setIndex(idx => (idx + 1) % childrenCount);
      }, interval + duration);
      return () => {
        clearInterval(tid);
      };
    }
  }, [interval, duration, childrenCount]);

  const childElements = React.Children.toArray(children);

  const indicators = childrenCount > 1 ? childElements.map((_, idx) => {
    return (
      <Transition key={idx} isIn={idx === currIdx} delay={duration} duration={interval} onEnter={handleEnterOrExit} initStage={true}>
        {(stage, ref) => {
          const isActive = idx === currIdx && (stage === TransitionStages.Entering);
          return (
            <li ref={ref} key={`indicator-${idx}`} className={classnames(isActive && styles.active)}>
              <span style={{ transitionDuration: `${interval}ms` }}></span>
            </li>
          );
        }}
      </Transition>
    );
  }) : null;

  const contents = childElements.map((item, idx) => {
    return (
      <Transition key={idx} isIn={idx === currIdx} duration={duration}
        onEnter={handleEnterOrExit}
        onExit={handleEnterOrExit}
      >
        {(stage, ref) => {
          const isActive = (!stage && idx === currIdx)
            || (stage === TransitionStages.Entered || stage === TransitionStages.Exit || stage === TransitionStages.Exiting);
          const isPrevious = (stage === TransitionStages.Enter || stage === TransitionStages.Entering) && (globalDirection === "ltr");
          const isNext = (stage === TransitionStages.Enter || stage === TransitionStages.Entering) && (globalDirection === "rtl");
          const direction = (stage === TransitionStages.Entering || stage === TransitionStages.Exiting || stage === TransitionStages.Exit) ? globalDirection : undefined;
          return React.cloneElement(item as React.ReactElement, {
            ref,
            isActive,
            isPrevious,
            isNext,
            direction,
            duration,
          });
        }}
      </Transition>
    );
  });
  return (
    <div className={styles.root}>
      {indicators &&
        <ol className={styles.indicators}>
          {indicators}
        </ol>
      }
      <InnerComponent className={styles.inner}>
        {contents}
      </InnerComponent>
    </div>
  );
};

export default Carousel;
