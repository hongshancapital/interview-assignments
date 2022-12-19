//
//  CollectItemButton.swift
//  RefreshTableDemo
//
//  Created by yaojinhai on 2022/12/14.
//

import Foundation
import SwiftUI

struct CollectItemButton : View {
    
    @Binding var isCollection: Bool
    @Environment(\.colorScheme) private var colorScheme

    var body: some View {
        
        return Image(uiImage: getCurrentImage()!)
            .onTapGesture {
            isCollection.toggle()
        }
    }
    
    private func getCurrentImage() -> UIImage? {
        
        if isCollection {
            let image = UIImage(named: "collect_item")
            return image?.drawImageColor(scolor: colorScheme == .dark ? UIColor.white : UIColor.black)
        }
        return UIImage(named: "love_flag")?.drawImageColor(scolor: colorScheme == .dark ? UIColor.white : UIColor.black)
        
    }
}


extension UIImage {
    func drawImageColor(scolor: UIColor?) -> UIImage {
        guard let color = scolor else {
            return self
        }
        UIGraphicsBeginImageContextWithOptions(size, false, 0)
        color.setFill()
        let bounds = CGRect(x: 0, y: 0, width: size.width, height: size.height)
        UIRectFill(bounds)
        draw(in: bounds, blendMode: CGBlendMode.destinationIn, alpha: 1)
        let newImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        
        return newImage!
    }
}
