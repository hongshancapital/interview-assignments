//
//  STConstants.swift
//  STBaseProject
//
//  Created by stack on 2019/03/16.
//  Copyright © 2019年 ST. All rights reserved.
//

import UIKit
import Foundation

public enum STScreenSize {
    case AMXScreenSizeCurrent
    case AMXScreenSize3p5Inch
    case AMXScreenSize4Inch
    case AMXScreenSize4p7Inch
    case AMXScreenSize5p5Inch
    case AMXScreenSize7p9Inch
    case AMXScreenSize9p7Inch
    case AMXScreenSize12p9Inch
}

// 自定义 bar height
public struct STConstantBarHeightModel {
    public var navNormalHeight: CGFloat = 64.0
    public var navIsSafeHeight: CGFloat = 88.0
    public var tabBarNormalHeight: CGFloat = 49.0
    public var tabBarIsSafeHeight: CGFloat = 83.0
    public init() {}
}

public class STConstants: NSObject {
    
    private var benchmarkDesignSize = CGSize.zero
    public static let shared: STConstants = STConstants()
    private var barHeightModel: STConstantBarHeightModel = STConstantBarHeightModel()
    
    private override init() {
        super.init()
    }

    /// 设计图基准尺寸
    ///
    /// 配置一次
    ///
    /// - Parameter size: 基准尺寸
    public func st_configBenchmarkDesign(size: CGSize) -> Void {
        self.benchmarkDesignSize = size
    }
    
    /// 自定义 bar 高度
    ///
    /// - Parameter model: `STConstantBarHeightModel`
    public func st_customNavHeight(model: STConstantBarHeightModel) -> Void {
        self.barHeightModel = model
    }
    
    public class func st_multiplier() -> CGFloat {
        let size = STConstants.shared.benchmarkDesignSize
        if size == .zero {
            return 1.0
        }
        let min = UIScreen.main.bounds.height < UIScreen.main.bounds.width ? UIScreen.main.bounds.height : UIScreen.main.bounds.width
        return min / size.width
    }
    
    /// 当前屏幕与标准设计尺寸比例值
    ///
    /// 调用此方法前，需调用 `st_configBenchmarkDesign` 传入基准设计尺寸，配置一次
    ///
    /// - Parameter float: 设计图标注值
    public class func st_handleFloat(float: CGFloat) -> CGFloat {
        let multiplier = self.st_multiplier()
        return float * multiplier
    }
    
    public class func st_appw() -> CGFloat {
        return UIScreen.main.bounds.size.width
    }
    
    public class func st_apph() -> CGFloat {
        return UIScreen.main.bounds.size.height
    }
    
    public class func st_isIPhone5() -> Bool {
        return self.st_apph() < 667
    }
    
    /// 3.5inch
    ///
    /// 设备名称：3GS、4、4S
    ///
    /// 宽 x 高：320 x 480  @2x
    public class func st_isIPhone480() -> Bool {
        return self.st_apph() == 480
    }
    
    /// 4.0inch
    ///
    /// 设备名称：5、5S、5C、SE
    ///
    /// 宽 x 高：320 x 568  @2x
    public class func st_isIPhone568() -> Bool {
        return self.st_apph() == 568
    }
    
    /// 4.7inch
    ///
    /// 设备名称：6、6s、7、8
    ///
    /// 宽 x 高： 375 x 667  @2x
    public class func st_isIPhone667() -> Bool {
        return self.st_apph() == 667
    }
    
    /// 5.4inch
    ///
    /// 设备名称：12 mini
    ///
    /// 宽 x 高： 360 x 780  @3x
    public class func st_isIPhone12Mini() -> Bool {
        return self.st_apph() == 780
    }
    
    /// 5.5inch
    ///
    /// 设备名称：6Plus、6sPlus、7Plus、8Plus
    ///
    /// 宽 x 高： 414 x 736 @3x
    public class func st_isIPhonePlus() -> Bool {
        return self.st_apph() == 736
    }
    
