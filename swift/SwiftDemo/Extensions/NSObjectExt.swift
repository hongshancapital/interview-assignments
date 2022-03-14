//
//  NSObjectExt.swift
//  SwiftDemo
//
//  Created by AYX on 2022/3/11.
//

import UIKit

extension NSObject {
    static var className: String {
        return String(describing: self)
    }
}
