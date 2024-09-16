//
//  LoadingView.swift
//  SwitUI实战
//
//

import SwiftUI

struct LoadingView: View {
    var body: some View {
        VStack(spacing: 20) {
            Text("⏰")
                .font(.system(size: 80))
            ProgressView()
            Text("正在加载数据，请稍等 ...")
                .foregroundColor(.gray)
        }
    }
}

struct LoadingView_Previews: PreviewProvider {
    static var previews: some View {
        LoadingView()
    }
}
