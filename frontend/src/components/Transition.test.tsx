import React from "react";
import { act, render } from "@testing-library/react";

import Transition, { TransitionStages } from "./Transition";

const sleep = (delay?: number, ...args: unknown[]) => new Promise<void>((resolve) => {
  setTimeout(resolve, delay, ...args);
});

test("invokes on{Enter, Entering, Entered} events", async () => {
  const onEnter = jest.fn();
  const onEntering = jest.fn();
  const onEntered = jest.fn();
  const childrenCallback = jest.fn();
  const { rerender } = render(
    <Transition delay={1} duration={10} onEnter={onEnter} onEntering={onEntering} onEntered={onEntered}>
      {childrenCallback}
    </Transition>
  );

  await act(() => sleep(1));
  expect(childrenCallback.mock.calls.length).toBe(1);
  expect(childrenCallback.mock.calls[0][0]).toBe(TransitionStages.Exited);

  rerender(
    <Transition delay={1} duration={10} isIn={true} onEnter={onEnter} onEntering={onEntering} onEntered={onEntered}>
      {childrenCallback}
    </Transition>
  );

  expect(childrenCallback.mock.calls.length).toBe(3);
  expect(childrenCallback.mock.calls[2][0]).toBe(TransitionStages.Enter);

  expect(childrenCallback.mock.calls.length).toBe(3);
  expect(onEnter.mock.calls.length).toBe(1);
  expect(onEntering.mock.calls.length).toBe(0);
  expect(onEntered.mock.calls.length).toBe(0);

  await act(() => sleep(2));

  expect(childrenCallback.mock.calls.length).toBe(4);
  expect(onEnter.mock.calls.length).toBe(1);
  expect(onEntering.mock.calls.length).toBe(1);
  expect(onEntered.mock.calls.length).toBe(0);

  await act(() => sleep(12));

  expect(childrenCallback.mock.calls.length).toBe(5);
  expect(onEnter.mock.calls.length).toBe(1);
  expect(onEntering.mock.calls.length).toBe(1);
  expect(onEntered.mock.calls.length).toBe(1);
});

test("invokes on{Enter, Entering, Entered} events with initStage", async () => {
  const onEnter = jest.fn();
  const onEntering = jest.fn();
  const onEntered = jest.fn();
  const childrenCallback = jest.fn();
  render(
    <Transition initStage={true} isIn={true} delay={1} duration={10} onEnter={onEnter} onEntering={onEntering} onEntered={onEntered}>
      {childrenCallback}
    </Transition>
  );

  expect(childrenCallback.mock.calls.length).toBe(2);
  expect(onEnter.mock.calls.length).toBe(1);
  expect(onEntering.mock.calls.length).toBe(0);
  expect(onEntered.mock.calls.length).toBe(0);

  await act(() => sleep(2));

  expect(childrenCallback.mock.calls.length).toBe(3);
  expect(onEnter.mock.calls.length).toBe(1);
  expect(onEntering.mock.calls.length).toBe(1);
  expect(onEntered.mock.calls.length).toBe(0);

  await act(() => sleep(12));

  expect(childrenCallback.mock.calls.length).toBe(4);
  expect(onEnter.mock.calls.length).toBe(1);
  expect(onEntering.mock.calls.length).toBe(1);
  expect(onEntered.mock.calls.length).toBe(1);
});

test("invokes on{Exit, Exiting, Exited} events", async () => {
  const onExit = jest.fn();
  const onExiting = jest.fn();
  const onExited = jest.fn();
  const childrenCallback = jest.fn();
  const { rerender } = render(
    <Transition isIn={true} delay={1} duration={10} onExit={onExit} onExiting={onExiting} onExited={onExited}>
      {childrenCallback}
    </Transition>
  );

  await act(() => sleep(1));
  expect(childrenCallback.mock.calls.length).toBe(1);
  expect(childrenCallback.mock.calls[0][0]).toBe(TransitionStages.Entered);

  rerender(
    <Transition delay={1} duration={10} onExit={onExit} onExiting={onExiting} onExited={onExited}>
      {childrenCallback}
    </Transition>
  );

  expect(childrenCallback.mock.calls.length).toBe(3);
  expect(childrenCallback.mock.calls[2][0]).toBe(TransitionStages.Exit);

  expect(onExit.mock.calls.length).toBe(1);
  expect(onExiting.mock.calls.length).toBe(0);
  expect(onExited.mock.calls.length).toBe(0);

  await act(() => sleep(2));

  expect(childrenCallback.mock.calls.length).toBe(4);
  expect(onExit.mock.calls.length).toBe(1);
  expect(onExiting.mock.calls.length).toBe(1);
  expect(onExited.mock.calls.length).toBe(0);

  await act(() => sleep(12));

  expect(childrenCallback.mock.calls.length).toBe(5);
  expect(onExit.mock.calls.length).toBe(1);
  expect(onExiting.mock.calls.length).toBe(1);
  expect(onExited.mock.calls.length).toBe(1);
});

test("invokes on{Exit, Exiting, Exited} events initStage", async () => {
  const onExit = jest.fn();
  const onExiting = jest.fn();
  const onExited = jest.fn();
  const childrenCallback = jest.fn();
  render(
    <Transition initStage={true} delay={1} duration={10} onExit={onExit} onExiting={onExiting} onExited={onExited}>
      {childrenCallback}
    </Transition>
  );

  expect(childrenCallback.mock.calls.length).toBe(2);
  expect(onExit.mock.calls.length).toBe(1);
  expect(onExiting.mock.calls.length).toBe(0);
  expect(onExited.mock.calls.length).toBe(0);

  await act(() => sleep(2));

  expect(onExit.mock.calls.length).toBe(1);
  expect(onExiting.mock.calls.length).toBe(1);
  expect(onExited.mock.calls.length).toBe(0);

  await act(() => sleep(12));

  expect(onExit.mock.calls.length).toBe(1);
  expect(onExiting.mock.calls.length).toBe(1);
  expect(onExited.mock.calls.length).toBe(1);
});
