import renderer from 'react-test-renderer';
import Carousel from '../Carousel';
import { render } from '@testing-library/react';

describe('Carousel', () => {

    beforeEach(() => {
        jest.useFakeTimers();
    });

    afterEach(() => {
        jest.useRealTimers();
    });

    it('component could be updated and unmounted without errors', () => {
        const { unmount, rerender } = render(<Carousel />);
        expect(() => {
            rerender(<Carousel />);
            unmount();
        }).not.toThrow();
    });

    it('snapshot', () => {
        const component = renderer.create(
            <Carousel></Carousel>,
        );
        let tree = component.toJSON();
        expect(tree).toMatchSnapshot();
    })
})

