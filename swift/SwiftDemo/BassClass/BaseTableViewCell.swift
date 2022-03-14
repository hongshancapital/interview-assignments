//
//  BaseTableViewCell.swift
//  SwiftDemo
//
//  Created by AYX on 2022/3/11.
//

import UIKit

class BaseTableViewCell: UITableViewCell {
    static var reuseId: String {
        return String(describing: self)
    }
    
    static var nibName: String {
        return String(describing: self)
    }
}
