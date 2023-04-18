import { render } from '@testing-library/react';
import { Carousel } from './Carousel';

beforeAll(() => {
  jest.useFakeTimers();
});

test('test carousel data props react element', () => {
  const data = [
    {
      header: 'xPhone',
      subHeader: [
        <div key="test-233" id="test-header">
          hello world 233
        </div>,
      ],
      className: 'bg-[#111111] text-[#FAFAFA] carousel-bg bg-iphone',
    },
    {
      header: 'Tablet',
      subHeader: <p id="test-sub-header">hello world</p>,
      className: 'bg-[#FAFAFA] carousel-bg bg-tablet',
    },
  ];
  const { container, getByText } = render(<Carousel data={data} />);

  expect(getByText(/xPhone/i)).toBeInTheDocument();
  expect(getByText(/Tablet/i)).toBeInTheDocument();
  expect(container.querySelector('#test-header')).toBeInTheDocument();

  expect(container.querySelector('#test-sub-header')).toBeInTheDocument();
});

test('test carousel slide', () => {
  const data = [
    {
      header: 'xPhone',
      subHeader: ['Lots to Love. Less to spend.', 'Starting at $399.'],
      className: 'bg-[#111111] text-[#FAFAFA] carousel-bg bg-iphone',
    },
    {
      header: 'xPhone',
      subHeader: ['Lots to Love. Less to spend.', 'Starting at $399.'],
      className: 'bg-[#111111] text-[#FAFAFA] carousel-bg bg-iphone',
    },
    {
      header: 'Tablet',
      subHeader: 'Just the right amount of everything',
      className: 'bg-[#FAFAFA] carousel-bg bg-tablet',
    },
  ];
  const { container } = render(<Carousel data={data} duration={1000} />);
  const carouselContainer = container.querySelector<HTMLUListElement>(
    '.carousel-item-container'
  );
  const carouselSlides = Array.from(
    container.querySelectorAll<HTMLLIElement>('.slide')
  );

  setTimeout(() => {
    expect(carouselContainer?.style.transform).toMatch(
      'translate3d(-100%, 0, 0)'
    );
    expect(carouselSlides[0].style.transform).toMatch(/scale3d\(0,/);
    expect(carouselSlides[1].style.transform).not.toMatch(/scale3d\(0,/);
  }, 1300);

  setTimeout(() => {
    expect(carouselContainer?.style.transform).toMatch(
      'translate3d(-200%, 0, 0)'
    );
    expect(carouselSlides[1].style.transform).toMatch(/scale3d\(0,/);
    expect(carouselSlides[2].style.transform).not.toMatch(/scale3d\(0,/);
  }, 2300);
  setTimeout(() => {
    expect(carouselContainer?.style.transform).toMatch('translate3d(0%, 0, 0)');
    expect(carouselSlides[1].style.transform).toMatch(/scale3d\(0,/);
    expect(carouselSlides[0].style.transform).not.toMatch(/scale3d\(0,/);
  }, 3300);

  jest.advanceTimersByTime(3300);
});
