//
//  LoadingView.swift
//  
//
//  Created by 黄磊 on 2022/4/12.
//

import SwiftUI

struct LoadingView: View {
    var body: some View {
        ProgressView()
            .progressViewStyle(CircularProgressViewStyle(tint: .init(UIColor.lightGray)))
    }
}

struct LoadingView_Previews: PreviewProvider {
    static var previews: some View {
        LoadingView()
    }
}
