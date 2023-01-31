import { render, screen } from '@testing-library/react';
import { Indicators } from './Indicators';

describe('Indicators', () => {
  it('renders correctly', () => {
    const { asFragment } = render(
      <Indicators length={3} currentActiveIndex={0} duration={1000} />
    );
    expect(asFragment()).toMatchSnapshot();
  });
  it('shows the progress and has animation for active indicator', () => {
    render(<Indicators length={3} currentActiveIndex={1} duration={1000} />);

    expect(screen.getAllByLabelText('indicator progress')[1]).toHaveStyle({
      'opacity': 1,
      animation: 'progress 1000ms linear',
    });
  });
  it('hides the progress and set animation to none for inactive indicator', () => {
    render(<Indicators length={3} currentActiveIndex={1} duration={1000} />);

    expect(screen.getAllByLabelText('indicator progress')[0]).toHaveStyle({
      'opacity': 0,
      animation: 'none',
    });
  });
});
