import { render } from '@testing-library/react';
import App from './App';

test('renders carousel', () => {
    const testID = 'app';
    const { getByTestId } = render(<App testID={testID} />);

    expect(getByTestId(testID).querySelector('.carousel')).toBeInTheDocument();
});
