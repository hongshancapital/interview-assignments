import { TOOLBOX_ENUM } from "../types";

export const generateToolboxNames = () => {
    return [TOOLBOX_ENUM.E2E, TOOLBOX_ENUM.HEADLESS_CHROME, TOOLBOX_ENUM.JEST, TOOLBOX_ENUM.SELENIUM];
}