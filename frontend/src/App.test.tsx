import { render, screen } from '@testing-library/react';
import App from './App';

test('Carousel render', () => {
    render(<App />)
    const linkElement = screen.getByTestId('carousel');
    expect(linkElement).toBeInTheDocument();
})