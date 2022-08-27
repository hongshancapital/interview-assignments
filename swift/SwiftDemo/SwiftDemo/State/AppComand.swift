//
//  AppComand.swift
//  SwiftDemo
//
//  Created by teenloong on 2022/8/27.
//

import Foundation

protocol AppCommand {
    func execute(in store: Store)
}

struct InitActionCommand: AppCommand {
    func execute(in store: Store) {
        
    }
}
