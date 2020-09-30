import React from "react";
import useTransition, { Stages as TransitionStages, useCheckStageChanged } from "../hooks/use-transition";

const noop = () => {};

export interface TransitionProps {
  onEnter?: (node: React.ReactNode) => void;
  onEntering?: (node: React.ReactNode) => void;
  onEntered?: (node: React.ReactNode) => void;
  onExit?: (node: React.ReactNode) => void;
  onExiting?: (node: React.ReactNode) => void;
  onExited?: (node: React.ReactNode) => void;

  isIn?: boolean;
  delay?: number;
  duration?: number;
  initStage?: boolean;

  children: (stage: TransitionStages | undefined, ref: React.RefObject<any>) => React.ReactNode;
}

const Transition: React.FC<TransitionProps> = props => {
  const {
    children,
    isIn,
    delay,
    duration = 600,
    initStage = false,

    onEnter = noop,
    onEntering = noop,
    onEntered = noop,
    onExit = noop,
    onExiting = noop,
    onExited = noop,
  } = props;

  const ref = React.useRef<HTMLElement>(null);
  const stage = useTransition(isIn, duration, delay, initStage);
  const isStageChanged = useCheckStageChanged(stage);

  React.useLayoutEffect(() => {
    if (!isStageChanged) return;

    switch (stage) {
      case TransitionStages.Enter:
        onEnter(ref.current);
        break;
      case TransitionStages.Entering:
        onEntering(ref.current);
        break;
      case TransitionStages.Entered:
        onEntered(ref.current);
        break;
      case TransitionStages.Exit:
        onExit(ref.current);
        break;
      case TransitionStages.Exiting:
        onExiting(ref.current);
        break;
      case TransitionStages.Exited:
        onExited(ref.current);
        break;
      default:
        break;
    }
  }, [stage, isStageChanged, onEnter, onEntering, onEntered, onExit, onExiting, onExited]);

  return (
    <>
      {children(stage, ref)}
    </>
  );
}

export default Transition;
export { TransitionStages };
