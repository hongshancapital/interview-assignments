//
//  STTabBarItem.swift
//  STBaseProject
//
//  Created by stack on 2017/12/16.
//  Copyright © 2017 ST. All rights reserved.
//

import UIKit
import Foundation

public class STTabBarItem: NSObject {
    
    public class func st_setTabBarItem(title: String,
                                       titleSize: CGFloat,
                                       titleFontName: String,
                                       normalImage: String,
                                       selectedImage: String,
                                       normalTitleColor: UIColor,
                                       selectedTitleColor: UIColor,
                                       backgroundColor: UIColor) -> UITabBarItem {
        var image: UIImage = UIImage.init()
        if let newImage = UIImage.init(named: normalImage) {
            image = newImage.withRenderingMode(.alwaysOriginal)
        }
        var lightImage: UIImage = UIImage.init()
        if let newImage = UIImage.init(named: selectedImage) {
            lightImage = newImage.withRenderingMode(.alwaysOriginal)
        }
        let item: UITabBarItem = UITabBarItem.init(title: title, image: image, selectedImage: lightImage)
        UITabBarItem.appearance().setTitleTextAttributes(
            [NSAttributedString.Key.foregroundColor: normalTitleColor,
             NSAttributedString.Key.backgroundColor: backgroundColor,
             NSAttributedString.Key.font: UIFont.init(name: titleFontName, size: titleSize) ?? UIFont.systemFont(ofSize: titleSize)], for: UIControl.State.normal)
        UITabBarItem.appearance().setTitleTextAttributes(
            [NSAttributedString.Key.foregroundColor: selectedTitleColor,
             NSAttributedString.Key.font: UIFont.init(name: titleFontName, size: titleSize) ?? UIFont.systemFont(ofSize: titleSize)], for: UIControl.State.selected)
        return item
    }
}
