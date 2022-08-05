import { renderHook, act } from '@testing-library/react-hooks';
import useSlider from './useSlider';
import { SlideDirection } from '../../types/carousel';

const types = {
  count: 3,
  duration: 1000
}
const sleep = () => new Promise(resolve => setTimeout(resolve, types.duration));

describe('useSlider()', () => {
  it('should initIndex === 1, when initialization setting', async () => {
    const { result } = renderHook(() => useSlider(types.count));
    const [ initIndex, { slideToDirection, slideToIndex }] = result.current;
    expect(initIndex).toBe(0);
    expect(slideToDirection).toBeDefined();
    expect(slideToIndex).toBeDefined();
  });

  it('switch to the next, when slide be called and param is SlideDirection.Right', async () => {
    const { result } = renderHook(() => useSlider(types.count));
    act(() => {
      result.current[1].slideToDirection(SlideDirection.Right);
    });
    await sleep();
    expect(result.current[0]).toBe(1);
  });

  it('switch to the previous, when slide be called and params is SlideDirection.Left', async () => {
    const { result } = renderHook(() => useSlider(types.count));
  
    act(() => {
      result.current[1].slideToDirection(SlideDirection.Left);
    });
    await sleep();
    expect(result.current[0]).toBe(2);
  });

  it('switch to the specified rotating image, when slideTo is called and params is a valid value', async () => {
    const { result } = renderHook(() => useSlider(types.count));

    act(() => {
      result.current[1].slideToIndex(3);
    });
    await sleep();
    expect(result.current[0]).toBe(3);
  });

})