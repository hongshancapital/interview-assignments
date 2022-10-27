import React from 'react';
import {render} from '@testing-library/react';
import Carousel from "./index";
import demoItems from '../demo-items'

describe('Carousel', () => {
  it('render without crash', () => {
    const comp = render(<Carousel items={demoItems}/>)
    expect(comp.getByText('xPhone')).toBeInTheDocument()
  })
})