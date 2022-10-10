//
//  Combine.swift
//  SwiftUIDemo
//
//  Created by weizhao on 2021/10/17.
//  Copyright © 2021 weizhao. All rights reserved.
//

import Foundation

struct CombineExtension<Base> {
    let base: Base

    init(_ base: Base) {
        self.base = base
    }
}

protocol CombineCompatible {
    associatedtype CombineExtensionBase

    static var combine: CombineExtension<CombineExtensionBase>.Type { get set }
    var combine: CombineExtension<CombineExtensionBase> { get set }
}

extension CombineCompatible {

    static var combine: CombineExtension<Self>.Type {
        get {
            return CombineExtension<Self>.self
        }
        set {}
    }

    var combine: CombineExtension<Self> {
        get {
            return CombineExtension<Self>(self)
        }
        set {}
    }
}

