import React from 'react';
import Carousel from './Carousel';
import {render, screen, act, waitFor} from '@testing-library/react';
import { slides } from './mock-data';


describe('carousel', () => {
    beforeEach(() => {
        jest.useFakeTimers();
    })

    afterEach(() => {
        jest.runOnlyPendingTimers();
        jest.useRealTimers();
    });

    it('should auto switch slide', async () => {
        act(() => {
            render(<Carousel slides={slides} />);
        });

        await waitFor(() => {
            const slideContainerEl = screen.getByTestId('slide-container');
            expect(slideContainerEl).toHaveStyle('transform: translateX(-100vw)');
        }, { timeout: 3200 });

        await waitFor(() => {
            const slideContainerEl = screen.getByTestId('slide-container');
            expect(slideContainerEl).toHaveStyle('transform: translateX(-200vw)');
        }, { timeout: 6200 });

        await waitFor(() => {
            const slideContainerEl = screen.getByTestId('slide-container');
            expect(slideContainerEl).toHaveStyle('transform: translateX(0vw)');
        }, { timeout: 9200 });
    });
});

