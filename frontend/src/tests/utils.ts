import { act } from 'react-dom/test-utils';

export function windowResize(x: number, y: number) {
  window.innerWidth = x;
  window.innerHeight = y;
  window.dispatchEvent(new Event('resize'));
}

/**
 * Copy from ant-design: https://github.com/ant-design/ant-design/blob/master/tests/utils.tsx#L77
 * Wait for a time delay. Will wait `advanceTime * times` ms.
 *
 * @param advanceTime Default 1000
 * @param times Default 20
 */
export async function waitFakeTimer(advanceTime = 1000, times = 20) {
  for (let i = 0; i < times; i += 1) {
    // eslint-disable-next-line no-await-in-loop
    await act(async () => {
      await Promise.resolve();

      if (advanceTime > 0) {
        jest.advanceTimersByTime(advanceTime);
      } else {
        jest.runAllTimers();
      }
    });
  }
}
