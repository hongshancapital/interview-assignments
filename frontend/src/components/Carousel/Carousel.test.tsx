import { render, screen } from '@testing-library/react';
import Carousel from './index';

test('renders Carousel Component', () => {
    render(<Carousel showSwitch>
        <div>第一个</div>
        <div>第二个</div>
        <div>第三个</div>
    </Carousel>);
    const linkElement = screen.getByTestId('carousel');
    expect(linkElement).toBeInTheDocument();

    const text = screen.getByText(/第二个/i);
    expect(text).toBeInTheDocument();

    const next = screen.getByTitle(/下一页/i);
    expect(next).toBeInTheDocument();
});
