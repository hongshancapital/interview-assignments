import React from 'react';
import { fireEvent, render, screen } from '@testing-library/react';

import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';

import Carousel from './components/Carousel';

const imgsPrefix = 'carousel-imgs-';
const anchorsPrefix = 'carousel-anchors-';
const switchDuration = 0.5;
const previewDuration = 2;

const strings = [
  iphone,
  tablet,
  airpods,
];

const objects = [
  {
    title: 'xPhone',
    text: 'Lots to love. Less to spend.Starting at $399.',
    img: iphone,
  },
  {
    title: 'Tablet',
    text: 'Just the right amount of everything.',
    img: tablet,
  },
  {
    title: 'Buy a Tablet or Phone for college.Get arPods.',
    img: airpods,
  },
];

test('renders Carousel with list=strings', () => {
  const { getByTestId } = render(<Carousel list={strings} />);

  strings.forEach((string) => {
    const element = getByTestId(`${imgsPrefix}${string}`);
    expect(element).toBeInTheDocument();
  });
});

test('renders Carousel with list=objects', () => {
  const { getByTestId, getByText } = render(<Carousel list={objects} />);

  objects.forEach((object) => {
    const { img, title, text } = object;

    const imgElement = getByTestId(`${imgsPrefix}${img}`);
    expect(imgElement).toBeInTheDocument();

    if (title) {
      const titleElement = getByText(title);
      expect(titleElement).toBeInTheDocument();
    }

    if (text) {
      const textElement = getByText(text);
      expect(textElement).toBeInTheDocument();
    }
  });
});

test('show first img in Carousel', () => {
  const { getByTestId } = render(<Carousel list={objects} />);

  const element = getByTestId(`${imgsPrefix}0`);
  expect(element).toBeInTheDocument();
});

test('show second img in Carousel after first', () => {
  const { getByTestId } = render(<Carousel list={objects} />);

  const anchorElement = getByTestId(`${anchorsPrefix}0`);
  fireEvent.animationEnd(anchorElement);

  const element = getByTestId(`${imgsPrefix}1`);
  expect(element).toBeInTheDocument();
});

test('show first img in Carousel after loop', () => {
  const { getByTestId } = render(<Carousel list={objects} />);

  objects.forEach((object, index) => {
    const { img } = object;

    const anchorElement = getByTestId(`${anchorsPrefix}${index}`);
    fireEvent.animationEnd(anchorElement);

    const imgElement = getByTestId(`${imgsPrefix}${img}`);
    fireEvent.transitionEnd(imgElement);
  });

  const element = getByTestId(`${imgsPrefix}0`);
  expect(element).toBeInTheDocument();
});

test('click anchor to show img', () => {
  const { getByTestId } = render(<Carousel list={objects} />);

  const anchorElement = getByTestId(`${anchorsPrefix}1`);
  fireEvent.click(anchorElement);

  const element = getByTestId(`${imgsPrefix}1`);
  expect(element).toBeInTheDocument();
});
