import { render } from '@testing-library/react';
import Progress from '../index';
describe('test Progress component', () => {
  it('mount correctly', () => {
    expect(() => render(<Progress />)).not.toThrow();
  });

  it('test rendering properties', () => {
    const { container } = render(
      <Progress className="test" percent={'100%'} />
    );

    expect(container.querySelector('.progress')).toHaveClass('test');
    expect(container.querySelector('.progress-bar')).toHaveStyle({
      width: '100%',
    });
  });
  it('test the rendering style attribute', () => {
    const { container } = render(
      <Progress
        percent={'100%'}
        style={{ width: '40px', height: '10px' }}
        barStyle={{ width: '50%', transition: 'all 0.1s' }}
      />
    );

    expect(container.querySelector('.progress')).toHaveStyle({
      width: '40px',
      height: '10px',
    });
    expect(container.querySelector('.progress-bar')).toHaveStyle({
      width: '50%',
      transition: 'all 0.1s',
    });
  });
});
