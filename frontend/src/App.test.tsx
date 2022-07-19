import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

import { SlideBar, SlideList, Carousel } from './Carousel'

jest.useFakeTimers();

const carouselData = [{
  id: 'xPhone',
  title: ['xPhone'],
  text: ['Lots to love.Less to spend.', 'Starting at $399.'],
}, {
  id: 'Tablet',
  title: ['Tablet'],
  text: ['Just the right amount of everything.'],
}, {
  id: 'arpods',
  title: ['Buy a Tablet or xPhone for collage.', 'Get arPods.'],
  text: [],
}]

test('renders app, test title', () => {
  const { getByText } = render(<App />);
  for (let i = 0; i < carouselData.length; i += 1) {
    const { title } = carouselData[i]
    for (let j = 0; j < title.length; j += 1) {
      const titleElement = getByText(title[j]);
      expect(titleElement).toBeInTheDocument();
    }
  }
});

test('renders app, test text', () => {
  const { getByText } = render(<App />);
  for (let i = 0; i < carouselData.length; i += 1) {
    const { text } = carouselData[i]
    for (let j = 0; j < text.length; j += 1) {
      const textElement = getByText(text[j]);
      expect(textElement).toBeInTheDocument();
    }
  }
});

test('renders app, test text', () => {
  const { getByText } = render(<App />);
  for (let i = 0; i < carouselData.length; i += 1) {
    const { text } = carouselData[i]
    for (let j = 0; j < text.length; j += 1) {
      const textElement = getByText(text[j]);
      expect(textElement).toBeInTheDocument();
    }
  }
});

test('renders app, test slide time', () => {
  render(<App />);
  expect(setTimeout).toHaveBeenCalledTimes(1);
  expect(setTimeout).toHaveBeenLastCalledWith(expect.any(Function), 5 * 1000);
});

test('render SlideBar components', () => {
  const props = {
    count: 4,
    speed: 10,
    current: 1,
  }

  const { container } = render(<SlideBar {...props} />);

  const progressElement = container.querySelectorAll('.slidebar-item');

  expect(progressElement.length).toBe(props.count);

  for (let i = 0; i < progressElement.length; i += 1) {
    const activeElement = progressElement[i].querySelector('.active');
    if (i === props.current) {
      expect(activeElement).toHaveStyle(`animation-duration: ${props.speed}s;`)
    } else {
      expect(activeElement).toBe(null);
    }
  }
})


test('render SlideList components', () => {
  const props = {
    direct: 'right',
    current: 1,
  }
  const { container, getByTestId } = render(
    <SlideList {...props}>
      <div data-testid="item-a">1</div>
      <div data-testid="item-b">2</div>
      <div data-testid="item-c">3</div>
    </SlideList>
  );

  const itembElement = getByTestId('item-b');

  expect(itembElement).toBeInTheDocument();

  const itemElement = container.querySelectorAll('.slidelist-item');

  for (let i = 0; i < itemElement.length; i += 1) {
    const slideElement = itemElement[i];
    if (i < props.current) {
      expect(slideElement).toHaveClass('left');
    } else if (i === props.current) {
      expect(slideElement).toHaveClass('active');
    } else {
      expect(slideElement).toHaveClass('right');
    }
  }
})



test('render Carousel components', () => {
  const props = {
    speed: 2
  }
  render(
    <Carousel {...props}>
      <div data-testid="item-a">1</div>
      <div data-testid="item-b">2</div>
      <div data-testid="item-c">3</div>
    </Carousel>
  );

  expect(setTimeout).toHaveBeenCalledTimes(1);
  expect(setTimeout).toHaveBeenLastCalledWith(expect.any(Function), props.speed * 1000);
})