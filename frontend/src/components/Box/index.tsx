import {
    WidthVariant as _WidthVariant,
    MaxWidthVariant as _MaxWidthVariant,
    HeightVariant as _HeightVariant,
    MaxHeightVariant as _MaxHeightVariant,
} from "./Box";

// Workaround for re-exporting types with --isolatedModules
// https://stackoverflow.com/questions/53728230/cannot-re-export-a-type-when-using-the-isolatedmodules-with-ts-3-2-2
export type WidthVariant = _WidthVariant;
export type MaxWidthVariant = _MaxWidthVariant;
export type HeightVariant = _HeightVariant;
export type MaxHeightVariant = _MaxHeightVariant;
export { default } from "./Box";
