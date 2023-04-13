import { render } from '@testing-library/react';
import MultiText from '.';

describe('MultiText', () => {
    it('should render with a single text', () => {
        const text = 'hello world';
        const { getByText } = render(<MultiText texts={text} />);
        const textElement = getByText(text);

        expect(textElement).toBeInTheDocument();
    });

    it('should render with multiple texts', () => {
        const texts = ['hello', 'world'];
        const { getByText } = render(<MultiText texts={texts} />);

        for (let text of texts) {
            const textElement = getByText(text);

            expect(textElement).toBeInTheDocument();
        }
    });
});
