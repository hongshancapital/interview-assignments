import Foundation

@propertyWrapper
struct Injection<T> {
    private let component: T
    
    init() {
        component = Resolver.shared.resolve(T.self)
    }
    
    var wrappedValue: T { component }
}

class Resolver {
    static let shared = Resolver()
    
    private var factory: [String: Any] = [:]
    
    init() {}
    
    func add<T>(_ type: T.Type, component: T) {
        self.factory.updateValue(component, forKey: String(describing: type.self))
    }
    
    func resolve<T>(_ type: T.Type) -> T {
        factory[String(describing: type.self)] as! T
    }
}
