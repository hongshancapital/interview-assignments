//
//  EdgeInsets+extenstion.swift
//  SwiftUIAssignments
//
//  Created by zcj on 2023/6/6.
//

import SwiftUI

extension EdgeInsets {

    /// fast create EdgeInsets
    /// - Parameters:
    ///   - horizontal: horizontal
    ///   - vertical: vertical
    /// - Returns: EdgeInsets
    static func symmetric(horizontal: CGFloat = 0, vertical: CGFloat = 0) -> EdgeInsets {
        return EdgeInsets(top: vertical, leading: horizontal, bottom: vertical, trailing: horizontal)
    }
}
