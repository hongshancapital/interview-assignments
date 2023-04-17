import { render, fireEvent } from '@testing-library/react';
import Indicator from './index';

describe('Indicator', () => {
  test('mount', () => {
    const total = 5;
    const cur = 0;
    const { container } = render(
      <Indicator
        total={total}
        cur={cur}
        onIndexSelected={() => {}}
        onTimingEnd={() => {}}
      />,
    );

    const ul = container.getElementsByTagName('ul')[0];
    expect(ul).not.toBeUndefined();
    expect(ul.children.length).toBe(total);

    const arr = Array.from(ul.children).reduce<number[]>(
      (acc, curItem, curIndex) => {
        if (curItem.className.split(' ').includes('cur')) {
          return [...acc, curIndex];
        } else {
          return acc;
        }
      },
      [],
    );
    expect(arr.length === 1 && arr[0] === cur).toBeTruthy();
  });

  test('should trigger when dot click', () => {
    const total = 5;
    const cur = 0;
    const onIndexSelected = jest.fn();
    const { container } = render(
      <Indicator
        total={total}
        cur={cur}
        onIndexSelected={onIndexSelected}
        onTimingEnd={() => {}}
      />,
    );

    fireEvent.click(container.getElementsByTagName('li')[cur]);
    expect(onIndexSelected).toHaveBeenCalledTimes(0);

    const indexToClick = 3;
    fireEvent.click(container.getElementsByTagName('li')[indexToClick]);
    expect(onIndexSelected).toHaveBeenCalledWith(indexToClick);
  });

  test('should trigger when animation end', async () => {
    const total = 5;
    const cur = 0;
    const onTimingEnd = jest.fn();
    const { container } = render(
      <Indicator
        total={total}
        cur={cur}
        onIndexSelected={() => {}}
        onTimingEnd={onTimingEnd}
      />,
    );

    fireEvent.animationEnd(container.getElementsByTagName('li')[cur]);
    expect(onTimingEnd).toHaveBeenCalledTimes(1);
  });
});
