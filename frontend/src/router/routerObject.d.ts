export interface metaOption {
  requiresAuth: boolean;
  title: string;
  key: string;
}
export interface RouteObject {
  path: string;
  element: React.ReactNode;
  meta?: metaOption;
}
