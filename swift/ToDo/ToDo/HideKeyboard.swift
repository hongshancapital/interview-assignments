//
//  HideKeyboard.swift
//  ToDo
//
//  Created by 童雨 on 2021/12/8.
//

import UIKit

#if canImport(UIKit)

extension UIApplication{
    func endEditing(){
        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}

#endif
