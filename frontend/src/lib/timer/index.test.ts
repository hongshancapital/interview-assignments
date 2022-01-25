import React from "react";
import { act } from "@testing-library/react";
import Timer from "./index";

beforeEach(() => {
  jest.useFakeTimers();
});

afterEach(() => {
  jest.useRealTimers();
});

test("the timer should tick periodically", () => {
  const INTERVAL = 3000;
  const onTick = jest.fn();
  new Timer({
    interval: INTERVAL,
    onTick,
  });
  expect(onTick).toHaveBeenCalledTimes(1);
  act(() => {
    jest.advanceTimersByTime(INTERVAL);
  });
  expect(onTick).toHaveBeenCalledTimes(2);
});

test("the timer should not auto start", () => {
  const INTERVAL = 3000;
  const onTick = jest.fn();
  new Timer({
    interval: INTERVAL,
    autoStart: false,
    onTick,
  });
  expect(onTick).not.toBeCalled();
});

test("the timer starts manually", () => {
  const INTERVAL = 3000;
  const onTick = jest.fn();
  const timer = new Timer({
    interval: INTERVAL,
    autoStart: false,
    onTick,
  });
  timer.start();
  expect(onTick).not.toBeCalled();
  act(() => {
    jest.advanceTimersByTime(INTERVAL);
  });
  expect(onTick).toHaveBeenCalledTimes(1);
});

test("the timer start with onTick trigger immediately", () => {
  const INTERVAL = 3000;
  const onTick = jest.fn();
  const timer = new Timer({
    interval: INTERVAL,
    autoStart: false,
    onTick,
  });
  timer.start(true);
  expect(onTick).toHaveBeenCalledTimes(1);
});

test("the timer should not start when it is already started", () => {
  const INTERVAL = 3000;
  const onTick = jest.fn();
  const timer = new Timer({
    interval: INTERVAL,
    onTick,
  });
  timer.start();
  timer.start();
  expect(onTick).toHaveBeenCalledTimes(1);
});

test("the timer stops", () => {
  const INTERVAL = 3000;
  const onTick = jest.fn();
  const timer = new Timer({
    interval: INTERVAL,
    onTick,
  });
  timer.stop();
  act(() => {
    jest.advanceTimersByTime(INTERVAL);
  });
  expect(onTick).toHaveBeenCalledTimes(1);
});

test("the timer should throw error when the interval is invalid", () => {
  expect(() => {
    new Timer({
      interval: 0,
    });
  }).toThrow(`Invalid timer interval: 0`);
});
