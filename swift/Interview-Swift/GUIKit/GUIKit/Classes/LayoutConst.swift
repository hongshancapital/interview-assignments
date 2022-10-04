//
//  LayoutConst.swift
//  Pods-GUIKit_Example
//
//  Created by lizhao on 2022/9/19.
//

import Foundation

public struct UI {
    public struct Color {
        public static let separator = UIColor(red: 0.95, green: 0.95, blue: 0.95, alpha: 1)
        public static let border = UIColor(red: 0.95, green: 0.95, blue: 0.95, alpha: 1)
    }
 
    public struct Space {
        /// 8 小间隙
        public static let small: CGFloat = 8
        /// 14 标准间隙
        public static let medium: CGFloat = 14
        /// 20 大间隙
        public static let large: CGFloat = 20
        /// 14
        public static let listLeftRightInset: CGFloat = medium
    }
    
    public struct Corner {
        /// 6 默认圆角
        public static let `default`: CGFloat = 6
        /// 3 小圆角
        public static let small: CGFloat = 3
        /// 6的圆角
        public static let big: CGFloat = 6
        /// 18的圆角
        public static let biggest: CGFloat = 18
    }

    public struct Border {
        /// 2 粗描边
        public static let bold: CGFloat = 2
        /// 1 细描边
        public static let light: CGFloat = 1
    }
}
