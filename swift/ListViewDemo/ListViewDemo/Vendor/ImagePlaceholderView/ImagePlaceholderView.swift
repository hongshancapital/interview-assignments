//
//  ImagePlaceholderView.swift
//  ListViewDemo
//
//  Created by HL on 2022/3/16.
//

import SwiftUI

struct ImagePlaceholderView: View {
    var body: some View {
        ActivityIndicator(style: .medium)
    }
}

struct ImagePlaceholderView_Previews: PreviewProvider {
    static var previews: some View {
        ImagePlaceholderView()
    }
}
