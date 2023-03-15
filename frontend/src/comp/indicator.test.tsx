import { render, fireEvent } from '@testing-library/react';
import Indicator from './indicator';

describe('test indicator', () => {
  test('count is right', () => {
    const ret = render(<Indicator count={2} current={0} duration={100} onTransitionEnd={() => {}} />);
    expect(ret.container.querySelectorAll('.indicator-block').length).toBe(2);
  });
  test('active count is right', () => {
    const ret = render(<Indicator count={2} current={1} duration={100} onTransitionEnd={() => {}} />);
    expect(ret.container.querySelectorAll('.indicator-block-inner').length).toBe(2);
    ret.rerender(<Indicator count={2} current={0} duration={100} onTransitionEnd={() => {}} />)
    expect(ret.container.querySelectorAll('.indicator-block-inner').length).toBe(1);
  });
  test('fired onTransitionEnd right', async () => {
    const duration = 100;
    const callback = jest.fn();
    const ret = render(<Indicator count={2} current={0} duration={duration} onTransitionEnd={callback} />);
    expect(callback).not.toBeCalled();
    const dom = ret.container.querySelector('.indicator-block-inner');
    fireEvent.transitionEnd(dom!, {});
    expect(callback).toBeCalled();
    expect(callback).toBeCalledTimes(1);
  })
})