//
//  GrayText.swift
//  SwiftUIDemo
//
//  Created by banban on 2022/5/25.
//


import SwiftUI

struct GrayText : View {
    
    var _title : String?
    
    init(_ title : String?) {
        _title = title
    }
    
    var body: some View {
        Text(_title ?? "")
            .foregroundColor(Color.gray)
    }
}
