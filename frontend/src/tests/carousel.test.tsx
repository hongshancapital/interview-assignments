import { render, screen } from '@testing-library/react';
// import renderer from 'react-test-renderer';
import Carousel from '../components/Carousel';
import { SLIDES } from '../constants/carousel';

it('carousel component work just fine', () => {
  render(<Carousel slides={SLIDES} />);

  const xPhoneText = screen.getByText(/Lots to love, Less to spend./i);
  expect(xPhoneText).toBeInTheDocument();

  const tabletText = screen.getByText(/Just the right amount of everything./i);
  expect(tabletText).toBeInTheDocument();

  const tableTitle = screen.getByText('Tablet');
  expect(tableTitle).toHaveClass('item-title');
  // screen.debug();
});
