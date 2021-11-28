//
//  ToDoColorPattern.swift
//  ToDoList
//
//  Created by zhoubo on 2021/11/24.
//

import Foundation
import SwiftUI

enum ColorPattern {
    case mainBackground
    case mainBackgroundAplha
    case mainText
    case todoCard
    case strikethrough
    
    var color: Color {
        switch self {
        case .mainBackground:
            return Color(red: 0.96, green: 0.96, blue: 0.96)
        case .mainBackgroundAplha:
            return Color(Color.RGBColorSpace.sRGB, red: 0.96, green: 0.96, blue: 0.96, opacity: 0.7)
        case .mainText:
            return Color.black
        case .todoCard:
            return Color.white
        case .strikethrough:
            return Color(red: 197.0 / 255.0, green: 200.0 / 255.0, blue: 213.0 / 255.0)
        }
    }
}
