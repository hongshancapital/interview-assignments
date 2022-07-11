import {rest} from "msw";

export const baseUrl = (path: string) => {
    return new URL(path, 'http://localhost:5000').toString()
}

export const handlers = [
    rest.get(baseUrl('/toolbox'), (req, res, context) => {
        return res(
            context.status(200),
            context.json({
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
                    }]
            })
        )
    }),

    rest.get(baseUrl('/toolbox/options'), (req, res, context) => {
        return res(
            context.status(200),
            context.json([
                    "E2E",
                    "Unit Test",
                    "Jest",
                    "Selenium"
                ]
            )
        )
    }),

    rest.post<{ name: string, tools: string }>(baseUrl('/toolbox/create'), (req, res, context) => {
        if (req.body.name == null) {
            return res(context.status(400), context.json({"error": "No name specified"}))
        }
        return res(
            context.status(200),
            context.json({"message": "success"}
            )
        )
    })
]