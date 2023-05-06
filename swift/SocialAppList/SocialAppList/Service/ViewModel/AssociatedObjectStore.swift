//
//  AssociatedObjectStore.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import ObjectiveC

protocol AssociatedObjectStore {}

extension AssociatedObjectStore {
    func setAssociatedObject<T>(_ value: T?, forKey key: UnsafeRawPointer) {
        if let value = value {
            objc_setAssociatedObject(self, key, AssociatedObject(value), .OBJC_ASSOCIATION_RETAIN_NONATOMIC)
        } else {
            objc_setAssociatedObject(self, key, nil, .OBJC_ASSOCIATION_RETAIN_NONATOMIC)
        }
    }

    func associatedObject<T>(forKey key: UnsafeRawPointer) -> T? {
        return (objc_getAssociatedObject(self, key) as? AssociatedObject<T>)?.value as? T
    }

    func associatedObject<T>(forKey key: UnsafeRawPointer, default: @autoclosure () -> T) -> T {
        if let value: T = self.associatedObject(forKey: key) {
            return value
        }
        let value = `default`()
        self.setAssociatedObject(value, forKey: key)
        return value
    }
}

private class AssociatedObject<T> {
    var value: T?
    init(_ value: T?) {
        self.value = value
    }
}
