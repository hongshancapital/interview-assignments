import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

describe('renders learn react link', () => {
  it('should render <App />', () => {
    const { container } = render(<App />);
    const app = container.querySelector('.App');
    expect(app).toBeInTheDocument();
  });

  it('should render <Carousel />', () => {
    const { container } = render(<App />);
    const carousel = container.querySelector('.carousel');
    expect(carousel).toBeInTheDocument();
  });
});
