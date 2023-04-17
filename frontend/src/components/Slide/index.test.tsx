import { render } from '@testing-library/react';
import Slide from '.';

describe('Slide', () => {
    it('should render black text', () => {
        const text = 'hello world';
        const testID = 'slide_content';
        const { getByText, getByTestId } = render(
            <Slide
                testID={testID}
                image=""
                title={text}
                backgroundColor="#fff"
            />
        );

        expect(getByText(text)).toBeInTheDocument();
        expect(getByTestId(testID)).not.toHaveClass('slide__content--white');
    });

    it('should render white text', () => {
        const text = 'hello world';
        const testID = 'slide_content';
        const { getByText, getByTestId } = render(
            <Slide
                testID={testID}
                image=""
                title={text}
                backgroundColor="#fff"
                useWhiteText
            />
        );

        expect(getByText(text)).toBeInTheDocument();
        expect(getByTestId(testID).firstChild).toHaveClass(
            'slide__content--white'
        );
    });

    it('should render with description', () => {
        const title = 'hello';
        const description = 'world';
        const { getByText } = render(
            <Slide
                image=""
                title={title}
                backgroundColor="#fff"
                description={description}
            />
        );

        expect(getByText(description)).toBeInTheDocument();
    });
});
