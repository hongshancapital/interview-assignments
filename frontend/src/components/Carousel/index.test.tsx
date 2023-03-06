import React from 'react';
import { act, fireEvent, render } from '@testing-library/react';
import Carousel from './index';

beforeEach(() => {
    jest.useFakeTimers();
})

afterEach(() => {
    jest.useRealTimers();
})

describe('Test carousel basic function', () => {
    test('Should init with right count sliders, and progress bars When with two sliders', () => {
        const renderResult = render(<Carousel duration={1}>
            <div>123</div>
            <div>234</div>
        </Carousel>);
        const list = renderResult.getByRole('list');
        expect(list).toBeInTheDocument();

        const listItems = renderResult.getAllByRole('listitem');
        expect(listItems).toHaveLength(2);
        expect(listItems[0].textContent).toBe('123');
        expect(listItems[1].textContent).toBe('234');

        const bars = renderResult.container.getElementsByClassName('bar-outer');
        expect(bars).toHaveLength(2);
    })

    test('Should show first slider When init', () => {
        const renderResult = render(
            <Carousel duration={1}>
                <div>123</div>
                <div>234</div>
            </Carousel>
        );

        const list = renderResult.getByRole('list');
        expect(list.style.left).toBe('0%');
    })

    test('Should show second slider When timeout first time', () => {
        const renderResult = render(
            <Carousel duration={1}>
                <div>123</div>
                <div>234</div>
            </Carousel>
        );

        const list = renderResult.getByRole('list');

        act(() => {
            jest.runAllTimers();
        });

        expect(list.style.left).toBe('-100%')
    })

    test('Should back to first slider When timeout second time Given total two sliders', async () => {
        const renderResult = render(
            <Carousel duration={1}>
                <div>123</div>
                <div>234</div>
            </Carousel>
        );

        const list = renderResult.getByRole('list');

        act(() => {
            jest.runAllTimers();
        });

        expect(list.style.left).toBe('-100%')

        act(() => {
            jest.runAllTimers();
        });

        expect(list.style.left).toBe('0%')
    })

    test('Should show second slider When click second bar', async () => {
        const renderResult = render(
            <Carousel duration={1}>
                <div>123</div>
                <div>234</div>
            </Carousel>
        );

        const bars = renderResult.container.getElementsByClassName('bar-outer');
        act(() => {
            fireEvent(bars[1], new MouseEvent('click', { bubbles: true, cancelable: true }));
        })

        const secondElement = renderResult.getByRole('list');
        expect(secondElement.style.left).toBe('-100%');
    })

});
