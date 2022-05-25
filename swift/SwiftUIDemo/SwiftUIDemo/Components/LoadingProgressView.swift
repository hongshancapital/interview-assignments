//
//  LoadingProgressView.swift
//  SwiftUIDemo
//
//  Created by banban on 2022/5/25.
//

import SwiftUI

struct LoadingProgressView : View {
    
    var isLarge : Bool = false
    
    var body: some View {
        ProgressView()
            .progressViewStyle(CircularProgressViewStyle(tint: Color.gray))
            .scaleEffect(isLarge ? 1.5 : 1.0)
    }
}
