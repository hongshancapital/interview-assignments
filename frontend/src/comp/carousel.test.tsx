import { act, render } from '@testing-library/react';
import CarouselBox from './carousel';

describe('test carousel', () => {
  test('timer works, aka carousel startup', () => {
    jest.useFakeTimers();
    const tree = (
      <CarouselBox>
        <div>1</div>
        <div>1</div>
        <div>1</div>
      </CarouselBox>
    )
    const ret = render(tree);
    act(() => {
      jest.runAllTimers();
    });
    expect(ret.container.querySelectorAll('.indicator-block-inner').length).toBe(1);
  });
})