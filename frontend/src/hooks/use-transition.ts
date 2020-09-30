import * as React from "react";

export enum Stages {
  "Enter" = "Enter",
  "Entering" = "Entering",
  "Entered" = "Entered",
  "Exit" = "Exit",
  "Exiting" = "Exiting",
  "Exited" = "Exited",
}

export function useCheckStageChanged(stage: Stages | undefined): boolean {
  const ref = React.useRef<Stages | undefined>(stage);

  React.useEffect(() => {
    ref.current = stage;
  }, [stage]);

  return stage !== ref.current;
}

export default function useTransition(
  isIn?: boolean, duration: number = 1000, delay?: number, initStage?: boolean
): Stages | undefined {
  const initialStage: Stages | undefined = initStage ? undefined : (isIn ? Stages.Entered : Stages.Exited);
  const [ stage, setStage ] = React.useState<Stages | undefined>(initialStage);
  const isStageChanged = useCheckStageChanged(stage);

  React.useLayoutEffect(() => {
    if (isIn && (stage === undefined || stage === Stages.Exited)) {
      setStage(Stages.Enter);
    } else if (!isIn && (stage === undefined || stage === Stages.Entered)) {
      setStage(Stages.Exit);
    }
  }, [stage, isIn]);

  React.useLayoutEffect(() => {
    if (!isStageChanged) return;
    if (stage !== Stages.Enter && stage !== Stages.Exit) return;

    const nextStage = ({
      [Stages.Enter]: Stages.Entering,
      [Stages.Exit]: Stages.Exiting,
    })[stage];
    if (!delay) {
      setStage(nextStage);
    } else {
      setTimeout(() => {
        setStage(nextStage);
      }, delay);
    }
  }, [stage, delay, isStageChanged]);

  React.useLayoutEffect(() => {
    if (!isStageChanged) return;
    if (stage !== Stages.Entering && stage !== Stages.Exiting) return;

    const nextStage = ({
      [Stages.Entering]: Stages.Entered,
      [Stages.Exiting]: Stages.Exited,
    })[stage];
    setTimeout(() => {
      setStage(nextStage);
    }, duration);
  }, [stage, duration, isStageChanged]);

  return stage;
}