    /// 5.8inch
    ///
    /// 设备名称：X、XS、11 Pro
    ///
    /// 宽 x 高： 375 x 812  @3x
    public class func st_isIPhone812() -> Bool {
        return self.st_apph() == 812
    }
    
    /// 6.1inch
    ///
    /// 设备名称：12 Pro
    ///
    /// 宽 x 高：390 x 844  @3x
    public class func st_isIPhone844() -> Bool {
        return self.st_apph() == 844
    }
    
    /// 6.1inch
    ///
    /// 设备名称：11、XR
    ///
    /// 宽 x 高：414 x 896  @2x
    public class func st_isIPhone896() -> Bool {
        return self.st_apph() == 896
    }
    
    /// 6.5inch
    ///
    /// 设备名称：11 Pro Max
    ///
    /// 宽 x 高：414 x 896  @3x
    public class func st_isIPhone11ProMax() -> Bool {
        return self.st_apph() == 896
    }
    
    /// 6.7inch
    ///
    /// 设备名称：12 Pro Max
    ///
    /// 宽 x 高：428 x 926
    public class func st_isIPhone926() -> Bool {
        return self.st_apph() == 926
    }
    
    /// 是否是刘海屏
    ///
    /// 高度大于736 为刘海屏
    public class func st_isIPhoneSafe() -> Bool {
        return self.st_apph() > 736
    }
    
    /// 导航栏高度
    public class func st_navHeight() -> CGFloat {
        if self.st_isIPhoneSafe() {
            return STConstants.shared.barHeightModel.navIsSafeHeight
        }
        return STConstants.shared.barHeightModel.navNormalHeight
    }
    
    /// tabBar高度
    public class func st_tabBarHeight() -> CGFloat {
        if self.st_isIPhoneSafe() {
            return STConstants.shared.barHeightModel.tabBarIsSafeHeight
        }
        return STConstants.shared.barHeightModel.tabBarNormalHeight
    }
    
    public class func st_safeBarHeight() -> CGFloat {
        if self.st_isIPhoneSafe() {
            return 34
        }
        return 0
    }
    
    public class func st_outputLogPath() -> String {
        let outputPath = "\(STFileManager.getLibraryCachePath())/outputLog"
        let pathIsExist = STFileManager.fileExistAt(path: outputPath)
        if !pathIsExist.0 {
            let _ = STFileManager.create(filePath: outputPath, fileName: "log.txt")
        }
        return "\(outputPath)/log.txt"
    }
    
    public class func st_notificationQueryLogName() -> String {
        return "com.notification.queryLog"
    }
}

/// 在DEBUG模式下打印到控制台
public func STLog<T>(_ message: T, file: String = #file, funcName: String = #function, lineNum: Int = #line) {
    #if DEBUG
    let file = (file as NSString).lastPathComponent
    let content = "\n\("".st_currentSystemTimestamp()) \(file)\nfuncName: \(funcName)\nlineNum: (\(lineNum))\nmessage: \(message)"
    print(content)
    #endif
}

/// 在DEBUG模式下打印到控制台并保存日志
public func STLogP<T>(_ message: T, file: String = #file, funcName: String = #function, lineNum: Int = #line) {
    #if DEBUG
    let file = (file as NSString).lastPathComponent
    let content = "\n\("".st_currentSystemTimestamp()) \(file)\nfuncName: \(funcName)\nlineNum: (\(lineNum))\nmessage: \(message)"
    print(content)
    var allContent = ""
    let outputPath = STConstants.st_outputLogPath()
    let userDefault = UserDefaults.standard
    if let origintContent = userDefault.object(forKey: outputPath) as? String {
        allContent = "\(origintContent)\n\(content)"
    } else {
        allContent = content
    }
    userDefault.setValue(allContent, forKey: outputPath)
    userDefault.synchronize()
    NotificationCenter.default.post(name: NSNotification.Name(rawValue: STConstants.st_notificationQueryLogName()), object: content)
    #endif
}
