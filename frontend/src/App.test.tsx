import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

describe('test App', () => {
  it('renders correctly', () => {
    const { container } = render(<App />);
    expect(container.firstChild).toMatchSnapshot();
  });

  it('mount correctly', () => {
    expect(() => render(<App />)).not.toThrow();
  });
});
