import React from 'react';
import { act, fireEvent, render, waitFor } from '@testing-library/react';
import Carousel from './index';

let originalFn: any;
beforeEach(() => {
    jest.useFakeTimers();
    originalFn = Element.prototype.getBoundingClientRect;
    // fake the 'getBoundingClientRect' aim to get given slider width, if not the slider left always be 0;
    Element.prototype.getBoundingClientRect = jest.fn(() => {
        return {
            width: 200,
            height: 100,
            top: 0,
            left: 0,
            bottom: 0,
            right: 0,
            x: 0,
            y: 0,
            toJSON: () => {
            }
        }
    });
})

afterEach(() => {
    Element.prototype.getBoundingClientRect = originalFn;
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
        expect(list.style.left).toBe('0px');
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

        expect(list.style.left).toBe('-200px')
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

        expect(list.style.left).toBe('-200px')

        act(() => {
            jest.runAllTimers();
        });

        expect(list.style.left).toBe('0px')
    })

    test('Should back to first slider When resize window', () => {
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

        act(() => {
            fireEvent.resize(window);
        })

        expect(list.style.left).toBe('0px')
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
        expect(secondElement.style.left).toBe('-200px');
    })

});
