export class TimeLog {

  private start : number 
  logs : Array<[string, number]> = []
  constructor() {
    this.start = new Date().getTime()
  }

  log(log : string) {
    this.logs.push([log, new Date().getTime() - this.start])
  }

  toString() {
    return this.logs.map(x => x[0]).join("|")
  }
}