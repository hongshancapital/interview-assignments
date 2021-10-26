type Listener<Topic extends string | number> = (
  topic : Topic, ...msgs: any[]
) => void
export class Emitter<Topic extends string | number> {
  private topics: Map<Topic, Set<Listener<Topic>>> = new Map()

  on(topic : Topic, listener: Listener<Topic>) : () => void; 
  on(topic : Topic[], listener: Listener<Topic>) : () => void;
  on(topic : Topic | Topic[], listener: Listener<Topic>) {
    if(Array.isArray(topic)) {
      topic.forEach(t => {
        this.on(t, listener)
      })
      return
    }
    if (!this.topics.has(topic)) {
      this.topics.set(topic, new Set())
    }
    this.topics.get(topic)!.add(listener)

    return () => {
      if(Array.isArray(topic)) {
        topic.forEach(t => {
          this.topics.get(t)!.delete(listener)
        })
        return
      }
      this.topics.get(topic)!.delete(listener)
    }
  }

  emit(topic: Topic, ...msgs: any[]) {
    const handers = this.topics.get(topic)
    if (handers) {
      handers.forEach((h) => {
        h(topic, ...msgs)
      })
    }
  }
}
