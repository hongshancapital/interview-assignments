import React from 'react'
import { render, act } from '@testing-library/react'
import { Bar } from '../../../components/bar/item'
import { Subject } from 'rxjs'

describe('bar\'s item', () => {
    const mockTimer$ = new Subject<number>()

    it('should render a bar item', () => {
        const { container } = render(<Bar timer$={mockTimer$}></Bar>)

        expect(container.firstChild).toMatchInlineSnapshot(`
            <div
              class="bar"
              style="--progress: 0%;"
            />
        `)
    })

    it('should rerender when timer emits value', () => {
        const { container } = render(<Bar timer$={mockTimer$}></Bar>)

        act(() => mockTimer$.next(10))
        expect(container.firstChild).toMatchInlineSnapshot(`
            <div
              class="bar"
              style="--progress: 10%;"
            />
        `)
    })
})