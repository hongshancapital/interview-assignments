import React from 'react';
import { fireEvent, render } from '@testing-library/react';
import App from './App';

// it('renders learn react link', () => {
//     const { getByText } = render(<App />);
//     const linkElement = getByText(/Get arPods/i);
//     expect(linkElement).toBeInTheDocument();
// });

// 测试是否渲染了正确数目的progress
it('render number of progress correct', ()=>{
    let renderDom = render(<App/>)
  
    const progresses = renderDom.container.getElementsByClassName('progress_inner')

    expect(progresses.length).toBe(3)
})

// 测试进度条是否能切换
it('render progress change', () => {
    let renderDom = render(<App />)
    const progresses = renderDom.container.getElementsByClassName('progress_inner')

    expect(progresses[0].className === "progress_inner progress_change" && 
            progresses[1].className === "progress_inner " &&
            progresses[2].className === "progress_inner ").toBe(true)

    fireEvent.animationEnd(progresses[0])
    expect(progresses[0].className === "progress_inner " && 
            progresses[1].className === "progress_inner progress_change" &&
            progresses[2].className === "progress_inner ").toBe(true)

    fireEvent.animationEnd(progresses[1])
    expect(progresses[0].className === "progress_inner " && 
            progresses[1].className === "progress_inner " &&
            progresses[2].className === "progress_inner progress_change").toBe(true)
    
    fireEvent.animationEnd(progresses[2])
    expect(progresses[0].className === "progress_inner progress_change" && 
            progresses[1].className === "progress_inner " &&
            progresses[2].className === "progress_inner ").toBe(true)
});
