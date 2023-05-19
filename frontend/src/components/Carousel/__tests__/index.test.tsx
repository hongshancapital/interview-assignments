import React from 'react';
import { render } from '@testing-library/react';
import Carousel, { CarouselRef } from '..';

describe('Carousel', () => {

  it(`should has text`, () => {
    const text = "123";
    const { getByText } = render(
      <Carousel>
        <div>{text}</div>
      </Carousel>
    );
    const divElement = getByText(new RegExp(text, 'i'));
    expect(divElement).toBeInTheDocument();
  });

  it(`should have 3 item`, () => {
    const text = ["1", "2", "3"];
    const { container } = render(
      <Carousel>
        {
          text.map(s => (<div key={s}>{s}</div>))
        }
      </Carousel>
    );
    const dotElements = container.querySelectorAll('.carousel-item');
    expect(dotElements.length).toBe(3);
  });


  it(`should has 3 dot`, () => {
    const text = ["1", "2", "3"];
    const { container } = render(
      <Carousel>
        {
          text.map(s => (<div key={s}>{s}</div>))
        }
      </Carousel>
    );
    const dotElements = container.querySelectorAll('.dot');
    expect(dotElements.length).toBe(3);
  });

  it(`should has prev, next and go function`, () => {
    const ref = React.createRef<CarouselRef>();
    const text = ["1", "2", "3"];
    render(
      <Carousel ref={ref}>
        {
          text.map(s => (<div key={s}>{s}</div>))
        }
      </Carousel>
    );
    const { prev, next, goTo } = ref.current || {};
    expect(typeof prev).toBe('function');
    expect(typeof next).toBe('function');
    expect(typeof goTo).toBe('function');
  });
});