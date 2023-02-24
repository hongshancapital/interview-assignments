import { render } from '@testing-library/react';
import App from './App';

describe('App test', () => {
  test('Should render carousel', () => {
    const { container } = render(<App />);

    const carousel = container.querySelector('.carousel')
    expect(carousel).toBeInTheDocument()
  });

  test('Should render banner', () => {
    const { getByText } = render(<App />);

    const banner1 = getByText(/Lots to love. Less to spend/i);
    expect(banner1).toBeInTheDocument();
    const banner2 = getByText(/Just the right amount of everything./i);
    expect(banner2).toBeInTheDocument();
    const banner3 = getByText(/Buy a Tablet or xPhone for college./i);
    expect(banner3).toBeInTheDocument();
  });
})
