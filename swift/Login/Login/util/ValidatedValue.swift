//
//  ValidareValue.swift
//  Login
//
//  Created by xiwang wang on 2021/8/30.
//

import Foundation

class ValidatedValue<T>: ObservableObject {
    let objectWillChange = ObjectWillChangePublisher()
    let validator: (T, T) -> T
    var value: T {
        get {
            _value
        }
        set {
            _value = validator(_value, newValue)
            objectWillChange.send()
        }
    }
    var _value: T
    
    init(_ value: T, validator: @escaping (T, T)->T) {
        self._value = value
        self.validator = validator
    }
}
