import axios from "axios";
import { Config } from '@/config';
import { ToolboxForm } from "@/types";


export const getToolboxList = async () => {
  try {
    const resp = await axios.get<{data: ToolboxForm[]}>(`${Config.apiUrl}toolbox`);
    return resp.data.data;
  } catch (err) {
    console.error(err);
  }
};

export const createToolboxPreference = async (data: ToolboxForm) => {
  try {
    const resp = await axios.post(`${Config.apiUrl}toolbox/create`, data);
    return resp.data.data;
  } catch (err) {
    console.error(err);
    return err;
  }
};


export const getToolboxOptions = async () => {
  try {
    const resp = await axios.get<string[]>(`${Config.apiUrl}toolbox/options`);
    return resp.data;
  } catch (err) {
    console.error(err);
  }
};