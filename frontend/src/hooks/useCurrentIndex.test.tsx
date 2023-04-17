import { render } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { fireEvent } from '@testing-library/react';
import useCurrentIndex from './useCurrentIndex';

describe('useCurrentIndex', () => {
    const testID = 'test';
    const data = [{ text: 'hello' }, { text: 'world' }, { text: 'hi' }];
    type Data = typeof data;

    function Test({ data }: { data: Data }) {
        const [index, setIndex] = useCurrentIndex(data, 0);

        return (
            <div
                data-testid={testID}
                onClick={() => {
                    setIndex((i) => i + 1);
                }}
            >
                {index >= 0 && index < data.length && data[index].text}
            </div>
        );
    }

    it('should ignore index manipulations if there are no items', () => {
        const { getByTestId } = render(<Test data={[]} />);

        act(() => {
            fireEvent.click(getByTestId(testID));
        });

        expect(getByTestId(testID)).toBeInTheDocument();
    });

    it('should work with CASE 1', () => {
        const { getByText, container } = render(<Test data={data} />);

        expect(getByText(data[0].text)).toBeInTheDocument();

        const { getByText: getByText2 } = render(
            <Test data={[{ text: 'added' }, data[0], data[1]]} />,
            {
                container,
            }
        );

        expect(getByText2(data[0].text)).toBeInTheDocument();
    });

    it('should work with CASE 2.1', () => {
        const { getByTestId, getByText, container } = render(
            <Test data={data} />
        );

        act(() => {
            fireEvent.click(getByTestId(testID));
        });

        expect(getByText(data[1].text)).toBeInTheDocument();

        const { getByText: getByText2 } = render(
            <Test data={[data[0], data[2], { text: 'added' }]} />,
            {
                container,
            }
        );

        expect(getByText2(data[2].text)).toBeInTheDocument();
    });

    it('should work with CASE 2.2', () => {
        const { container, getByTestId, getByText } = render(
            <Test data={data} />
        );

        act(() => {
            fireEvent.click(getByTestId(testID));
        });
        act(() => {
            fireEvent.click(getByTestId(testID));
        });

        expect(getByText(data[2].text)).toBeInTheDocument();

        const { getByText: getByText2 } = render(
            <Test data={[data[0], data[1]]} />,
            {
                container,
            }
        );

        expect(getByText2(data[0].text)).toBeInTheDocument();

        // cover case 2.2: all removed
        const { getByTestId: getByTestId3 } = render(<Test data={[]} />, {
            container,
        });

        expect(getByTestId3(testID)).toBeInTheDocument();
    });
});
