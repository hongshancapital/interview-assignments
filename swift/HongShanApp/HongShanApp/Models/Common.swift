//
//  Common.swift
//  HongShanApp
//
//  Created by wangbin on 2021/10/22.
//

import Foundation
import SwiftUI

let SCREEN_WIDTH = UIScreen.main.bounds.size.width
let SCREEN_HEIGHT = UIScreen.main.bounds.size.height

let COMMON_MARGIN = 10.0

func RGB(_ red:Double,_ green:Double,_ blue:Double) -> Color {
    return Color.init(red: red / 255.0, green: green / 255.0, blue: blue / 255.0)
}

let LIST_BACK_COLOR = RGB(190, 210, 174)


