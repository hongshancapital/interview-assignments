import { useCallback, useRef } from "react";

/**
 * yield 0, 1, 2 .... (count-1)
 * @param count loop limit
 */
export function* CarouselStepper(count: number): Generator<number> {
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
