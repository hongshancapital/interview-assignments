import React from "react";
import { render } from "@testing-library/react";
import App from "./App";
import { Carousel } from './components/Carousel';
import { Progress } from './components/Progress';

describe("Demo test", () => {
    /**
     * 对应文案显示是否正常
     */
    it("should have some copywriting", () => {
        const { queryByText } = render(<App />);
        const emement1 = queryByText("xPhone");
        const emement2 = queryByText("Lots to love.Less to Spend.");
        const emement3 = queryByText("Starting at $399.");
        const emement4 = queryByText("Tablet");
        const emement5 = queryByText("Just the right amount of everything.");
        const emement6 = queryByText("Buy a Tablet or xPhone for College.");
        const emement7 = queryByText("Get AirPods");

        expect(emement1).toBeInTheDocument();
        expect(emement2).toBeInTheDocument();
        expect(emement3).toBeInTheDocument();
        expect(emement4).toBeInTheDocument();
        expect(emement5).toBeInTheDocument();
        expect(emement6).toBeInTheDocument();
        expect(emement7).toBeInTheDocument();
    });

    /**
     * 3 个 banner 元素
     */
    it("should have 3 banner", () => {
        const { container, asFragment } = render(<App />);
        const el = container.querySelectorAll(".app-carousel");
        expect(el.length).toBe(3);
        expect(asFragment()).toMatchSnapshot();
    });

    /**
     * 3 个 progress 元素
     */
    it("should have 3 progress", () => {
        const { container, asFragment } = render(<App />);
        const el = container.querySelectorAll(".progress");
        expect(el.length).toBe(3);
        expect(asFragment()).toMatchSnapshot();
    });

    it('exports modules correctly', () => {
        expect([Carousel, Progress]).toMatchSnapshot();
    });
});
