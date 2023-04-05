import App from './App';
import { mountTest } from './tests/mountTest';

// test('renders learn react link', () => {
//   const { getByText } = render(<App />);
//   const linkElement = getByText(/learn react/i);
//   expect(linkElement).toBeInTheDocument();
// });

describe('App', () => {
  mountTest(App);
});
