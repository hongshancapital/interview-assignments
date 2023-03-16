import React from 'react';
import {act, render, screen, waitFor} from '@testing-library/react';
import App from './App';
import userEvent from "@testing-library/user-event";
import {server} from "@/mocks/server";
import {rest} from "msw";
import {baseUrl} from "@/mocks/handlers";

test('should contain initial toolbox and options', async () => {
    // server.printHandlers();
    await act(async () => {
        render(<App/>);
    })
    const titleElement = screen.getByText('QA Engineer\'s Toolbox Arsenal:');
    expect(titleElement).toBeInTheDocument();

    const toolboxElement = screen.getByText('Appium:E2E');
    expect(toolboxElement).toBeInTheDocument();

    const optionElement = screen.getByText('Unit Test');
    expect(optionElement).toBeInTheDocument();
});

test('add a new tool', async () => {
    await act(async () => {
        render(<App/>);
    })
    userEvent.type(screen.getByPlaceholderText("Please enter Engineer's name"), "Mock Server Worker");
    userEvent.click(screen.getByText('E2E'));
    server.use(
        rest.get(baseUrl('/toolbox'), (req, res, ctx) => {
            return res(ctx.status(200), ctx.json({
                "message": "success",
                "data": [
                    {
                        "id": 1,
                        "name": "Appium",
                        "tools": "E2E"
                    },
                    {
                        "id": 2,
                        "name": "Jest",
                        "tools": "Jest"
                    },
                    {
                        "id": 3,
                        "name": "Mock Server Worker",
                        "tools": "E2E"
                    }]
            }))
        }),
    )
    userEvent.click(screen.getByText('Submit'));

    await waitFor(() => {
        expect(screen.getByText('Mock Server Worker:E2E')).toBeInTheDocument();
    })
});

test('add a new tool without name', async () => {
    await act(async () => {
        render(<App/>);
    })
    userEvent.click(screen.getByText('E2E'));
    userEvent.click(screen.getByText('Submit'));

    await waitFor(() => {
        expect(screen.getByText('Error: Request failed with status code 400')).toBeInTheDocument();
    })
});