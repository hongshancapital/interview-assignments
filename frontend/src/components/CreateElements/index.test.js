import React from 'react';
import { render, configure, getAllByTestId, queryAllByTestId, queryAllByText } from '@testing-library/react';
import CreateElements from './CreateElements';
configure({ testIdAttribute: 'class' })


test("renders without crashing when data is empty ", () => {
    render(<CreateElements data={{}} />);
});


describe('render CreateElements component', () => {
    test('check CreateElements Fun can right render', () => {
        const testClassName = 'test_class'
        const contentList = ['1', '2']
        const { container } = render(<div className="App">
            {
                CreateElements({
                    contentType: 'p',
                    className: testClassName,
                    contentList: contentList
                })
            }
        </div>)
        const element = getAllByTestId(container, testClassName)
        expect(element.length).toBe(contentList.length);
    });

    test('check contentList is Empty And not Render Element', () => {
        const testClassName2 = 'test_class2'
        const { container } = render(<div className="App">
            {
                CreateElements({
                    contentType: 'span',
                    className: testClassName2
                })
            }
        </div>)
        const element = queryAllByTestId(container, testClassName2)
        expect(element.length).toBe(0);
    });

    test('check className is Empty And Can Render Element', () => {
        const Text = 'my some test'
        const { container } = render(<div className="App">
            {
                CreateElements({
                    contentType: 'p',
                    contentList: [Text]
                })
            }
        </div>)
        const element = queryAllByText(container, Text)
        expect(element).toHaveLength(1);
    });
})



