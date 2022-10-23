function f(err: any) : string {
  if (typeof err === 'string') {
    return err;
  }
  else {
    return 'xxx';
  }
}