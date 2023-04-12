import { render,act } from '@testing-library/react';
import Carousel from '../components/Carousel';

const SLIDE_DATA = [
  {
    title: 'xPhone',
    texts: ['Lots to love.Less to spend.', 'Starting at $399.'],
    className: 'App-slide-phone',
  },
  {
    title: 'Tablet',
    texts: ['Just the right amount of everything'],
    className: 'App-slide-tablet',
  },
  {
    title: (
      <>
        Buy a Tablet or xPhone for college .<br />
        Get arPods.
      </>
    ),
    texts: [],
    className: 'App-slide-arpods',
  },
];
const COUNT = SLIDE_DATA.length;
const Component = (
  <Carousel count={COUNT} speed={1000} duration={3000}>
    {SLIDE_DATA.map(({ className, title, texts }, idx) => (
      <Carousel.Slide
        className={`App-slide ${className || ''}`}
        key={idx}
      >
        <h2 className="App-title">{title}</h2>
        {texts.map((text, idx2) => (
          <p className="App-text" key={idx2}>
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
    const paginations = root[0].querySelectorAll('.carousel-pagination-item');
    expect(paginations.length).toBe(3);
  });

  it('slide after click pagination',() => {
    const { getAllByTestId } = render(
      <div data-testid="root">
        {Component}
      </div>
    );

    const root = getAllByTestId('root');
    const paginations = root[0].querySelectorAll('.carousel-pagination-item');

    act(()=>{
      (paginations[1] as HTMLElement)?.click?.();
    });

    expect(paginations[0].children.length).toBe(0);
    expect(paginations[1].children.length).toBe(1);
  });
});
