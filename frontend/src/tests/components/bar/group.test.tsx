import React from 'react'
import { render, act, RenderResult, fireEvent, waitFor } from '@testing-library/react'
import { Group } from '../../../components/bar/group'
import { sleep } from '../../utils/sleep'

describe('bar', () => {
    let renderResult: RenderResult
    let getProgress: (index: number) => number
    let bars: NodeListOf<HTMLDivElement>
    beforeEach(() => {
        act(() => {
            renderResult = render(<Group barCount={3} duration={500}></Group>)
            bars = renderResult.container.querySelectorAll<HTMLDivElement>('.bar')
            getProgress = (index: number) => {
                return +bars
                    .item(index)
                    .style.getPropertyValue('--progress').replace('%', '')
            }
        })
    })

    it('should render 3 items', () => {
        expect(renderResult.container.querySelectorAll('.bar').length).toBe(3)
    })

    it('should change progress periodically', async () => {
        await waitFor(() => sleep(100))
        expect(getProgress(0))
            .not.toBe(0)
        await waitFor(() => sleep(500))
        expect(getProgress(0))
            .toBe(0)
        expect(getProgress(1))
            .not.toBe(0)
        await waitFor(() => sleep(500))
        expect(getProgress(0))
            .toBe(0)
        expect(getProgress(1))
            .toBe(0)
        expect(getProgress(2))
            .not.toBe(0)
        await waitFor(() => sleep(500))
        expect(getProgress(0))
            .not.toBe(0)
        expect(getProgress(1))
            .toBe(0)
        expect(getProgress(2))
            .toBe(0)
    })

    it('should change style after clicking bar', async () => {
        await waitFor(() => sleep(100))
        expect(getProgress(1))
            .toBe(0)
        act(() => {
            fireEvent.click(bars.item(1))
        })
        await waitFor(() => sleep(10))
        expect(getProgress(0))
            .toBe(0)
        expect(getProgress(1))
            .not.toBe(0)
    })
})