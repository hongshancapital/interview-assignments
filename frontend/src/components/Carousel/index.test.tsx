import { render } from '@testing-library/react';
import Carousel from './index';
import { data } from '../../data';

describe('Carousel test', () => {
  test('render title and desc correctly', async () => {
    const { getByText } = render(<Carousel data={data} duration={3000} />);
    data.forEach((item) => {
      item.titles.forEach((title) => {
        expect(getByText(title)).toBeInTheDocument();
      });
      item.descs?.forEach((desc) => {
        expect(getByText(desc)).toBeInTheDocument();
      });
    });
  });
  test('render dot correctly', async () => {
    const { container } = render(<Carousel data={data} duration={3000} />);
    const dotElements = container.querySelectorAll('.carousel-dot');
    expect(dotElements.length).toEqual(data.length);
  });
  test('isActive correctly', async () => {
    const { container } = render(<Carousel data={data} duration={3000} />);
    const activeElement = container.querySelector('.active');
    expect(activeElement).toBeInTheDocument();
  });
});
