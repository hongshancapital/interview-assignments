import { render, screen } from '@testing-library/react';
import App from './App';
import { sliders } from './mock/sliders';
describe('demo data test ----- render Carousel', () => {
  test('sliders data has text', () => {
    render(<App />);
    expect(screen.getByText(`${sliders[1].title}`)).toBeInTheDocument();
  });
  test('api data has same page by use default api data', () => {
    const { container } = render(<App />);
    expect(container.getElementsByClassName('carousel-item')?.length).toBe(
      sliders.length
    );
  });
});
