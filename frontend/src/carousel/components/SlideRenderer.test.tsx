import { render } from '@testing-library/react';
import React from 'react';
import { SlideRenderer } from './SlideRenderer';

describe('SlideRenderer', () => {
  it('should render title', () => {
    const { getByText } = render(<SlideRenderer titles={['SlideTitle']} descriptions={[]} />)

    expect(getByText('SlideTitle')).toBeTruthy();
  });

  it('should render descriptions', () => {
    const { getByText } = render(<SlideRenderer titles={['']} descriptions={['description1', 'description2']} />)

    expect(getByText('description1')).toBeTruthy();
    expect(getByText('description2')).toBeTruthy();
  });
});