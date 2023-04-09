import { render,act } from '@testing-library/react';
import Carousel from '../components/Carousel';

const SLIDE_DATA = [
  {
    title: 'xPhone',
    texts: ['Lots to love.Less to spend.', 'Starting at $399.'],
    className: 'App__slide__phone',
  },
  {
    title: 'Tablet',
    texts: ['Just the right amount of everything'],
    className: 'App__slide__tablet',
  },
  {
    title: (
      <>
        Buy a Tablet or xPhone for college .<br />
        Get arPods.
      </>
    ),
    texts: [],
    className: 'App__slide__arpods',
  },
];
const COUNT = SLIDE_DATA.length;
const Component = (
  <Carousel count={COUNT} speed={300} delay={3000}>
    {SLIDE_DATA.map((el, idx) => (
      <Carousel.Slide
        className={`App__slide ${el.className || ''}`}
        key={idx}
      >
        <h2 className="App__title">{el.title}</h2>
        {el.texts.map((text, idx2) => (
          <p className="App__text" key={idx2}>
            {text}
          </p>
        ))}
      </Carousel.Slide>
    ))}
</Carousel>
);

describe('Carousel', () => {
  it('should render', () => {
    const { getAllByTestId } = render(
      <div data-testid="root">
        {Component}
      </div>
    );

    const root = getAllByTestId('root');
    const paginations = root[0].querySelectorAll('.carousel__pagination__item');
    expect(paginations.length).toBe(3);
  });

  it('slide after click pagination',() => {
    const { getAllByTestId } = render(
      <div data-testid="root">
        {Component}
      </div>
    );

    const root = getAllByTestId('root');
    const paginations = root[0].querySelectorAll('.carousel__pagination__item');

    act(()=>{
      (paginations[1] as HTMLElement)?.click?.();
    });

    expect(paginations[0].children.length).toBe(0);
    expect(paginations[1].children.length).toBe(1);
  });
});
