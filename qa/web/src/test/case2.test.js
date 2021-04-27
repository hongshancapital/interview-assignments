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

test.concurrent('case 2: when the toolbox has more than 2 items, display every item', async () => {
    const options = ["E2E","Headless Chrome","Jest","Selenium"];
    var toolboxData = {"message":"success","data":[{"id":2,"name":"ling","tools":"E2E"},{"id":1,"name":"Lily","tools":"Selenium"}]}
    var expectText1 = toolboxData.data[0].name + ":" + toolboxData.data[0].tools
    var expectText2 = toolboxData.data[1].name + ":" + toolboxData.data[1].tools
    setup('src/mock/options.json', JSON.stringify(options))
    setup('src/mock/toolbox.json', JSON.stringify(toolboxData))

    render(<App />);

    await screen.findByText(options[0]);
    await screen.findByText(expectText1);

    expect(screen.getByText(expectText1)).toBeInTheDocument();
    expect(screen.getByText(expectText2)).toBeInTheDocument();

});
