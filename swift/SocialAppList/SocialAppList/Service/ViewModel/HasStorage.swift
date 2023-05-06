//
//  Storage.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import Foundation
import Combine

private var storageContext: UInt8 = 0

protocol HasStorage: AnyObject, AssociatedObjectStore {
    var storage: Set<AnyCancellable> { get set }
}

extension HasStorage {
    func synchronizedBag<T>( _ action: () -> T) -> T {
        objc_sync_enter(self)
        let result = action()
        objc_sync_exit(self)
        return result
    }

    var storage: Set<AnyCancellable> {
        get {
            return synchronizedBag {
                associatedObject(forKey: &storageContext, default: .init())
            }
        }

        set {
            synchronizedBag {
                setAssociatedObject(newValue, forKey: &storageContext)
            }
        }
    }
}
