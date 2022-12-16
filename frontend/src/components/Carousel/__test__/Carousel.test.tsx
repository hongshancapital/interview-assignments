import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import Carousel from '../';
import { sleep } from '../common';
import './err-handler';
const { createHoverPausePlugin } = Carousel.plugins;;

test('Progress component props test', async ()=>{
  const plugins = [createHoverPausePlugin()];
  const getRender = () => (
    <Carousel duration={500} plugins={plugins}>
        <Carousel.Option>
            <div></div>
        </Carousel.Option>
        <Carousel.Option>
            <div></div>
        </Carousel.Option>
        <Carousel.Option>
            <div></div>
        </Carousel.Option>
    </Carousel>
  );
  const { rerender } = render(getRender());
  const checkActive = async (idx: number) => {
    rerender(getRender());
    const allBars = await screen.queryAllByTestId('carousel-progress-bg');
    const activeProgressBar = allBars[idx];
    const progress = parseFloat(activeProgressBar?.style.width || '0');
    expect(progress>0).toBeTruthy();
  };
  await sleep(300);
  // duration 500ms，300ms时处于第0项
  checkActive(0);
  // 鼠标放在carousel上，触发暂停，即使再等待300ms，总时长超过duration，依旧处于第0项
  await userEvent.hover(screen.getByTestId('carousel'));
  await sleep(300);
  checkActive(0);
  // 鼠标移除，解除暂停，300ms后播放第1项
  await userEvent.unhover(screen.getByTestId('carousel'));
  await sleep(300);
  checkActive(1);
  // 再等待600ms，到达第2项
  await sleep(600);
  checkActive(2);
});
