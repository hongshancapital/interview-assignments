/*
 * @Author: danjp
 * @Date: 2022/6/25 11:37
 * @LastEditTime: 2022/6/25 11:37
 * @LastEditors: danjp
 * @Description:
 */
import { act, renderHook } from '@testing-library/react-hooks';
import useResize from './useResize';

const setup = (fn: () => void) => (
  renderHook(() => useResize(fn))
);

describe('useResize', () => {
  it('should be defined', () => {
    expect(useResize).toBeDefined();
  });
  
  it ('should window width changed', () => {
    const fn = jest.fn();
  
    function changeSize(width: number) {
      act(() => {
        (global as any).innerWidth = width;
        (global as any).dispatchEvent(new Event('resize'));
      });
    }
  
    setup(fn);
  
    changeSize(1440);
    expect(fn).toBeCalledTimes(1);
  });
});
