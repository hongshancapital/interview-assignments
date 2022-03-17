//
//  RefreshingView.swift
//  ListViewDemo
//
//  Created by HL on 2022/3/16.
//

import SwiftUI

struct RefreshingView: View {
    
    let text: String
    
    var body: some View {
        HStack {
            ActivityIndicator(style: .medium)
            Text(text)
        }
    }
}

struct RefreshingView_Previews: PreviewProvider {
    static var previews: some View {
        RefreshingView(text: "")
    }
}
