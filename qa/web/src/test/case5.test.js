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

test.concurrent('case 5: submit when name contains Chinese or special characters. expect to display name correctly', async () => {

    const optionsTestData = ["E2E","Headless Chrome","Jest","Selenium"];
    var name = "中文%20&：: 0d@~/>)名字"
    var tool = optionsTestData[0]
    var expectText = name + ":" +  tool;

    setup('src/mock/options.json', JSON.stringify(optionsTestData))

    render(<App />);

    // wait for options loaded
    await screen.findByText(optionsTestData[0]);

    // input name
    userEvent.type(screen.getByPlaceholderText("Please enter Engineer's name"), name);
    // click tool
    fireEvent.click(screen.getByText(tool));
    // click submit
    fireEvent.click(screen.getByText("Submit"));
    // check "Lily:E2E" display
    await waitFor(() => expect(screen.getByText(expectText)).toBeInTheDocument())

});
