import { useCallback, useEffect, useRef, useState } from "react";

/**
 * yield 0, 1, 2 .... (count-1)
 * @param count loop limit
 */
function* CarouselStepper(count: number): Generator<number> {
  for (let index = 0; true; index++) {
    index = index % count;
    yield index; // prevent overflow;
  }
}

export function useStepper(count: number) {
  const it = useRef(CarouselStepper(count)).current;
  const next = useCallback(
    () => it.next().value,
    [it]
  );
  return next;
}

export function useCarouselStepper(count: number, dom: HTMLDivElement | null) {
  const stepperRef = useRef<Generator<number>>();
  useEffect(
    () => {
      if (stepperRef.current) {
        stepperRef.current?.return(undefined);
      }
      stepperRef.current = CarouselStepper(count);
    },
    [count]
  );

  const [step, setStep] = useState(0);

  useEffect(
    () => {
      if (!dom) return;
      const stepper = stepperRef.current;
      const listener = (e: TransitionEvent) => {
        const { value } = stepper!.next();
        setStep(value);
      };
      setTimeout(() => listener({} as any), 3000)
      dom.addEventListener('transitionend', listener, { passive: true });
      return () => {
        dom.removeEventListener('transitionend', listener);
      }
    },
    [dom],
  );

  return step;
}
