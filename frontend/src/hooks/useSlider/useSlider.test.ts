import { renderHook, act } from '@testing-library/react-hooks';
import useSlider from './useSlider';
import { SlideDirection } from '../../types/carousel';

const ref = { current: document.createElement('div') };

const types = {
  count: 3,
  duration: 1000
}
const sleep = () => new Promise(resolve => setTimeout(resolve, types.duration));

describe('useSlider()', () => {
  it('should initIndex === 1, when initialization setting', async () => {
    const { result } = renderHook(() => useSlider(ref, types));
    const [ initIndex, { slideToDirection, slideToIndex }] = result.current;
    expect(initIndex).toBe(1);
    expect(slideToDirection).toBeDefined();
    expect(slideToIndex).toBeDefined();
    expect(ref.current).toHaveStyle(`transform: translateX(-100%)`);
  });

  it('switch to the next, when slide be called and param is SlideDirection.Right', async () => {
    const { result } = renderHook(() => useSlider(ref, types));
    act(() => {
      result.current[1].slideToDirection(SlideDirection.Right);
    });
    await sleep();
    expect(ref.current).toHaveStyle(`transform: translateX(-200%)`);
    expect(result.current[0]).toBe(2);
  });

  it('switch to the previous, when slide be called and params is SlideDirection.Left', async () => {
    const { result } = renderHook(() => useSlider(ref, types));
  
    act(() => {
      result.current[1].slideToDirection(SlideDirection.Left);
    });
    await sleep();
    expect(ref.current).toHaveStyle(`transform: translateX(-000%)`);
    expect(result.current[0]).toBe(3);
  });

  it('switch to the specified rotating image, when slideTo is called and params is a valid value', async () => {
    const { result } = renderHook(() => useSlider(ref, types));

    act(() => {
      result.current[1].slideToIndex(3);
    });
    await sleep();
    expect(ref.current).toHaveStyle(`transform: translateX(-300%)`);
    expect(result.current[0]).toBe(3);
  });

})