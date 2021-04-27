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


test.concurrent('case 3: submit first tool. expect to display the correct name and the first tool', async () => {

    const optionsTestData = ["E2E","Headless Chrome","Jest","Selenium"];
    var name = 'Lily'
    var tool = optionsTestData[0]
    var expectText = name + ":" +  tool;

    setup('src/mock/options.json', JSON.stringify(optionsTestData))

    render(<App />);

    // wait for options loaded
    await screen.findByText(optionsTestData[0]);
    const submitElement = screen.getByText("Submit");
    // input "Lily"
    userEvent.type(screen.getByPlaceholderText("Please enter Engineer's name"), name);
    // click submit, first tool is selected default.
    fireEvent.click(submitElement);
    // check "Lily:E2E" display
    await waitFor(() => expect(screen.getByText(expectText)).toBeInTheDocument())

});
