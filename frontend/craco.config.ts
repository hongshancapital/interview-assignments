
import path from "path"
import type {CracoConfig} from "@craco/types"
const cracoConfiguration:CracoConfig={
  webpack: {
    alias: {
      "@": path.resolve(__dirname, "src"),
    },
  },
};
export default cracoConfiguration