import { TextVariant as _TextVariant } from "./Text";

// Workaround for re-exporting types with --isolatedModules
// https://stackoverflow.com/questions/53728230/cannot-re-export-a-type-when-using-the-isolatedmodules-with-ts-3-2-2
export type TextVariant = _TextVariant;
export { default } from "./Text";
