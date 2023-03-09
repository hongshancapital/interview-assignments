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
        const { unmount, rerender } = render(<Carousel><h1>hello</h1><h1>world</h1></Carousel>);
        expect(() => {
            rerender(<Carousel><h1>hello</h1><h1>world</h1></Carousel>);
            unmount();
        }).not.toThrow();
    });

    it('snapshot', () => {
        const component = renderer.create(
            <Carousel><h1>hello</h1><h1>world</h1></Carousel>,
        );
        let tree = component.toJSON();
        expect(tree).toMatchSnapshot();
    })
})

