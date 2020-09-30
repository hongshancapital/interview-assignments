import { renderHook } from "@testing-library/react-hooks";
import useTransition, { Stages as TransitionStages, useCheckStageChanged } from "./use-transition";

describe("test useCheckStageChanged hook", () => {
  describe('initial step', () => {
    test('should returns false when initial stage is undefined', () => {
      const { result } = renderHook(() => useCheckStageChanged(undefined));
      expect(result.current).toBeFalsy();
    });
    test('should return false when pass a TransitionStage', () => {
      const { result } = renderHook(() => useCheckStageChanged(TransitionStages.Enter));
      expect(result.current).toBeFalsy();
    });
  });

  test('should returns false when stage is same', () => {
    const { result, rerender } = renderHook<{
      stage: TransitionStages | undefined;
    }, ReturnType<typeof useCheckStageChanged>>(({stage}) => {
      return useCheckStageChanged(stage);
    }, {
      initialProps: {
        stage: TransitionStages.Enter,
      },
    });

    rerender({stage: TransitionStages.Enter});

    expect(result.current).toBeFalsy();
  });

  test('should returns true when stage changed', () => {
    const { result, rerender } = renderHook<{
      stage: TransitionStages | undefined;
    }, ReturnType<typeof useCheckStageChanged>>(({stage}) => {
      return useCheckStageChanged(stage);
    }, {
      initialProps: {
        stage: undefined,
      },
    });

    rerender({stage: TransitionStages.Enter});

    expect(result.current).toBeTruthy();
  });
});

describe("test useTransition hook", () => {
  test('initial stage is entered if isIn is true', async () => {
    const { result } = renderHook(() => {
      return useTransition(true, 20);
    });

    expect(result.current).toBe(TransitionStages.Entered);
  });

  test('initial stage is exited if isIn is false', async () => {
    const { result } = renderHook(() => {
      return useTransition(false, 20);
    });

    expect(result.current).toBe(TransitionStages.Exited);
  });

  test('init stage if initStage is true and isIn is true', async () => {
    const { result, waitForNextUpdate } = renderHook(() => {
      return useTransition(true, 20, 10, true);
    });

    expect(result.current).toBe(TransitionStages.Enter);
    await waitForNextUpdate();
    expect(result.current).toBe(TransitionStages.Entering);
    await waitForNextUpdate();
    expect(result.current).toBe(TransitionStages.Entered);
  });

  test('init stage if initStage is true and isIn is false', async () => {
    const { result, waitForNextUpdate } = renderHook(() => {
      return useTransition(false, 20, 10, true);
    });

    expect(result.current).toBe(TransitionStages.Exit);
    await waitForNextUpdate();
    expect(result.current).toBe(TransitionStages.Exiting);
    await waitForNextUpdate();
    expect(result.current).toBe(TransitionStages.Exited);
  });

  test('transitioning stage', async () => {
    const { result, rerender, waitForNextUpdate } = renderHook(({isIn}) => {
      return useTransition(isIn, 20, 10);
    }, {
      initialProps: {
        isIn: false,
      },
    });

    expect(result.current).toBe(TransitionStages.Exited);

    rerender({ isIn: true });
    expect(result.current).toBe(TransitionStages.Enter);
    await waitForNextUpdate();
    expect(result.current).toBe(TransitionStages.Entering);
    await waitForNextUpdate();
    expect(result.current).toBe(TransitionStages.Entered);

    rerender({ isIn: false });
    expect(result.current).toBe(TransitionStages.Exit);
    await waitForNextUpdate();
    expect(result.current).toBe(TransitionStages.Exiting);
    await waitForNextUpdate();
    expect(result.current).toBe(TransitionStages.Exited);
  });
});
