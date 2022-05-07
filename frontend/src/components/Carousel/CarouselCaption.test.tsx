import React from 'react';
import { render } from '@testing-library/react';
import CarouselCaption from './CarouselCaption';

test('renders carousel caption with given text color', () => {
  const { container } = render(
    <CarouselCaption title="" subtitle="" color={ 'rgb(11, 11, 111)' } />
  );
  expect(container.firstChild).toHaveStyle({ color: 'rgb(11, 11, 111)' });
});

test('renders carousel caption with given title ', () => {
  const { getByText } = render(
    <CarouselCaption title="title" subtitle="" color={ '#fff' } />
  );
  const title = getByText(/title/);
  expect(title).toBeInTheDocument();
  expect(title).toHaveStyle({
    fontSize: '60px',
    whiteSpace: 'pre-line'
  });
});

test('renders carousel caption with given subtitle ', () => {
  const { getByText } = render(
    <CarouselCaption title="" subtitle="subtitle" color={ '#fff' } />
  );
  const subtitle = getByText(/subtitle/);
  expect(subtitle).toBeInTheDocument();
  expect(subtitle).toHaveStyle({
    fontSize: '40px',
    marginTop: '20px'
  });
});