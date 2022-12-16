import { render, screen } from '@testing-library/react';
import AutoProgress, { AutoProgressProps } from '../AutoProgress';
import { sleep } from '../common';

import './err-handler';

test('AutoProgress component props test', async ()=>{
  const statusEnum = {
    Pending: 'pending',
    Fulfilled: 'fulfilled'
  };
  let state = statusEnum.Pending;
  const baseProps: AutoProgressProps = {
    duration: 1000,
    stoped: true,
    paused: false,
    onEnd: ()=>{state = statusEnum.Fulfilled;}
  };

  const compareProgress = async (val: number, range: number) => {
    const progressBar = await screen.queryByTestId('carousel-progress-bg');
    const progress = parseFloat(progressBar?.style.width || '0');
    expect(Math.abs(val - progress)).toBeLessThanOrEqual(range);
  };
  const { rerender } = render(<AutoProgress {...baseProps}/>);
  await sleep(100);
  // 设置了stopd: true,进度条不会开始
  compareProgress(0, 0);
  rerender(<AutoProgress {...baseProps} stoped={false} />);
  await sleep(500);
  rerender(<AutoProgress {...baseProps} stoped={false} paused={true}/>);
  await sleep(200);
  // 500ms时将paused设为true，此时进度条应在50%左右
  compareProgress(50, 5);
  rerender(<AutoProgress {...baseProps} stoped={false} paused={false}/>);
  await sleep(700);
  // 超过duration，进度应为100%
  compareProgress(100, 0);
  // 完毕时触发了onEnd事件
  expect(state).toEqual(statusEnum.Fulfilled);

});