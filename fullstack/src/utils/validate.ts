import _isURL from "validator/lib/isURL";

export function isURL(value: unknown): value is string {
  if (typeof value !== "string") {
    return false;
  }

  return _isURL(value, { require_protocol: true });
}

export function isAlias(value: unknown): value is string {
  if (typeof value !== "string") {
    return false;
  }

  return /^[0-9a-zA-Z]{8}$/.test(value);
}
