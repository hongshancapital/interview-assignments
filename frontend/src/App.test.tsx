import { render, screen } from '@testing-library/react';
import App from './App';
import { slides } from './fixtures';

// TODO: use jest fake timers to test the carousel visually
//       expect each slide title toBeVisible every 2 seconds
//       might conflicts with RTL's waitFor() function
//       consider set timeout / retry option of waitFor
describe('App component', () => {
  beforeEach(() => {
    render(<App />);
  });

  test('renders without crashing', () => {
    expect(
      screen.getByRole('region', { name: /yangbot-homework/i }),
    ).toBeInTheDocument();
  });

  test('renders the correct number of slides', () => {
    const slidesRoles = screen.getAllByRole('group', { name: /slides/i });
    expect(slidesRoles).toHaveLength(slides.length);
  });

  test('renders the correct number of indicators', () => {
    const indicators = screen.getAllByRole('tab');
    expect(indicators).toHaveLength(3);
  });

  test('displays the correct content on each slide', () => {
    const slideTitles = screen.getAllByRole('heading');
    slides.forEach((slide, i) => {
      expect(slideTitles[i]).toHaveTextContent(
        slide.title.replaceAll('\n', ' '),
      );
    });
  });
});
