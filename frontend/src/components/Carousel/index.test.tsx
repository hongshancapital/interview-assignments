import { render, fireEvent } from '@testing-library/react';
import Carousel from './index';

const list = ['item 1', 'item 2', 'item 3'];

describe('Carousel', () => {
  test('mount', () => {
    const { container } = render(
      <Carousel>
        {list.map((item, index) => {
          return <Carousel.Item key={index}>{item}</Carousel.Item>;
        })}
      </Carousel>,
    );

    const itemsEle = container.getElementsByClassName(
      'items',
    )[0] as HTMLElement;
    expect(itemsEle).not.toBeUndefined();
    expect(itemsEle.children.length).toBe(list.length);
    expect(itemsEle.style.width).toEqual(`${100 * list.length}%`);
  });

  test('should autoplay', async () => {
    const { container } = render(
      <Carousel autoplay>
        {list.map((item, index) => {
          return <Carousel.Item key={index}>{item}</Carousel.Item>;
        })}
      </Carousel>,
    );

    const itemsEle = container.getElementsByClassName(
      'items',
    )[0] as HTMLElement;

    expect(itemsEle.style.transform).toEqual(`translate(-0%, 0)`);
    for (let cur = 0; cur < list.length; cur++) {
      fireEvent.animationEnd(container.getElementsByTagName('li')[cur]);
      const x = cur === list.length - 1 ? 0 : (100 * (cur + 1)) / list.length;
      expect(itemsEle.style.transform).toEqual(`translate(-${x}%, 0)`);
    }
  });
});
