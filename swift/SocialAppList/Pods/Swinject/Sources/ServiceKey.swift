//
//  Copyright © 2019 Swinject Contributors. All rights reserved.
//

import Foundation

// MARK: ServiceKeyOption

public protocol ServiceKeyOption: CustomStringConvertible {
    func isEqualTo(_ another: ServiceKeyOption) -> Bool
    func hash(into: inout Hasher)
}

// MARK: - ServiceKey

internal struct ServiceKey {
    internal let serviceType: Any.Type
    internal let argumentsType: Any.Type
    internal let name: String?
    internal let option: ServiceKeyOption? // Used for SwinjectStoryboard or other extensions.

    internal init(
        serviceType: Any.Type,
        argumentsType: Any.Type,
        name: String? = nil,
        option: ServiceKeyOption? = nil
    ) {
        self.serviceType = serviceType
        self.argumentsType = argumentsType
        self.name = name
        self.option = option
    }
}

// MARK: Hashable

extension ServiceKey: Hashable {
    public func hash(into hasher: inout Hasher) {
        ObjectIdentifier(serviceType).hash(into: &hasher)
        ObjectIdentifier(argumentsType).hash(into: &hasher)
        name?.hash(into: &hasher)
        option?.hash(into: &hasher)
    }
}

// MARK: Equatable

func == (lhs: ServiceKey, rhs: ServiceKey) -> Bool {
    return lhs.serviceType == rhs.serviceType
        && lhs.argumentsType == rhs.argumentsType
        && lhs.name == rhs.name
        && equalOptions(opt1: lhs.option, opt2: rhs.option)
}

private func equalOptions(opt1: ServiceKeyOption?, opt2: ServiceKeyOption?) -> Bool {
    switch (opt1, opt2) {
    case let (opt1?, opt2?): return opt1.isEqualTo(opt2)
    case (nil, nil): return true
    default: return false
    }
}
