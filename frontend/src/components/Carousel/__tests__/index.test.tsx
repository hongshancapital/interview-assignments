import { fireEvent, render } from '@testing-library/react';
import React from 'react';
import { act } from 'react-dom/test-utils';
import Carousel from '..';

const config = {
    interval: 3000,
    speed: 500,
    items: [
        {
            title: 'xPhone',
            description: (<div role='document'>Lots to love. Less to spend.<br />Starting at $399</div>),
            img: 'http://xphone',
            style: {
                color: '#fff',
                backgroundColor: '#111',
                backgroundSize: '50% auto',
            },
        },
        {
            title: 'Tablet',
            description: 'Just the right amount of everything.',
            img: 'http://tablet',
            style: {
                backgroundColor: '#fafafa',
                backgroundSize: '100% auto',
            },
        },
        {
            title: (<div role='heading'>Buy a Tablet or xPhone for colleage.<br /> Get airPods.</div>),
            img: "http://tablet-xphone",
            style: {
                backgroundColor: '#f1f1f3',
                backgroundSize: '125% auto',
            },
        },
        {
            title: 'adad',
            img: 'sadfds'
        }
    ],
};

describe('Carousel', () => {
    test('Carousel render', () => {
        const { getByText } = render(<Carousel {...config} />);
        [
            /Lots to love\. Less to spend/i,
            /Just the right amount of everything/i,
            /Buy a Tablet or xPhone for colleage/i
        ].forEach((it) => {
            const slider = getByText(it);
            expect(slider).toBeInTheDocument();
        })
    });

    test('Carousel play', () => {
        jest.useFakeTimers();
        jest.spyOn(global, 'setInterval');
        render(<Carousel {...config} />);
        act(() => {
            jest.runOnlyPendingTimers();
        });
        expect(setInterval).toHaveBeenCalled();
    });

    test('Carousel indicator switch', () => {
        const { container } = render(<Carousel {...config} />);
        const wrapEle = container.getElementsByClassName('carousel-container')[0];
        Array(3).fill(0).forEach((_, i) => {
            const indicatorEle = container.getElementsByClassName('indicator-item')[i];
            fireEvent.click(indicatorEle);
            expect((wrapEle as HTMLElement).style.transform).toEqual(`translateX(-${100 * i}%)`);
        })
    })
})