import Foundation

protocol UniqueKey {
    var uniqueKey: String { get }
}

extension UniqueKey where Self: RawRepresentable, Self.RawValue == String {
    var uniqueKey: String {
        "\(Self.self).\(rawValue)"
    }
}

