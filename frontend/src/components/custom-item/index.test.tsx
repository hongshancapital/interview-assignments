import { CustomItem } from './index';
import mountTest from '../../tests/mountTest';
import { render } from '@testing-library/react';

describe('CustomItem', () => {
  mountTest(CustomItem);

  it('should render correctly', () => {
    const { container } = render(
      <CustomItem item={{
        titles: ['test_title'],
        descs: ['test_desc'],
        img_url: 'test_url',
        style: {
          background: '#fff',
          color: '#000'
        }
      }} />
    );
    expect(container).toBeInTheDocument();
    expect(container.querySelector('.custom-item')).toHaveStyle('background: #fff; color: #000;');
  });
});