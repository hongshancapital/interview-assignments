import { render, fireEvent } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { sliders, slidersEmpty, slidersWithTooManyData } from '../mock/sliders';
import Carousel from './Carousel';
describe('Component test -----  Carousel', () => {
  test('api test', () => {
    const mockDataList = [sliders, slidersEmpty, slidersWithTooManyData];
    for (const data of mockDataList) {
      const { container } = render(<Carousel sliders={data} />);
      expect(container.getElementsByClassName('carousel-item')?.length).toBe(
        data.length
      );
    }
  });

  test('user-event test ----- switch slider', () => {
    const { container } = render(<Carousel sliders={sliders} />);
    const indicatorList = container.getElementsByClassName(
      'carousel-indicator-item'
    );
    for (let i = 0; i < indicatorList.length; i++) {
      let indicator = indicatorList[i];
      userEvent.click(indicator);
      expect(
        container.getElementsByClassName('carousel-container')[0]
      ).toHaveStyle(`transform: translate(-${i * global.innerWidth}px, 0)`);
    }
  });

  test('animation test ----- different interval', async () => {
    const intervalList = [1000, 2000];
    for (const interval of intervalList) {
      const { container } = render(
        <Carousel sliders={sliders} interval={interval} />
      );
      const carouselContainer = container.getElementsByClassName(
        'carousel-indicator-item'
      );
      const logSpy = jest.spyOn(console, 'log');
      fireEvent.animationEnd(carouselContainer[0]);
      expect(logSpy).toHaveBeenCalledWith('auto');
    }
  });
});
