import { render } from '@testing-library/react';
import Banner from '../index';
describe('test Banner component', () => {
  const data = {
    title: 'xPhone',
    text: 'Lots to love.Less to spend.\nStarting at $399.',
    color: '#fff',
    img: 'test img',
    backgroundColor: '#101010',
  };

  it('renders correctly', () => {
    const { container } = render(<Banner data={data} />);
    expect(container.firstChild).toMatchSnapshot();
  });

  it('mount correctly', () => {
    expect(() => render(<Banner data={data} />)).not.toThrow();
  });

  it('test rendering properties', () => {
    const { container } = render(<Banner className="test" data={data} />);

    expect(container.querySelector('.banner')).toHaveClass('test');
    expect(container.querySelector('.banner')).toHaveStyle({
      color: '#fff',
      backgroundColor: '#101010',
    });

    expect(container.querySelector('h3')?.textContent).toBe('xPhone');

    expect(container.querySelector('p')?.textContent).toBe(
      'Lots to love.Less to spend.\nStarting at $399.'
    );
  });
});
