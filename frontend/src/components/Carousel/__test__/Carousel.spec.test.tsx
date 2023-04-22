import { render, screen, waitFor, fireEvent } from '@testing-library/react'
import Carousel from '../index'
import styles from '../index.module.scss';

const wait = (time: number) => new Promise(resolve => setTimeout(resolve, (time)))

describe('Carousel', () => {
  const carouselItems: React.ReactNode[] = [
    <div>item1</div>,
    <div>item2</div>,
    <div>item3</div>,
  ]

  it('should has items', async () => {
    const wrapper = render(<Carousel items={carouselItems}/>);
    const [item1, item2, item3] = await Promise.all([
      wrapper.findAllByText('item1'),
      wrapper.findAllByText('item2'),
      wrapper.findAllByText('item3'),
    ])
    expect(item1.length).toBe(2);
    expect(item2.length).toBe(1);
    expect(item3.length).toBe(1);
  })

  it('should has dots', async () => {
    const { container } = render(<Carousel items={carouselItems}/>);
    const dotContainer = container.querySelector(`.${styles.carouselDotContainer}`)
    const dotItem = container.querySelector(`.${styles.carouselDotItem}`)
    expect(dotContainer).toBeTruthy();
    expect(dotItem).toBeTruthy();
  })

  it('number of dots matched items', async () => {
    const { container } = render(<Carousel items={carouselItems}/>);
    const itemLength = carouselItems.length;
    const dotLength = Array.from(container.querySelectorAll(`.${styles.carouselDotItem}`)).length;
    expect(itemLength).toEqual(dotLength);
  })

  it('should have active class when click on it', async () => {
    const { container } = render(<Carousel items={carouselItems}/>);
    const dotList = Array.from(container.querySelectorAll(`.${styles.carouselDotItem}`));
    const clickTarget = dotList[2];
    fireEvent(clickTarget, new MouseEvent('click', {
      bubbles: true,
      cancelable: true,
    }));
    await wait(1000);
    expect(clickTarget.classList.contains(styles.active)).toBeTruthy();
  })
})