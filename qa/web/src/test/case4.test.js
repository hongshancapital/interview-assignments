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

test.concurrent('case 4: submit when name is null. expect to prompt usefull error message', async () => {

    const optionsTestData = ["E2E","Headless Chrome","Jest","Selenium"];
    var name = ""
    var tool = optionsTestData[0]
    var expectText = "Error: No name specified";

    setup('src/mock/options.json', JSON.stringify(optionsTestData))

    render(<App />);

    // wait for options loaded
    await screen.findByText(optionsTestData[0]);

    // input ""
    userEvent.type(screen.getByPlaceholderText("Please enter Engineer's name"), name);
    // click tool
    fireEvent.click(screen.getByText(tool));
    // click submit
    fireEvent.click(screen.getByText("Submit"));
    // check "Lily:E2E" display
    await waitFor(() => expect(screen.getByText(expectText)).toBeInTheDocument())

});
