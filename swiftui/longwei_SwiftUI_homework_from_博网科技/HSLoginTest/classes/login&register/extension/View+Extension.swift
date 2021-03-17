//
//  View+Extension.swift
//  HSLoginTest
//
//  Created by 伟龙 on 2021/3/10.
//

import Foundation
import SwiftUI
extension View {
    func userScaleAnimation(_ scale:CGFloat,duration:Double = 0.3, delay:Double = 0.1) -> some View {
       return self.scaleEffect(scale)
        .animation(Animation.easeOut(duration: duration).delay(delay))
    }
}
