1. Carousel is an component which can scrolling play each slide with an interval of 2s.
2. Carousel can be configured by imported configuration, which shoud come from an easily editabled configuration file of project.
3. The configuration just like this：
        [
            {
                titles: ["xPhone"],
                descriptions: ["Lots to love.Less to spend.", "Starging at $399."],
                icon: "/sliceBackground/iphone.png",
                fontColor: "white",
                backgroundColor: "#111111",
                indicator: {
                    backgroundColor: "#9b9b9b9b",
                    processColor: "#f9f9f9f9",
                },
            },
            {
                titles: ["Tablet"],
                descriptions: ["Just the right amount of everything."],
                icon: "/sliceBackground/tablet.png",
                backgroundColor: "#fafafafa",
                indicator: {
                    backgroundColor: "#fafafafa",
                    processColor: "#a2a2a2",
                },
            },
            {
                titles: ["Buy a Tablet or xPhone for college.", "Get airPods."],
                descriptions: [],
                icon: "/sliceBackground/airpods.png",
                backgroundColor: "#f0f0f2",
                indicator: {
                    backgroundColor: "#fafafafa",
                    processColor: "#a9a9a9",
                },
            }
        ]
    Each array item indicate one slide.
    The titles or descriptions can be empty array.
    The icon, backgroundColor and the indicator can be none.
5. Move iphone.png, tablet.png, airpods.png to public folder just for to get the resource through configuration.
4. There is an indocator which can tell you the index of current slide. It can be clicked to stop slide scroll and can restart the scroll when mouse move out the indicator.