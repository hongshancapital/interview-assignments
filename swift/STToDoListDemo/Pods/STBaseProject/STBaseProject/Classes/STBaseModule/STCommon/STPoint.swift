//
//  STPoint.swift
//  STBaseProject
//
//  Created by stack on 2017/10/14.
//  Copyright © 2017年 ST. All rights reserved.
//

import UIKit
import Foundation

public class STPoint: NSObject {

    /// 两点间的距离
    /// hypot(x, y)函数为计算三角形的斜边长度
    ///
    /// - Parameters:
    ///   - pointA: 点A的坐标.
    ///   - pointB: 点B的坐标.
    ///
    /// - Returns:  两点间的距离.
    public static func st_distanceBetween(pointA: CGPoint, pointB: CGPoint) -> CGFloat {
        let x = abs(pointA.x - pointB.x)
        let y = abs(pointA.y - pointB.y)
        return hypot(x, y)
    }
    
    /// 计算圆上两点间的角度
    ///
    /// a^2 = b^2 + c^2 - 2bccosA
    ///
    /// - Parameters:
    ///   - radius: 半径
    ///   - center: 圆心
    ///   - startCenter: 起始点坐标
    ///   - endCenter: 结束点坐标
    ///
    /// - Returns: 圆上两点间的角度.
    public static func st_calculateAngle(radius: CGFloat, center: CGPoint, startCenter: CGPoint, endCenter: CGPoint) -> CGFloat {
        let distance = STPoint.st_distanceBetween(pointA: startCenter, pointB: endCenter)
        let cosA = (2 * pow(radius, 2) - pow(distance, 2)) / (2 * pow(radius, 2))
        var angle = 180 / Double.pi * Double(acosf(Float(cosA)))
        if startCenter.x > endCenter.x {
            angle = 360 - angle
        }
        return CGFloat(angle)
    }
}
