import { jest } from "@jest/globals";
import { act } from "@testing-library/react";

export function flushPromisesAndTimers(): Promise<void> {
  jest.useFakeTimers();
  return act(
    () =>
      new Promise<void>((resolve) => {
        setTimeout(resolve, 1);
        jest.runAllTimers();
      })
  );
}
