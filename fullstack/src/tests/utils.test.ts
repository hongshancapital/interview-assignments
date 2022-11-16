import { isURL } from "../utils";

test(' baidu.com is url', () => {
    expect(isURL("baidu.com")).toBeFalsy()
})