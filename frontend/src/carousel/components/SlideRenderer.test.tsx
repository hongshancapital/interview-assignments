import { render } from '@testing-library/react';
import React from 'react';
import { SlideRenderer } from './SlideRenderer';

describe('SlideRenderer', () => {
  it('should render title', () => {
    const { getByText } = render(<SlideRenderer titles={['SlideTitle']} descriptions={[]} image={{ src: '' }} />)

    expect(getByText('SlideTitle')).toBeTruthy();
  });

  it('should render descriptions', () => {
    const { getByText } = render(<SlideRenderer titles={['']} descriptions={['description1', 'description2']}
                                                image={{ src: '' }} />)

    expect(getByText('description1')).toBeTruthy();
    expect(getByText('description2')).toBeTruthy();
  });

  it('should render image', () => {
    const { getByRole } = render(<SlideRenderer titles={['']} descriptions={['description1', 'description2']}
                                                image={{ src: 'exampleImageSrc' }} />)

    expect(getByRole('img')).toBeTruthy();
  });
});