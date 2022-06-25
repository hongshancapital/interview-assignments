/*
 * @Author: danjp
 * @Date: 2022/6/25 13:39
 * @LastEditTime: 2022/6/25 13:39
 * @LastEditors: danjp
 * @Description:
 */
import { renderHook, act } from '@testing-library/react-hooks';
import useSlide, { SlideParams } from './useSlide';

const setup = (params: SlideParams) => (
  renderHook(() => useSlide(params))
);

describe('useSlide', () => {
  it('should be defined', () => {
    expect(useSlide).toBeDefined();
  });
  
  it('should default `current` value', () => {
    const { result } = setup({ count: 3 });
    expect(result.current.current).toEqual(0);
  });
  
  it('should `initialIndex` is not empty', () => {
    const { result } = setup({ count: 3, initialIndex: 2 });
    expect(result.current.current).toEqual(2);
  });
  
  it('should call the `slideGoTO` to show the next carousel', () => {
    const { result } = setup({ count: 3 });
    act(() => {
      result.current.slideGoTo();
    });
    expect(result.current.current).toEqual(1);
  })
});
