import React from 'react';
import { render, screen } from '@testing-library/react';
import App from '@/App';

var fs = require('fs');
var setup = (url:string, data:string) => {
    fs.writeFile(url, data, function(err) {
        if (err) {
            return console.error(err);
        }
    });
}

test.concurrent('case 1: first visit, show "Empty, add some!", 4 tools, and other static elements', async () => {

    const options = ["E2E","Headless Chrome","Jest","Selenium"];
    const toolboxData = {"message":"success","data":[]}
    setup('src/mock/options.json', JSON.stringify(options))
    setup('src/mock/toolbox.json', JSON.stringify(toolboxData))
    setup('src/mock/toolbox.json', JSON.stringify({"message":"success","data":[]}))

    render(<App />);

    await screen.findByText(options[0]);

    const headElement = screen.getByText(/QA Engineer's Toolbox Arsenal:/);
    expect(headElement).toBeInTheDocument();

    const msgElement = screen.getByText(/Empty, add some!/i);
    expect(msgElement).toBeInTheDocument();

    const inputElement = screen.getByPlaceholderText("Please enter Engineer's name");
    expect(inputElement).toBeInTheDocument();

    const submitElement = screen.getByText("Submit");
    expect(submitElement).toBeInTheDocument();

    options.forEach((option) => {
        expect(screen.getByText(option)).toBeInTheDocument();
    })
});

