import { act, renderHook } from '@testing-library/react';
import useResize from '../useResize';

describe('[useResize]hooks: resize元素可视区域变化', () => {
  test('[#1 - useResize]用例名称：observerRef被传递给ResizeObserver', () => {
    const observerRef = { current: document.createElement('div') };
    const observeSpy = jest.spyOn(ResizeObserver.prototype, 'observe');
    act(() => {
      renderHook(() => useResize(() => {}, observerRef));
    })
    expect(observeSpy).toHaveBeenCalledWith(observerRef.current);
  });
  test('[#2 - useResize]用例名称：卸载时取消ResizeObserver', () => {
    const observerRef = { current: document.createElement('div') };
    const observeSpy = jest.spyOn(ResizeObserver.prototype, 'observe');
    const unobserveSpy = jest.spyOn(ResizeObserver.prototype, 'unobserve');
    const { unmount } = renderHook(() => useResize(() => {}, observerRef));
    expect(observeSpy).toHaveBeenCalledWith(observerRef.current);
    unmount();
    expect(unobserveSpy).toHaveBeenCalledWith(observerRef.current);
  });
});
